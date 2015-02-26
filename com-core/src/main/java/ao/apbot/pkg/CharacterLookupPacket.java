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
 * {@code CharacterLookupPacket} is sent to the AO server to request the ID of a
 * character who's name is known.
 * </p>
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: S <br>
 * DIRECTION: out
 * </p>
 *
 */
public class CharacterLookupPacket extends Fact {

    public static final short TYPE = 21;

    private String name;
    private int characterId;

    public CharacterLookupPacket() {
        super(TYPE);
    }

    public CharacterLookupPacket(String name) {
        super(TYPE);
        this.name = name;
    }

    @Override
    public void encode(IoBuffer buff) {
        encodeString(buff, name);
    }

    @Override
    public void decode(IoBuffer buff) {
        this.characterId = buff.getInt();
        this.name = decodeString(buff);
    }

    public String getName() {
        return name;
    }

    public int getCharacterId() {
        return characterId;
    }

    @Override
    public String toString() {
        return String.format("%s %s id[%s] ", super.toString(), name, characterId);
    }
}
