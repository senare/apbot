package ao.apbot;

import java.net.InetSocketAddress;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ao.apbot.codec.XMLDecoder;
import ao.apbot.codec.XMLEncoder;

public class BotFactory implements ProtocolCodecFactory {

	private static Logger log = LoggerFactory.getLogger(BotFactory.class);

	private String chatServerHost = "chat.d1.funcom.com";
	private int chatServerPort = 7105;

	private int chatClientPort = 10500;

	public void spawnBot(String handle, String username, String password) throws Exception {
		log.info("Spawn bot for {} login {} ", handle, username);
		spawnServer(handle);
		spawnClient(handle, username, password);
	}

	public void spawnClient(String handle, String username, String password) throws Exception {
		NioSocketConnector connector = new NioSocketConnector();

		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(this));
		connector.setHandler(new ClientSessionHandler(handle, username, password));

		for (;;) {
			try {
				ConnectFuture future = connector.connect(new InetSocketAddress(chatServerHost, chatServerPort));
				future.awaitUninterruptibly();
				future.getSession();

				log.info("Client connected {} ", handle);
				break;
			} catch (RuntimeIoException e) {
				System.err.println("Failed to connect " + handle);
				e.printStackTrace();
				Thread.sleep(5000);
			}
		}
	}

	public void spawnServer(String handle) throws Exception {
		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(this));
		acceptor.setHandler(new ServerSessionHandler(handle));
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.bind(new InetSocketAddress(chatClientPort));

		log.info("Server connected {} ", handle);
	}

	private static final XMLEncoder XML_ENCODER = new XMLEncoder();

	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return XML_ENCODER;
	}

	private static final XMLDecoder XML_DECODER = new XMLDecoder();

	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return XML_DECODER;
	}
}