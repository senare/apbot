package ao.apbot.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ao.apbot.drools.Fact;
import ao.apbot.drools.facts.BroadcastMessagePacket;
import ao.apbot.drools.facts.ChannelUpdatePacket;
import ao.apbot.drools.facts.PrivateChannelCharacterJoinPacket;
import ao.apbot.drools.facts.PrivateChannelCharacterLeavePacket;
import ao.apbot.drools.facts.SimpleSystemMessagePacket;
import ao.apbot.drools.facts.SystemMessagePacket;
import ao.apbot.drools.facts.VicinityMessagePacket;
import ao.apbot.drools.facts.auth.CharacterListPacket;
import ao.apbot.drools.facts.auth.LoginErrorPacket;
import ao.apbot.drools.facts.auth.LoginOkPacket;
import ao.apbot.drools.facts.auth.LoginSeedPacket;
import ao.db.MMDBDatabase;
import ao.protocol.packets.MalformedPacketException;
import ao.protocol.packets.Packet;
import ao.protocol.packets.UnparsablePacket;
import ao.protocol.packets.bi.ChannelMessagePacket;
import ao.protocol.packets.bi.CharacterLookupPacket;
import ao.protocol.packets.bi.FriendRemovePacket;
import ao.protocol.packets.bi.FriendUpdatePacket;
import ao.protocol.packets.bi.PingPacket;
import ao.protocol.packets.bi.PrivateChannelInvitePacket;
import ao.protocol.packets.bi.PrivateChannelKickPacket;
import ao.protocol.packets.bi.PrivateChannelMessagePacket;
import ao.protocol.packets.bi.PrivateMessagePacket;
import ao.protocol.packets.toclient.CharacterUnknownPacket;
import ao.protocol.packets.toclient.CharacterUpdatePacket;

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
            Fact packet = toPacket(type);
            if (packet != null) {
                short len = ioBuffer.getShort();
                if (ioBuffer.remaining() < len) {
                    log.debug("Not enough bytes to read keep waiting...");
                    ioBuffer.position(startPosition);
                    return false;
                }
                packet.decode(ioBuffer);
                decoderOutput.write(packet);
            } else {
                byte[] data = new byte[ioBuffer.getShort()];
                ioBuffer.get(data);
                decoderOutput.write(toPacket(type, data));
            }
            return true;
        } catch (Exception x) {
            log.debug("Something went wrong rollback position");
            ioBuffer.position(startPosition);
            return false;
        }
    }

    public Fact toPacket(short type) throws MalformedPacketException {
        switch (type) {
        case LoginSeedPacket.TYPE:
            return new LoginSeedPacket(); // TYPE 0
        case LoginOkPacket.TYPE:
            return new LoginOkPacket(); // TYPE 5
        case LoginErrorPacket.TYPE:
            return new LoginErrorPacket(); // TYPE 6
        case CharacterListPacket.TYPE:
            return new CharacterListPacket(); // TYPE 7
        case SystemMessagePacket.TYPE:
            return new SystemMessagePacket(); // TYPE 20
        case VicinityMessagePacket.TYPE:
            return new VicinityMessagePacket(); // TYPE 34
        case BroadcastMessagePacket.TYPE:
            return new BroadcastMessagePacket(); // TYPE 35
        case SimpleSystemMessagePacket.TYPE:
            return new SimpleSystemMessagePacket(); // TYPE 36
        case PrivateChannelCharacterJoinPacket.TYPE:
            return new PrivateChannelCharacterJoinPacket(); // TYPE 55
        case PrivateChannelCharacterLeavePacket.TYPE:
            return new PrivateChannelCharacterLeavePacket(); // TYPE 56
        case ChannelUpdatePacket.TYPE:
            return new ChannelUpdatePacket(); // TYPE 60
        }

        return null;
    }

    public Packet toPacket(short type, byte[] data) throws MalformedPacketException {
        MMDBDatabase database = null;

        switch (type) {
        // Incoming Packets
        case CharacterUnknownPacket.TYPE:
            return new CharacterUnknownPacket(data); // TYPE 10
        case CharacterUpdatePacket.TYPE:
            return new CharacterUpdatePacket(data); // TYPE 37

            // Bidirectional Packets
        case CharacterLookupPacket.TYPE:
            return new CharacterLookupPacket(data, Packet.Direction.TO_CLIENT); // TYPE
                                                                                // 21
        case PrivateMessagePacket.TYPE:
            return new PrivateMessagePacket(data, Packet.Direction.TO_CLIENT); // TYPE
                                                                               // 30
        case FriendUpdatePacket.TYPE:
            return new FriendUpdatePacket(data, Packet.Direction.TO_CLIENT); // TYPE
                                                                             // 40
        case FriendRemovePacket.TYPE:
            return new FriendRemovePacket(data, Packet.Direction.TO_CLIENT); // TYPE
                                                                             // 41
        case PrivateChannelInvitePacket.TYPE:
            return new PrivateChannelInvitePacket(data, Packet.Direction.TO_CLIENT); // TYPE
                                                                                     // 50
        case PrivateChannelKickPacket.TYPE:
            return new PrivateChannelKickPacket(data, Packet.Direction.TO_CLIENT); // TYPE
                                                                                   // 51
        case PrivateChannelMessagePacket.TYPE:
            return new PrivateChannelMessagePacket(data, Packet.Direction.TO_CLIENT); // TYPE
                                                                                      // 57
        case ChannelMessagePacket.TYPE:
            return new ChannelMessagePacket(data, database, Packet.Direction.TO_CLIENT); // TYPE
                                                                                         // 65
        case PingPacket.TYPE:
            return new PingPacket(data, Packet.Direction.TO_CLIENT); // TYPE 100

        default:
            return new UnparsablePacket(type, data, Packet.Direction.TO_CLIENT);
        }
    }
}