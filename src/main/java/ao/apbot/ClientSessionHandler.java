package ao.apbot;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ao.apbot.drools.Fact;
import ao.apbot.drools.facts.PingPacket;
import ao.apbot.drools.facts.auth.CharacterListPacket;
import ao.apbot.drools.facts.auth.LoginErrorPacket;
import ao.apbot.drools.facts.auth.LoginOkPacket;
import ao.apbot.drools.facts.auth.LoginRequestPacket;
import ao.apbot.drools.facts.auth.LoginSeedPacket;
import ao.apbot.drools.facts.auth.LoginSelectPacket;

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
	public void messageReceived(IoSession session, Object message) {
		log.info("{} received {}", handle, message);

		if (message instanceof Fact) {
			Fact pkg = (Fact) message;
			switch (pkg.getType()) {
			case PingPacket.TYPE:
				session.write(new PingPacket(((PingPacket) pkg).getMessage()));
				break;
			case LoginSeedPacket.TYPE:
				session.write(new LoginRequestPacket((LoginSeedPacket) pkg, username, password));
				break;
			case CharacterListPacket.TYPE:
				session.write(new LoginSelectPacket((CharacterListPacket) pkg, handle));
				break;
			case LoginOkPacket.TYPE:
				log.info("{} has logged on", handle);
				break;
			case LoginErrorPacket.TYPE:
				log.info("{} failed to logon", handle);
				break;
			default:
			}
		} else {
			session.close(true);
		}
	}

	@Override
	public void sessionOpened(IoSession session) {
		log.info("{} connect", handle);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		log.info("{} sent {}", handle, message);
	};

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.info("{} idle {}", handle, status);
		session.write(new PingPacket("Java AOChat API ping"));
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		log.error("Client caught exception ", cause);
		session.close(true);
	}
}