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

import ao.apbot.codec.MsgPacket;

public class PrivateChannelMessagePacket extends MsgPacket {

    public static final short TYPE = 57;

    private int groupId;
    private int characterId;
    private String msg = null;
    private String str = null;

    public PrivateChannelMessagePacket() {
        super(TYPE);
    }

    public PrivateChannelMessagePacket(int groupId, String msg) {
        super(TYPE);
        this.groupId = groupId;
        this.msg = msg;
    }

    @Override
    public void decode(IoBuffer buff) {
        this.groupId = buff.getInt();
        this.characterId = buff.getInt();
        this.msg = decodeString(buff);
        this.str = decodeString(buff);
    }

    @Override
    public void encode(IoBuffer buff) {
        buff.putInt(groupId);
        encodeString(buff, msg);
        encodeString(buff, str);
    }

    public int getGroupId() {
        return groupId;
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
        return String.format("%s %s Group[%s] Id[%s] ", super.toString(), msg, groupId, characterId);
    }
}