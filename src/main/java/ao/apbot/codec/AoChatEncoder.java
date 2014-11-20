package ao.apbot.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ao.protocol.packets.Packet;

public class AoChatEncoder implements ProtocolEncoder {

    private static Logger log = LoggerFactory.getLogger(AoChatEncoder.class);

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {

        if (message instanceof Packet) {
            Packet pkg = (Packet) message;
            short type = pkg.getType();
            byte[] data = pkg.getData();

            IoBuffer buffer = IoBuffer.allocate(data.length + 8);
            buffer.setAutoExpand(true);
            buffer.putShort(type);
            buffer.putShort((short) data.length);
            buffer.put(data);
            buffer.flip();
            out.write(buffer);
        } else {
            log.info("FAIL !!!");
        }
    }

    @Override
    public void dispose(IoSession session) throws Exception {
    }
}
