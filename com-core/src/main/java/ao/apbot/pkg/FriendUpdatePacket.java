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

public class FriendUpdatePacket extends Fact {

    public static final short TYPE = 40;

    private int characterId;
    private int online;
    private String flags;

    public FriendUpdatePacket() {
        super(TYPE);
    }

    @Override
    public void decode(IoBuffer buff) {
        this.characterId = buff.getInt();
        this.online = buff.getInt();
        this.flags = decodeString(buff);
    }

    @Override
    public void encode(IoBuffer buff) {
        buff.putInt(characterId);
        encodeString(buff, flags);
    }

    public int getCharacterId() {
        return characterId;
    }

    public boolean isOnline() {
        return !(online == 0);
    }

    public boolean isFriend() {
        return !(flags.compareTo("\0") == 0);
    }

    @Override
    public String toString() {
        return String.format("%s Id[%s] %s %s", super.toString(), characterId, isOnline() ? "Online" : "Offline", isFriend() ? "Friend" : " nope ");
    }
}
