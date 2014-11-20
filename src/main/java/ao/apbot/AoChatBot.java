package ao.apbot;

import java.net.InetSocketAddress;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ao.apbot.codec.AoChatDecoder;
import ao.apbot.codec.AoChatEncoder;

public class AoChatBot implements ProtocolCodecFactory {

    private static Logger log = LoggerFactory.getLogger(AoChatBot.class);

    private String chatServerHost = "chat.d1.funcom.com";
    private int chatServerPort = 7105;

    public void spawn(String handle, String username, String password) throws Exception {
        log.info("Spawn bot {} ", handle);

        NioSocketConnector connector = new NioSocketConnector();

        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(this));
        connector.setHandler(new ClientSessionHandler(handle, username, password));

        for (;;) {
            try {
                ConnectFuture future = connector.connect(new InetSocketAddress(chatServerHost, chatServerPort));
                future.awaitUninterruptibly();
                future.getSession();
                break;
            } catch (RuntimeIoException e) {
                System.err.println("Failed to connect " + handle);
                e.printStackTrace();
                Thread.sleep(5000);
            }
        }
    }

    private static final AoChatEncoder ENCODER = new AoChatEncoder();

    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return ENCODER;
    }

    private static final AoChatDecoder DECODER = new AoChatDecoder();

    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return DECODER;
    }
}