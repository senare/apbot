/*
 * CharacterListPacket.java
 *
 * Created on May 13, 2007, 2:05 PM
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.mina.core.buffer.IoBuffer;

import ao.apbot.drools.Fact;
import ao.protocol.CharacterInfo;

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
 *
 * @author Paul Smith
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
