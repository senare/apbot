/*
 * PrivateChannelAcceptPacket.java
 *
 * Created on July 11, 2010, 2:30 PM
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

public class PrivateChannelAcceptPacket extends Fact {

    public static final short TYPE = 52;

    private int id;

    public PrivateChannelAcceptPacket(int channelHostId) {
        super(TYPE);
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
