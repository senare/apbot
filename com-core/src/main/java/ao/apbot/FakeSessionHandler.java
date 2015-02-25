package ao.apbot;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import ao.apbot.domain.Bot;

public class FakeSessionHandler extends IoHandlerAdapter {

    public FakeSessionHandler(Bot bot, AoChatBot aoChatBot) {
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
    }

    @Override
    public void sessionOpened(IoSession session) {
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
    };

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
    }
}