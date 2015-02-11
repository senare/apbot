package ao.apbot;

import java.net.InetSocketAddress;
import java.nio.ByteOrder;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.DummySession;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ao.apbot.codec.Fact;
import ao.apbot.codec.UnparsablePacket;
import ao.apbot.domain.Bot;
import ao.apbot.jpa.BotManager;
import ao.apbot.pkg.BroadcastMessagePacket;
import ao.apbot.pkg.ChannelMessagePacket;
import ao.apbot.pkg.ChannelUpdatePacket;
import ao.apbot.pkg.CharacterLookupPacket;
import ao.apbot.pkg.CharacterUnknownPacket;
import ao.apbot.pkg.CharacterUpdatePacket;
import ao.apbot.pkg.FriendRemovePacket;
import ao.apbot.pkg.FriendUpdatePacket;
import ao.apbot.pkg.PrivateChannelCharacterJoinPacket;
import ao.apbot.pkg.PrivateChannelCharacterLeavePacket;
import ao.apbot.pkg.PrivateChannelInvitePacket;
import ao.apbot.pkg.PrivateChannelKickPacket;
import ao.apbot.pkg.PrivateChannelMessagePacket;
import ao.apbot.pkg.PrivateMessagePacket;
import ao.apbot.pkg.SimpleSystemMessagePacket;
import ao.apbot.pkg.SystemMessagePacket;
import ao.apbot.pkg.VicinityMessagePacket;
import ao.apbot.pkg.auth.CharacterListPacket;
import ao.apbot.pkg.auth.LoginErrorPacket;
import ao.apbot.pkg.auth.LoginOkPacket;
import ao.apbot.pkg.auth.LoginSeedPacket;
import ao.apbot.pkg.auth.PingPacket;

public class AoChatBot implements ProtocolCodecFactory {

	private static Logger log = LoggerFactory.getLogger(AoChatBot.class);

	private ConcurrentMap<String, IoSession> network = new ConcurrentHashMap<>();

	private BotManager bm;

	private String chatServerHost = "chat.d1.funcom.com";
	private int chatServerPort = 7105;

	public void spawn(BotManager bm) throws Exception {
		this.bm = bm;

		for (Bot bot : bm.getBots()) {
			if (bot.isActive()) {
				spawn(bot);
			}
		}
	}

	public void newBot(String name, String username, String password, String template) {
		bm.newBot(name, username, password, template);
	}

	public void active(String name, boolean active) throws Exception {
		bm.active(name, active);
	}

	public void kill(String name) throws Exception {
		if (network.containsKey(name)) {
			IoSession ioSession = network.get(name);
			synchronized (network) {
				ioSession.close(true);
				network.remove(name);
			}
			log.debug("Halt {} ", name);
		} else {
			log.debug("No such handle running {} ", name);
		}
	}

	public void spawn(String name) throws Exception {
		List<Bot> bots = bm.load(name);
		if (bots.size() == 1) {
			spawn(bots.get(0));
		} else {
			if (bots.isEmpty()) {
				log.debug("No such bot defined {} ", name, bots.size());
			} else {
				log.debug("Unbigious {} found {} instances", name, bots.size());
			}
		}
	}

	private static final IoSession TOKEN = new DummySession();

