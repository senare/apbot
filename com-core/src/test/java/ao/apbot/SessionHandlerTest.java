package ao.apbot;

import org.apache.mina.core.session.IoSession;
import org.junit.Ignore;
import org.junit.Test;

import ao.apbot.domain.Bot;
import ao.apbot.pkg.PrivateChannelMessagePacket;

public class SessionHandlerTest {

	@Test
	public void test() {
		// GIVEN
		Bot bot = new Bot("Karl", "user", "secret", "template");
		AoChatBot aoChatBot = null;
		SessionHandler handler = new SessionHandler(bot, aoChatBot);

		IoSession session = null;
		Object message = new PrivateChannelMessagePacket(55, "!aaacreate admin bot user pwd");

		// WHEN
		handler.messageReceived(session, message);

		Object message2 = new PrivateChannelMessagePacket(55, "!gubbe test");

		// WHEN
		handler.messageReceived(session, message2);
	}
}
