/*
    Copyright (C) 2015 Senare

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

    contact : aperfectbot@gmail.com
    
 */
package ao.apbot;

import org.apache.mina.core.session.IoSession;
import org.junit.Test;

import ao.apbot.domain.Bot;
import ao.apbot.pkg.PrivateChannelMessagePacket;

public class SessionHandlerTest {

    @Test
    public void test() {
        // GIVEN
        Bot bot = new Bot("Karl", "user", "secret", Template.ADMIN, 234234);
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
