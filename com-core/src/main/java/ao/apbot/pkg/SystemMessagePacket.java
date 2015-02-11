/*
 * SystemMessagePacket.java
 *
 * Created on September 23, 2010, 12:16 PM
 *************************************************************************
 * Copyright 2010 Kevin Kendall
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

public class SystemMessagePacket extends Fact {

    public static final short TYPE = 37;

    private int clientId;
    private int windowId;
    private int messageId;
    private String msg;

    public SystemMessagePacket() {
        super(TYPE);
    }

    @Override
    public void decode(IoBuffer buff) {
        clientId = buff.getInt();
        windowId = buff.getInt();
        messageId = buff.getInt();
        msg = decodeString(buff);
    }

    public int getClientId() {
        return clientId;
    }

    public int getWindowId() {
        return windowId;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), msg);
    }
}