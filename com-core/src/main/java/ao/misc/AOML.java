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

public class AOML {
    public static String Blob(String name, String content) {
        String message = "<a href=\"text://";
        message += content.replaceAll("\"", "'");
        message += "\">" + name + "</a>";
        return message;
    }

    public static String Chatcmd(String name, String content) {
        String message = "<a href='chatcmd://";
        content = content.replaceAll("<", "&lt;");
        content = content.replaceAll(">", "&gt;");
        message += content.replaceAll("\'", "&#39;");
        message += "\'>" + name + "</a>";
        return message;
    }

    /*
     * Cleans a string with html encoding characters
     */
    public static String cleanString(String s) {
        return s.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
}
