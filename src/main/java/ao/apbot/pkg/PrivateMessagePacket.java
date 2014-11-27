/*
 * PrivateMessagePacket.java
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

package ao.apbot.pkg;

import org.apache.mina.core.buffer.IoBuffer;

import ao.apbot.codec.Fact;
import ao.apbot.codec.MsgPacket;

/**
 * <p>
 * {@code AOPrivateMessagePacket} is sent back and forth between the AO server
 * and client when private messages are sent and received by the client.
 * </p>
 *
 * <p>
 * FIXME: What is the second string in this packet used for? Can the message
 * sent/received ever be null?
 * </p>
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: ISS <br>
 * DIRECTION: in/out
 * </p>
 *
 * @author Paul Smith
 */
public class PrivateMessagePacket extends MsgPacket {

	public static final short TYPE = 30;

	private int characterId;
	private String msg = null;
	private String str = null;

	public PrivateMessagePacket() {
		super(TYPE);
	}

	public PrivateMessagePacket(int characterId, String msg) {
		super(TYPE);
		this.characterId = characterId;
		this.msg = msg;
	}

	@Override
	public void encode(IoBuffer buff) {
		buff.putInt(characterId);
		encodeString(buff, msg);
		encodeString(buff, str);
	}

	@Override
	public void decode(IoBuffer buff) {
		this.characterId = buff.getInt();
		this.msg = decodeString(buff);
		this.str = decodeString(buff);
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
		return String.format("%s %s id[%s] ", super.toString(), msg, characterId);
	}

	@Override
	public MsgPacket getReply(String msg) {
		return new PrivateMessagePacket(characterId, msg);
	}

}