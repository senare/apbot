package ao.apbot.codec;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class MsgPacketTest {

	@Test
	public void test() {
		// GIVEN
		MsgPacket pkg = testPkg("!karl ove 3445 karlsson");

		// WHEN
		String actual = pkg.getCommand();

		// THEN
		assertEquals("karl", actual);

		assertEquals("ove", pkg.getParam(1));
		assertEquals("3445", pkg.getParam(2));
		assertEquals("karlsson", pkg.getParam(3));
	}

	@Ignore("Not a test ...")
	private MsgPacket testPkg(final String msg) {
		return new MsgPacket((short) 55) {
			@Override
			public String getMsg() {
				return msg;
			}

			@Override
			public int getCharacterId() {
				return 0;
			}
		};
	}
}
