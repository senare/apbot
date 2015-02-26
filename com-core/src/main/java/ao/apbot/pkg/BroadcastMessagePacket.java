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
package ao.apbot.pkg;

import org.apache.mina.core.buffer.IoBuffer;

import ao.apbot.codec.Fact;

/**
 * <p>
 * {@code BroadcastMessagePacket} is sent from the server to the client when an
 * anonymous character says something in vicinity. Often times, these are system
 * messages.
 * </p>
 *
 * <p>
 * FIXME: What are the first and second strings in this packet used for? Can the
 * message sent/recieved ever be null?
 * </p>
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: SSS <br>
 * DIRECTION: in
 * </p>
 *
 */
public class BroadcastMessagePacket extends Fact {

    public static final short TYPE = 35;

    private String source;
    private String msg;
    private String str;

    public BroadcastMessagePacket() {
        super(TYPE);
    }

    @Override
    public void decode(IoBuffer buff) {
        source = decodeString(buff);
        msg = decodeString(buff);
        str = decodeString(buff);
    }

    public String getSource() {
        return source;
    }

    public String getMsg() {
        return msg;
    }

    public String getStr() {
        return str;
    }

    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), msg);
    }
}