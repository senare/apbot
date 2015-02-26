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
package ao.apbot.drools.core;

import static org.junit.Assert.*;

import org.junit.Test;

import ao.apbot.drools.core.OverEquip;

public class OverEquipTest {

    @Test
    public void test199() {
        String expected = "With a skill of <font color=#ffff00> 199 </font>, you will be OE above <font color=#ffff00> 248 </font> skill, with a requirement of <font color=#ffff00> 199 </font> skill, you can have <font color=#ffff00> 159 </font> without being OE.";
        String actual = OverEquip.get("199");
        assertEquals(expected, actual);
    }

    @Test
    public void testNotNumber() {
        String expected = "With a skill of <font color=#ffff00> 0 </font>, you will be OE above <font color=#ffff00> 0 </font> skill, with a requirement of <font color=#ffff00> 0 </font> skill, you can have <font color=#ffff00> 0 </font> without being OE.";
        String actual = OverEquip.get("this is not a number");
        assertEquals(expected, actual);
    }
}
