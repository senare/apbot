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

import java.util.Arrays;

import org.apache.mina.core.buffer.IoBuffer;

import ao.apbot.codec.MsgPacket;

/**
 * <p>
 * {@code AOPrivateMessagePacket} is sent back and forth between the AO server
 * and client when private messages are sent and received by the client.
 * </p>
 *
 * <p>
 * FIXME: What is the second string in this packet used for? Can the message
 * sent/received ever be null?
 * </p>
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: ISS <br>
 * DIRECTION: in/out
 * </p>
 *
 * @author Paul Smith
 */
public class PrivateMessagePacket extends MsgPacket {

    public static final short TYPE = 30;

    private int characterId;
    private String msg = null;
    private String str = null;

    public PrivateMessagePacket() {
        super(TYPE);
    }

    public PrivateMessagePacket(int characterId, String msg) {
        super(TYPE);
        this.characterId = characterId;
        this.msg = msg;
    }

    @Override
    public void encode(IoBuffer buff) {
        buff.putInt(characterId);
        encodeString(buff, msg);
        encodeString(buff, str);
    }

    @Override
    public void decode(IoBuffer buff) {
        this.characterId = buff.getInt();
        this.msg = decodeString(buff);
        this.str = decodeString(buff);
    }

    @Override
    protected void split() {
        super.split();

        if (this.command.startsWith("!")) {
            this.command = this.command.substring(1);
        }
    }

    public int getCharacterId() {
        return characterId;
    }

    public String getMsg() {
        return msg;
    }

    public String getStr() {
        return str;
    }

    @Override
    public String toString() {
        return String.format("%s %s id[%s] ", super.toString(), msg, characterId);
    }
}