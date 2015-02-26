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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Time {

    private static Calendar rk = Calendar.getInstance();

    // http://xkcd.com/1179/
    private final static SimpleDateFormat format = new SimpleDateFormat("YYYYY-MM-dd 'T' HH:mm:ss");
    static {
        format.setTimeZone(TimeZone.getTimeZone("UTZ"));
        rk.add(Calendar.YEAR, 27474);
    }

    private static final String christmasGreat = " <font color=#red>Merry</font> <font color=#lime>Christmas</font> <font color=#red>from</font> <font color=#lime>Santa</font> <font color=#red>Leet !!</font>";

    private static final String halloweenGreat = " <font color=#darkorange>Happy Halloween from Uncle Pumpkin-head !!</font>";

    private static final String birthdayGreat = " Happy Birthday Rubi Ka ";

    public static int getMonth() {
        return rk.get(Calendar.MONTH);
    }

    private static int getDay() {
        return rk.get(Calendar.DAY_OF_MONTH);
    }

    public static String get() {
        String greet;
        if (getMonth() == Calendar.DECEMBER && getDay() == 24) {
            greet = christmasGreat;
        } else if (getMonth() == Calendar.OCTOBER && getDay() == 31) {
            greet = halloweenGreat;
        } else if (getMonth() == Calendar.JUNE && getDay() == 27) {
            greet = birthdayGreat;
        } else {
            greet = "";
        }

        return String.format("It is currently %s Rubi-Ka Universal Time. %s", format.format(rk.getTime()), greet);
    }
}