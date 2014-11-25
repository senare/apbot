package ao.apbot.pkg.auth;

import org.apache.mina.core.buffer.IoBuffer;

import ao.apbot.codec.Fact;

/**
 * <p>
 * LoginSeedPacket is sent from the AO server to the client when a connection
 * between them is established. The seed that the server sends to the client is
 * used in the autentication process. Currently the seed is always a 32
 * character hexadecimal string (128 bit).
 * </p>
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: S <br>
 * DIRECTION: in
 * </p>
 *
 */
public class LoginSeedPacket extends Fact {

	public static final short TYPE = 0;

	public LoginSeedPacket() {
		super(TYPE);
	}

	private String seed;

	@Override
	public void decode(IoBuffer buff) {
		seed = decodeString(buff);
	}

	public String getServerSeed() {
		return seed;
	}

	@Override
	public String toString() {
		return String.format("%s [%s]", super.toString(), seed);
	}
}
