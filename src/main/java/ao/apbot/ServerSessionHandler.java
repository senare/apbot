package ao.apbot;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerSessionHandler extends IoHandlerAdapter {

	private static Logger log = LoggerFactory.getLogger(ServerSessionHandler.class);

	private String handle;

	public ServerSessionHandler(String handle) {
		this.handle = handle;
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.error("{} caught exception ", handle, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		log.info("{} message received {} ", handle, message);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.debug("{} IDLE {} ", handle, session.getIdleCount(status));
	}
}