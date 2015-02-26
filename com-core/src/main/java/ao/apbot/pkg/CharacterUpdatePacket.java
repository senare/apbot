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
 * {@code CharacterUpdatePacket}s are periodically sent from the AO server to
 * the client. One is sent when the client successfully logs in. They are also
 * sent before a client recieves a message from a user.
 * </p>
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: IS <br>
 * DIRECTION: in
 * </p>
 *
 */
public class CharacterUpdatePacket extends Fact {

    public static final short TYPE = 20;

    private int characterId;
    private String name;

    public CharacterUpdatePacket() {
        super(TYPE);
    }

    @Override
    public void decode(IoBuffer buff) {
        this.characterId = buff.getInt();
        this.name = decodeString(buff);
    }

    public int getCharacterId() {
        return characterId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s ", super.toString(), name, characterId);
    }
}