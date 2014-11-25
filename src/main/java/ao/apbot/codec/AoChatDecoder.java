package ao.apbot.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ao.apbot.drools.Fact;
import ao.apbot.drools.UnparsablePacket;
import ao.apbot.drools.facts.BroadcastMessagePacket;
import ao.apbot.drools.facts.ChannelUpdatePacket;
import ao.apbot.drools.facts.CharacterLookupPacket;
import ao.apbot.drools.facts.CharacterUnknownPacket;
import ao.apbot.drools.facts.CharacterUpdatePacket;
import ao.apbot.drools.facts.FriendRemovePacket;
import ao.apbot.drools.facts.PingPacket;
import ao.apbot.drools.facts.PrivateChannelCharacterJoinPacket;
import ao.apbot.drools.facts.PrivateChannelCharacterLeavePacket;
import ao.apbot.drools.facts.PrivateChannelInvitePacket;
import ao.apbot.drools.facts.PrivateChannelKickPacket;
import ao.apbot.drools.facts.SimpleSystemMessagePacket;
import ao.apbot.drools.facts.SystemMessagePacket;
import ao.apbot.drools.facts.VicinityMessagePacket;
import ao.apbot.drools.facts.auth.CharacterListPacket;
import ao.apbot.drools.facts.auth.LoginErrorPacket;
import ao.apbot.drools.facts.auth.LoginOkPacket;
import ao.apbot.drools.facts.auth.LoginSeedPacket;
import ao.protocol.packets.bi.ChannelMessagePacket;
import ao.protocol.packets.bi.FriendUpdatePacket;
import ao.protocol.packets.bi.PrivateChannelMessagePacket;
import ao.protocol.packets.bi.PrivateMessagePacket;

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
            Fact packet = packet(type);
            if (!(packet instanceof UnparsablePacket)) {
                short len = ioBuffer.getShort();
                if (ioBuffer.remaining() < len) {
                    log.debug("Not enough bytes to read keep waiting...");
                    ioBuffer.position(startPosition);
                    return false;
                }
            }
            packet.decode(ioBuffer);
            decoderOutput.write(packet);
            return true;
        } catch (Exception x) {
            log.debug("Something went wrong rollback position");
            ioBuffer.position(startPosition);
            return false;
        }
    }

    public Fact packet(short type) {
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