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
import ao.misc.CharacterInfo;

/**
 * <p>
 * LoginSelectPacket is sent from the client to the AO Server in response to a
 * ChararacterListPacket, in order to login the client.
 * </p>
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: I <br>
 * DIRECTION: out
 * </p>
 *
 */
public class LoginSelectPacket extends Fact {

    public static final short TYPE = 3;

    private int id;

    public LoginSelectPacket(CharacterListPacket characterListPacket, String handle) {
        super(TYPE);
        id = characterListPacket.getCharacters().stream().filter(t -> t.getName().equals(handle)).map(CharacterInfo::getID).findFirst().get();
    }

    @Override
    public void encode(IoBuffer buff) {
        buff.putInt(id);
    }

    @Override
    public String toString() {
        return String.format("%s Id[%s]", super.toString(), id);
    }
}