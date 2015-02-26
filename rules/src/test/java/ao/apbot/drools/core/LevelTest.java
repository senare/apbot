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

import ao.apbot.drools.core.Level;

public class LevelTest {

    @Test
    public void testMoreThen220() {
        String expected = "<font color=#DEDE42> L <font color=#FFFF00> 220 </font>:team 159 - 220 | PVP 175 - 220 | 0 SK | missions<font color=#66CCFF> 154 165 176 187 198 220 242 250 </font></font>";
        String actual = Level.get("230");
        assertEquals(expected, actual);
    }

    @Test
    public void testLessThen1() {
        String expected = "<font color=#DEDE42> L <font color=#FFFF00> 1 </font>:team 1 - 1 | PVP 1 - 5 | 1450 XP | missions<font color=#66CCFF> 1 </font></font>";
        String actual = Level.get("0");
        assertEquals(expected, actual);
    }

    @Test
    public void test199() {
        String expected = "<font color=#DEDE42> L <font color=#FFFF00> 199 </font>:team 144 - 220 | PVP 158 - 220 | 73393900 XP | missions<font color=#66CCFF> 139 149 159 169 179 199 218 238 250 </font></font>";
        String actual = Level.get("199");
        assertEquals(expected, actual);
    }

    @Test
    public void testNotNumber() {
        String expected = "<font color=#DEDE42> L <font color=#FFFF00> 1 </font>:team 1 - 1 | PVP 1 - 5 | 1450 XP | missions<font color=#66CCFF> 1 </font></font>";
        String actual = Level.get("this is not a number");
        assertEquals(expected, actual);
    }

    @Test
    public void testMishMoreThen250() {
        String expected = "<font color=#DEDE42> QL <font color=#FFFF00> 250 </font> missions maybe from these level players (+/- 1):<font color=#66CCFF> 140 141 142 143 144 145 146 147 148 149 150 151 152 153 154 155 156 157 158 159 160 161 162 163 164 165 166 167 167 168 169 170 170 171 172 173 174 175 176 177 178 179 180 181 182 183 184 185 186 187 188 189 190 191 192 193 194 194 195 196 197 198 199 200 201 202 203 204 205 206 207 208 209 210 211 212 213 214 215 216 217 218 219 220 </font></font>";
        String actual = Level.mish("255");
        assertEquals(expected, actual);
    }

    @Test
    public void testMishLessThen1() {
        String expected = "<font color=#DEDE42> QL <font color=#FFFF00> 1 </font> missions maybe from these level players (+/- 1):<font color=#66CCFF> 1 2 </font></font>";
        String actual = Level.mish("0");
        assertEquals(expected, actual);
    }

    @Test
    public void testMish199() {
        String expected = "<font color=#DEDE42> QL <font color=#FFFF00> 199 </font> missions maybe from these level players (+/- 1):<font color=#66CCFF> 133 166 181 199 </font></font>";
        String actual = Level.mish("199");
        assertEquals(expected, actual);
    }

    @Test
    public void testMishNotNumber() {
        String expected = "<font color=#DEDE42> QL <font color=#FFFF00> 1 </font> missions maybe from these level players (+/- 1):<font color=#66CCFF> 1 2 </font></font>";
        String actual = Level.mish("this is not a number");
        assertEquals(expected, actual);
    }
}
