package ao.apbot.drools.facts.auth;

import org.apache.mina.core.buffer.IoBuffer;

import ao.apbot.codec.LoginKeyGenerator;
import ao.apbot.drools.Fact;

/**
 * <p>
 * LoginRequestPacket is sent from the client to the AO server in an attempt to
 * authenticate the client.
 * </p>
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: ISS <br>
 * DIRECTION: out
 * </p>
 *
 * @author Paul Smith
 */
public class LoginRequestPacket extends Fact {

    public static final short TYPE = 2;

    private int version;
    private String username;
    private String key;

    public LoginRequestPacket(LoginSeedPacket seed, String username, String password) {
        super(TYPE);
        this.version = LoginKeyGenerator.PROTOCOL_VERSION;
        this.username = username;
        this.key = LoginKeyGenerator.generateLoginKey(seed.getServerSeed(), username, password);
    }

    public void encode(IoBuffer buff) {
        // buff.allocate(username.length() + key.length());
        // buff.setAutoExpand(true);
        // buff.order(ByteOrder.BIG_ENDIAN);
        buff.putInt(version);
        encodeString(buff, username);
        encodeString(buff, key);
    }

    @Override
    public String toString() {
        return String.format("%s V[%s] %s K[%s]", super.toString(), version, username, key);
    }
}
