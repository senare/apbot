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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.mina.core.buffer.IoBuffer;

import ao.apbot.codec.Fact;
import ao.misc.CharacterInfo;

/**
 * <p>
 * CharacterListPacket is sent from the server to the client after the client
 * has been successfully authenticated. It contains a list of the characters
 * belonging to the account for which the client was authenticated.
 * </p>
 * 
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: isii <br>
 * DIRECTION: in
 * </p>
 */
public class CharacterListPacket extends Fact {

    public static final short TYPE = 7;

    public CharacterListPacket() {
        super(TYPE);
    }

    private List<CharacterInfo> characters = new ArrayList<>();

    @Override
    public void decode(IoBuffer buff) {
        int[] ids = new int[buff.getShort()];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = buff.getInt();
        }

        String[] names = new String[buff.getShort()];
        for (int i = 0; i < names.length; i++) {
            names[i] = decodeString(buff);
        }

        int[] levels = new int[buff.getShort()];
        for (int i = 0; i < levels.length; i++) {
            levels[i] = buff.getInt();
        }

        int[] online = new int[buff.getShort()];
        for (int i = 0; i < online.length; i++) {
            online[i] = buff.getInt();
        }

        for (int i = 0; i < ids.length; ++i) {
            characters.add(new CharacterInfo(ids[i], names[i], levels[i], online[i]));
        }
    }

    public List<CharacterInfo> getCharacters() {
        return characters;
    }

    @Override
    public String toString() {
        return String.format("%s [%s]", super.toString(), characters.stream().map(CharacterInfo::toString).collect(Collectors.joining(" , ")));
    }
}
