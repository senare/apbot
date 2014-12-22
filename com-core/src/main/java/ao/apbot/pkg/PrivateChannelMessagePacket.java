/*
 * PrivateChannelMessagePacket.java
 *
 * Created on July 10, 2010, 2:10 PM
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

import ao.apbot.codec.MsgPacket;

public class PrivateChannelMessagePacket extends MsgPacket {

	public static final short TYPE = 57;

	private int groupId;
	private int characterId;
	private String msg = null;
	private String str = null;

	public PrivateChannelMessagePacket() {
		super(TYPE);
	}

	public PrivateChannelMessagePacket(int groupId, String msg) {
		super(TYPE);
		this.groupId = groupId;
		this.msg = msg;
	}

	@Override
	public void decode(IoBuffer buff) {
		this.groupId = buff.getInt();
		this.characterId = buff.getInt();
		this.msg = decodeString(buff);
		this.str = decodeString(buff);
	}

	@Override
	public void encode(IoBuffer buff) {
		buff.putInt(groupId);
		encodeString(buff, msg);
		encodeString(buff, str);
	}

	public int getGroupId() {
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