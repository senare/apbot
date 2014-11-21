/*
 * VicinityMessagePacket.java
 *
 * Created on March 28, 2008, 11:42 AM
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
 * {@code VicinityMessagePacket} is sent from the AO server to the client when
 * somebody says something in vicinity.
 * </p>
 *
 * <p>
 * FIXME: What is the second string in this packet used for? Can the message
 * sent/recieved ever be null?
 * </p>
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: ISS <br>
 * DIRECTION: in
 * </p>
 *
 * @author Paul Smith
 */
public class VicinityMessagePacket extends Fact {

    public static final short TYPE = 34;

    private int characterId;
    private String msg;
    private String str;

    public VicinityMessagePacket() {
        super(TYPE);
    }

    @Override
    public void decode(IoBuffer buff) {
        this.characterId = buff.getInt();
        msg = decodeString(buff);
        str = decodeString(buff);
    }

    public int getCharacterId() {
        return characterId;
    }

    public String getMsg() {
        return msg;
    }

    public String getStr() {
        return str;
    }

    @Override
    public String toString() {
        return String.format("%s Id[%s] %s", super.toString(), characterId, msg);
    }

}