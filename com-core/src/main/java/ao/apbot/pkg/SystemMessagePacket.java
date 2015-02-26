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

public class SystemMessagePacket extends Fact {

    public static final short TYPE = 37;

    private int clientId;
    private int windowId;
    private int messageId;
    private String msg;

    public SystemMessagePacket() {
        super(TYPE);
    }

    @Override
    public void decode(IoBuffer buff) {
        clientId = buff.getInt();
        windowId = buff.getInt();
        messageId = buff.getInt();
        msg = decodeString(buff);
    }

    public int getClientId() {
        return clientId;
    }

    public int getWindowId() {
        return windowId;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), msg);
    }
}