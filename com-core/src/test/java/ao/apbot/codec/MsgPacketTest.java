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
