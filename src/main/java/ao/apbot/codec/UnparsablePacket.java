/*
 * UnparsablePacket.java
 *
 * Created on May 13, 2007, 12:40 PM
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

package ao.apbot.codec;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * AOUnparsablePacket is used to encapsulate packet data that could not
 * successfully be parsed for whatever reason.
 *
 * @author Paul Smith
 */
public class UnparsablePacket extends Fact {

    private byte[] data;

    public UnparsablePacket(short type) {
        super(type);
    }

    @Override
    public void decode(IoBuffer buff) {
        this.data = new byte[buff.getShort()];
        buff.get(data);
    }
}