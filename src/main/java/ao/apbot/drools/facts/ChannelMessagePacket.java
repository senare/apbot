/*
 * ChannelMessagePacket.java
 *
 * Created on May 13, 2007, 5:36 PM
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
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: GISS/GSS <br>
 * DIRECTION: in/out
 * </p>
 *
 * @author Paul Smith
 */
public class ChannelMessagePacket extends Fact {

	public static final short TYPE = 65;

	private byte[] groupId = new byte[5];
	private int characterId;
	private String msg = null;
	private String str = null;

	public ChannelMessagePacket() {
		super(TYPE);
	}

	public ChannelMessagePacket(byte[] groupId, String msg) {
		super(TYPE);
		this.groupId = groupId;
		this.msg = msg;
	}

	@Override
	public void encode(IoBuffer buff) {
		buff.put(groupId);
		encodeString(buff, msg);
		encodeString(buff, str);
	}

	@Override
	public void decode(IoBuffer buff) {
		buff.get(groupId);
		this.characterId = buff.getInt();
		this.msg = decodeString(buff);
		this.str = decodeString(buff);
	}

	public byte[] getGroupId() {
		return groupId;
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
		return String.format("%s %s Group[%s] Id[%s] ", super.toString(), msg, groupId, characterId);
	}
}