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

import org.apache.mina.core.buffer.IoBuffer;

import ao.apbot.codec.Fact;

/**
 * <p>
 * </p>
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: S <br>
 * DIRECTION: in/out
 * </p>
 */
public class PingPacket extends Fact {

    public static final short TYPE = 100;

    private String message;

    public PingPacket() {
        super(TYPE);
    }

    public PingPacket(String message) {
        super(TYPE);
        this.message = message;
    }

    @Override
    public void decode(IoBuffer buff) {
        this.message = decodeString(buff);
    }

    @Override
    public void encode(IoBuffer buff) {
        encodeString(buff, message);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), message);
    }
}
