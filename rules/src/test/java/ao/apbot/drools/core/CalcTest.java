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

import ao.apbot.drools.core.Calc;

public class CalcTest {

    @Test
    public void testGet5_5_25() {
        String expected = "5*5 = 25.0";
        String actual = Calc.get("5*5");
        assertEquals(expected, actual);
    }

    @Test
    public void testGet5_5_1() {
        String expected = "5/5 = 1.0";
        String actual = Calc.get("5/5");
        assertEquals(expected, actual);
    }

    @Test
    public void testGet300_08_240() {
        String expected = "300*.8 = 240.0";
        String actual = Calc.get("300*.8");
        assertEquals(expected, actual);
    }
}
