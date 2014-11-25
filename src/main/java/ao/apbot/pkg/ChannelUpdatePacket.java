/*
 * ChannelUpdatePacket.java
 *
 * Created on March 28, 2008, 1:47 PM
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
 * {@code ChannelUpdatePacket} is sent from the server to the client notifying a
 * change to available channels
 * </p>
 *
 * <p>
 * FIXME: What is the last string used for?
 * </p>
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: GSIS <br>
 * DIRECTION: in
 * </p>
 *
 * @author Paul Smith
 * @see ao.apbot.pkg.ChannelMessagePacket
 */
public class ChannelUpdatePacket extends Fact {

    public static final short TYPE = 60;

    private byte[] groupId = new byte[5];
    private String groupName;
    private int groupStatus;
    private String str;

    public ChannelUpdatePacket() {
        super(TYPE);
    }

    @Override
    public void decode(IoBuffer buff) {
        buff.get(groupId);
        groupName = decodeString(buff);
        groupStatus = buff.getInt();
        str = decodeString(buff);
    }

    public byte[] getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getGroupStatus() {
        return groupStatus;
    }

    public String getStr() {
        return str;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s ", super.toString(), groupName, groupStatus);
    }
}