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
package ao.apbot.pkg.auth;

import ao.apbot.codec.Fact;

/**
 * <p>
 * LoginOkPacket is sent from the AO server to the client if the client was
 * successfully logged in.
 * </p>
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: empty <br>
 * DIRECTION: in
 * </p>
 */
public class LoginOkPacket extends Fact {

    public static final short TYPE = 5;

    public LoginOkPacket() {
        super(TYPE);
    }
}
