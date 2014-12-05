/*
 * CharacterUpdatePacket.java
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
 * @author Paul Smith
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