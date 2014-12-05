/*
 * LoginSeedPacket.java
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

package ao.apbot.pkg.auth;

import org.apache.mina.core.buffer.IoBuffer;

import ao.apbot.codec.Fact;

/**
 * <p>
 * </p>
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: S <br>
 * DIRECTION: in/out
 * </p>
 *
 * @author Paul Smith
 */
public class PingPacket extends Fact {

    public static final short TYPE = 100;

    private String message;

    public PingPacket() {
        super(TYPE);
    }

    public PingPacket(String message) {
        super(TYPE);
        this.message = message;
    }

    @Override
    public void decode(IoBuffer buff) {
        this.message = decodeString(buff);
    }

    @Override
    public void encode(IoBuffer buff) {
        encodeString(buff, message);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), message);
    }
}
