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
package ao.misc;

public class NameFormat {
    public static String format(String s) {
        String[] tokens = s.toLowerCase().split(" ");
        String temp = "";
        for (String token : tokens) {
            temp = temp + " " + token.substring(0, 1).toUpperCase() + token.substring(1);
        }
        temp = temp.substring(1);
        return temp;
    }
}