	private void spawn(Bot bot) throws Exception {

		IoSession botInstance = network.putIfAbsent(bot.getName(), TOKEN);
		if (botInstance != null && botInstance != TOKEN) {
			log.info("Bot {} is running", bot.getName());
			return;
		}

		synchronized (network) {
			botInstance = network.putIfAbsent(bot.getName(), (IoSession) TOKEN);
			if (botInstance != null && botInstance != TOKEN) {
				/* Bot spawned by competing thread */
				return;
			}

			log.info("Spawn bot {} ", bot.getName());

			NioSocketConnector connector = new NioSocketConnector();

			connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(this));
			connector.setHandler(new SessionHandler(bot, this));

			IoSession session;
			for (;;) {
				try {
					ConnectFuture future = connector.connect(new InetSocketAddress(chatServerHost, chatServerPort));
					future.awaitUninterruptibly();
					session = future.getSession();
					break;
				} catch (RuntimeIoException e) {
					System.err.println("Failed to connect " + bot.getName());
					e.printStackTrace();
					Thread.sleep(5000);
				}
			}
			network.put(bot.getName(), session);
		}
	}

	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return ENCODER;
	}

	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return DECODER;
	}

	private static final ProtocolEncoder ENCODER = new ProtocolEncoder() {

		@Override
		public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
			if (message instanceof Fact) {
				Fact pkg = (Fact) message;
				IoBuffer buffer = IoBuffer.allocate(1024);
				buffer.setAutoExpand(true);
				buffer.order(ByteOrder.BIG_ENDIAN);
				buffer.putShort(pkg.getType());
				int position = buffer.position();
				buffer.putShort((short) 0);
				pkg.encode(buffer);
				short len = (short) (buffer.position() - (position + 2));
				buffer.putShort(position, len);
				buffer.flip();
				out.write(buffer);
			} else {
				log.info("FAIL !!!");
				log.info(" " + message);
				log.info("class " + message.getClass());
			}
		}

		@Override
		public void dispose(IoSession session) throws Exception {
		}
	};

	private static final CumulativeProtocolDecoder DECODER = new CumulativeProtocolDecoder() {

		@Override
		protected boolean doDecode(IoSession session, IoBuffer ioBuffer, ProtocolDecoderOutput decoderOutput) throws Exception {
			int startPosition = ioBuffer.position();

			if (ioBuffer.remaining() < 3) {
				log.debug("Not enough bytes to read keep waiting...");
				return false;
			}

			try {
				short type = ioBuffer.getShort();
				Fact packet = packet(type);
				if (!(packet instanceof UnparsablePacket)) {
					short len = ioBuffer.getShort();
					if (ioBuffer.remaining() < len) {
						log.debug("Not enough bytes to read keep waiting...");
						ioBuffer.position(startPosition);
						return false;
					}
				}
				packet.decode(ioBuffer);
				decoderOutput.write(packet);
				return true;
			} catch (Exception x) {
				log.debug("Something went wrong rollback position");
				ioBuffer.position(startPosition);
				return false;
			}
		}

	};

	private static Fact packet(short type) {
		switch (type) {
		case LoginSeedPacket.TYPE:
			return new LoginSeedPacket(); // TYPE 0
		case LoginOkPacket.TYPE:
			return new LoginOkPacket(); // TYPE 5
		case LoginErrorPacket.TYPE:
			return new LoginErrorPacket(); // TYPE 6
		case CharacterListPacket.TYPE:
			return new CharacterListPacket(); // TYPE 7
		case CharacterUnknownPacket.TYPE:
			return new CharacterUnknownPacket(); // TYPE 10
		case SystemMessagePacket.TYPE:
			return new SystemMessagePacket(); // TYPE 20
		case CharacterLookupPacket.TYPE:
			return new CharacterLookupPacket(); // TYPE 21
		case PrivateMessagePacket.TYPE:
			return new PrivateMessagePacket(); // TYPE 30
		case VicinityMessagePacket.TYPE:
			return new VicinityMessagePacket(); // TYPE 34
		case BroadcastMessagePacket.TYPE:
			return new BroadcastMessagePacket(); // TYPE 35
		case SimpleSystemMessagePacket.TYPE:
			return new SimpleSystemMessagePacket(); // TYPE 36
		case CharacterUpdatePacket.TYPE:
			return new CharacterUpdatePacket(); // TYPE 37
		case FriendUpdatePacket.TYPE:
			return new FriendUpdatePacket(); // TYPE 40
		case FriendRemovePacket.TYPE:
			return new FriendRemovePacket(); // TYPE 41
		case PrivateChannelInvitePacket.TYPE:
			return new PrivateChannelInvitePacket(); // TYPE 50
		case PrivateChannelKickPacket.TYPE:
			return new PrivateChannelKickPacket(); // TYPE 51
		case PrivateChannelCharacterJoinPacket.TYPE:
			return new PrivateChannelCharacterJoinPacket(); // TYPE 55
		case PrivateChannelCharacterLeavePacket.TYPE:
			return new PrivateChannelCharacterLeavePacket(); // TYPE 56
		case PrivateChannelMessagePacket.TYPE:
			return new PrivateChannelMessagePacket(); // TYPE 57
		case ChannelUpdatePacket.TYPE:
			return new ChannelUpdatePacket(); // TYPE 60
		case ChannelMessagePacket.TYPE:
			return new ChannelMessagePacket(); // TYPE 65
		case PingPacket.TYPE:
			return new PingPacket(); // TYPE 100

		default:
			return new UnparsablePacket(type);
		}
	}
}