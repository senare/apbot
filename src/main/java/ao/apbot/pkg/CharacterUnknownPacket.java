/*
 * CharacterUnknownPacket.java
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
 * ???
 * </p>
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: I <br>
 * DIRECTION: in
 * </p>
 *
 * @author Paul Smith
 */
public class CharacterUnknownPacket extends Fact {

    public static final short TYPE = 10;

    public CharacterUnknownPacket() {
        super(TYPE);
    }

    private int unknownCharacterId;

    @Override
    public void decode(IoBuffer buff) {
        this.unknownCharacterId = buff.getInt();
    }

    public int getUnknownCharacterId() {
        return unknownCharacterId;
    }

    @Override
    public String toString() {
        return String.format("%s UnknownId[%s]", super.toString(), unknownCharacterId);
    }

}