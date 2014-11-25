package ao.apbot.drools;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;

public abstract class Fact {

	protected Charset UTF8 = Charset.forName("UTF-8");

	protected short type;

	public short getType() {
		return type;
	}

	public Fact(short type) {
		this.type = type;
	}

	public String toString() {
		return String.format("[%s]%s :", type, this.getClass().getSimpleName());
	}

	public void encode(IoBuffer buff) {
	}

	public void decode(IoBuffer buff) {
	}

	protected void encodeString(IoBuffer buff, String value) {
		if (value == null) {
			buff.putShort((short) 1);
			buff.put((byte) 0);
		} else {
			byte[] bytes = value.getBytes(UTF8);
			buff.putShort((short) bytes.length);
			buff.put(bytes);
		}
	}

	protected String decodeString(IoBuffer buff) {
		byte[] bytes = new byte[buff.getShort()];
		buff.get(bytes);
		return new String(bytes, UTF8);
	}
}
