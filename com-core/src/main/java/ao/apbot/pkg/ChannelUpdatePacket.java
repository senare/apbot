/*
    Copyright (C) 2015 Senare

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

    contact : aperfectbot@gmail.com
    
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