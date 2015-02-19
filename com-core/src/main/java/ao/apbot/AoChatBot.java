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
import org.jboss.logging.Logger;

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

    private static Logger LOGGER = Logger.getLogger(AoChatBot.class);

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

    public String newBot(String name, String username, String password, String template, int owner) {
        StringBuffer replay = new StringBuffer();
        try {

            if (name == null) {
                replay.append(" Name can't be null ");
            }
            if (username == null) {
                replay.append(" Username can't be null ");
            }
            if (password == null) {
                replay.append(" Password can't be null ");
            }
            if (template == null) {
                replay.append(" Template can't be null ");
            }

            Template enumTemplate = Template.ORG;
            try {
                enumTemplate = Enum.valueOf(Template.class, template.toUpperCase());
            } catch (IllegalArgumentException x) {
                return ("Available templates: <font color=#0000FF> ADMIN ORG PVP PVM </font>");
            }

            if (replay.length() != 0) {
                LOGGER.error(replay);
                return replay.toString();
            } else {
                bm.newBot(name, username, password, enumTemplate, owner);
                return String.format("Created bot %s ", name);
            }
        } catch (Exception x) {
            LOGGER.errorf(x, "new bot failed");
            return replay.toString();
        }
    }

    public String update(String currentName, String currentUsername, String name, String username, String password, String template) {
        StringBuffer replay = new StringBuffer();
        try {

            if (currentName == null) {
                replay.append(" Current name can't be null ");
            }
            if (currentUsername == null) {
                replay.append(" Current user can't be null ");
            }

            if (name == null) {
                replay.append(" Name can't be null ");
            }
            if (username == null) {
                replay.append(" Username can't be null ");
            }
            if (password == null) {
                replay.append(" Password can't be null ");
            }
            if (template == null) {
                replay.append(" Template can't be null ");
            }

            Template enumTemplate = Template.ORG;
            try {
                enumTemplate = Enum.valueOf(Template.class, template.toUpperCase());
            } catch (IllegalArgumentException x) {
                return ("Available templates: <font color=#0000FF> ADMIN ORG PVP PVM </font>");
            }

            if (replay.length() != 0) {
                LOGGER.error(replay);
                return replay.toString();
            } else {
                bm.update(currentName, currentUsername, name, username, password, enumTemplate);
                return String.format("Updated %s ", name);
            }
        } catch (Exception x) {
            LOGGER.errorf(x, "new bot failed");
            return replay.toString();
        }
    }

    public String active(String name, boolean active) {
        StringBuffer replay = new StringBuffer();
        try {

            if (name == null) {
                replay.append(" Name can't be null ");
            }

            if (replay.length() != 0) {
                LOGGER.error(replay);
                return replay.toString();
            } else {
                bm.active(name, active);
                return String.format("%s bot %s", (active ? "Activate" : "Deactivate"), name);
            }
        } catch (Exception x) {
            LOGGER.errorf(x, "Change state failed");
            return replay.toString();
        }
    }

    public String kill(String name) {
        StringBuffer replay = new StringBuffer();
        try {
            if (name == null) {
                replay.append(" Name can't be null ");
            }

            if (replay.length() != 0) {
                LOGGER.error(replay);
                return replay.toString();
            } else if (network.containsKey(name)) {
                IoSession ioSession = network.get(name);
                synchronized (network) {
                    ioSession.close(true);
                    network.remove(name);
                }
                LOGGER.debugf("Halt %s ", name);
                return String.format("Terminating %s", name);
            } else {
                LOGGER.debugf("No such handle running %s ", name);
                return String.format("No such handle running %s ", name);
            }
        } catch (Exception x) {
            LOGGER.errorf(x, "kill bot failed");
            return replay.toString();
        }
    }

    public String spawn(String name) {
        StringBuffer replay = new StringBuffer();
        try {
            if (name == null) {
                replay.append(" Name can't be null ");
            }

            if (replay.length() != 0) {
                LOGGER.error(replay);
                return replay.toString();
            } else {
                List<Bot> bots = bm.load(name);
                if (bots.size() == 1) {
                    spawn(bots.get(0));
                    return String.format("Spawning %s", name);
                } else {
                    if (bots.isEmpty()) {
                        LOGGER.debugf("No such bot defined %s ", name);
                        return String.format("No such bot defined %s ", name);
                    } else {
                        LOGGER.debugf("Unbigious %s found %s instances", name, bots.size());
                        return String.format("Unbigious %s found %s instances", name, bots.size());
                    }
                }
            }
        } catch (Exception x) {
            LOGGER.errorf(x, "spawn bot failed");
            return replay.toString();
        }
    }

    private static final IoSession TOKEN = new DummySession();

    private void spawn(Bot bot) throws Exception {

        IoSession botInstance = network.putIfAbsent(bot.getName(), TOKEN);
        if (botInstance != null && botInstance != TOKEN) {
            LOGGER.infof("Bot %s is running", bot.getName());
            return;
        }

        synchronized (network) {
            botInstance = network.putIfAbsent(bot.getName(), (IoSession) TOKEN);
            if (botInstance != null && botInstance != TOKEN) {
                /* Bot spawned by competing thread */
                return;
            }

            LOGGER.infof("Spawn bot %s ", bot.getName());

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
                LOGGER.info("FAIL !!!");
                LOGGER.info(" " + message);
                LOGGER.info("class " + message.getClass());
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
                LOGGER.debug("Not enough bytes to read keep waiting...");
                return false;
            }

            try {
                short type = ioBuffer.getShort();
                Fact packet = packet(type);
                if (!(packet instanceof UnparsablePacket)) {
                    short len = ioBuffer.getShort();
                    if (ioBuffer.remaining() < len) {
                        LOGGER.debug("Not enough bytes to read keep waiting...");
                        ioBuffer.position(startPosition);
                        return false;
                    }
                }
                packet.decode(ioBuffer);
                decoderOutput.write(packet);
                return true;
            } catch (Exception x) {
                LOGGER.debug("Something went wrong rollback position");
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