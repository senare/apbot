/*
 * CharacterLookupPacket.java
 *
 * Created on March 22, 2008, 12:25 PM
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

package ao.apbot.drools.facts;

import org.apache.mina.core.buffer.IoBuffer;

import ao.apbot.drools.Fact;

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
 * @author Paul Smith
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
