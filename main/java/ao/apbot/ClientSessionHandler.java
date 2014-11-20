package ao.apbot;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		log.info("{} logon {} ", handle, username);
		// TODO logon :)
		// session.write(request);
	}

	@Override
	public void messageReceived(IoSession session, Object message) {
		log.info("{} received {}", handle, message);
		session.close(true);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		log.error("Client caught exception ", cause);
		session.close(true);
	}
}