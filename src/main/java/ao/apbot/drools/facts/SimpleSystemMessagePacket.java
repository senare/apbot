/*
 * SimpleSystemMessagePacket.java
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
 * {@code SimpleSystemMessagePacket} is sent from the AO server to the client to
 * inform the client of some event(s) or technical difficulty.
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: S <br>
 * DIRECTION: in
 * </p>
 *
 * @author Paul Smith
 * @see ao.apbot.drools.facts.PrivateMessagePacket
 * @see VicinityMessagePacket
 * @see BroadcastMessagePacket
 * @see SystemMessagePacket
 */
public class SimpleSystemMessagePacket extends Fact {

    public static final short TYPE = 36;

    private String msg;

    public SimpleSystemMessagePacket() {
        super(TYPE);
    }

    @Override
    public void decode(IoBuffer buff) {
        msg = decodeString(buff);
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), msg);
    }
}