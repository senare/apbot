package ao.apbot;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ao.protocol.auth.LoginKeyGenerator;
import ao.protocol.auth.LoginRequestPacket;
import ao.protocol.auth.LoginSeedPacket;
import ao.protocol.packets.Packet;

public class ClientSessionHandler extends IoHandlerAdapter {

	private final static Logger log = LoggerFactory.getLogger(ClientSessionHandler.class);

	private String password;
	private String username;
	private String handle;

	public ClientSessionHandler(String handle, String username, String password) {
		this.handle = handle;
		this.username = username;
		this.password = password;
	}

	@Override
	public void sessionOpened(IoSession session) {
		log.info("{} connect", handle);
	}

	@Override
	public void messageReceived(IoSession session, Object message) {
		log.info("{} received {}", handle, message);

		if (message instanceof LoginSeedPacket) {
			Packet packet = new LoginRequestPacket(LoginKeyGenerator.PROTOCOL_VERSION, username, LoginKeyGenerator.generateLoginKey(((LoginSeedPacket) message).getLoginSeed(), username, password));
			session.write(packet);
		} else {
			session.close(true);
		}
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		log.error("Client caught exception ", cause);
		session.close(true);
	}
}