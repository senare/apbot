package ao.apbot.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ao.apbot.AoChatBot;

public class AoChatDecoder extends CumulativeProtocolDecoder {

    private static Logger log = LoggerFactory.getLogger(AoChatDecoder.class);

    @Override
    protected boolean doDecode(IoSession session, IoBuffer ioBuffer, ProtocolDecoderOutput decoderOutput) throws Exception {
        int startPosition = ioBuffer.position();

        if (ioBuffer.remaining() < 3) {
            log.debug("Not enough bytes to read keep waiting...");
            return false;
        }

        try {
            short type = ioBuffer.getShort();
            byte[] data = new byte[ioBuffer.getShort()];
            ioBuffer.get(data);
            decoderOutput.write(AoChatBot.packetFactory.toPacket(type, data));
            return true;
        } catch (Exception x) {
            log.debug("Something went wrong rollback position");
            ioBuffer.position(startPosition);
            return false;
        }
    }
}