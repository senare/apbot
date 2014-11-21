/*
 * LoginSelectPacket.java
 *
 * Created on May 12, 2007, 10:37 PM
 *************************************************************************
 * Copyright 2008 Paul Smith
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ao.apbot.drools.facts.auth;

import org.apache.mina.core.buffer.IoBuffer;

import ao.apbot.drools.Fact;
import ao.protocol.CharacterInfo;

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
 * @author Paul Smith
 * @see ao.protocol.auth.CharacterListPacket
 * @see ao.protocol.auth.LoginErrorPacket
 * @see ao.protocol.auth.LoginOkPacket
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