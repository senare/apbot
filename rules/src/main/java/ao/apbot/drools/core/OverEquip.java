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

public class OverEquip {

    public static String get(String oe) {
        final int skill = parse(oe);
        int a = (int) (skill / 0.8);
        int b = (int) (skill * 0.8);
        return String.format("With a skill of <font color=#ffff00> %d </font>, you will be OE above <font color=#ffff00> %d </font> skill, with a requirement of <font color=#ffff00> %d </font> skill, you can have <font color=#ffff00> %d </font> without being OE.", skill, a, skill, b);
    }

    private static int parse(String level) {
        int lvl = 0;
        try {
            lvl = Integer.valueOf(level);
        } catch (Exception x) {
        }
        return lvl;
    }
}
