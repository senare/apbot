/*
 * BroadcastMessagePacket.java
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
package ao.apbot.pkg;

import org.apache.mina.core.buffer.IoBuffer;

import ao.apbot.codec.Fact;

/**
 * <p>
 * {@code BroadcastMessagePacket} is sent from the server to the client when an
 * anonymous character says something in vicinity. Often times, these are system
 * messages.
 * </p>
 *
 * <p>
 * FIXME: What are the first and second strings in this packet used for? Can the
 * message sent/recieved ever be null?
 * </p>
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: SSS <br>
 * DIRECTION: in
 * </p>
 *
 * @author Paul Smith
 */
public class BroadcastMessagePacket extends Fact {

    public static final short TYPE = 35;

    private String source;
    private String msg;
    private String str;

    public BroadcastMessagePacket() {
        super(TYPE);
    }

    @Override
    public void decode(IoBuffer buff) {
        source = decodeString(buff);
        msg = decodeString(buff);
        str = decodeString(buff);
    }

    public String getSource() {
        return source;
    }

    public String getMsg() {
        return msg;
    }

    public String getStr() {
        return str;
    }

    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), msg);
    }
}