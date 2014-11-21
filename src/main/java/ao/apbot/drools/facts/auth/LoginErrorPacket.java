/*
 * LoginErrorPacket.java
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
package ao.apbot.drools.facts.auth;

import org.apache.mina.core.buffer.IoBuffer;

import ao.apbot.drools.Fact;

/**
 * <p>
 * LoginErrorPacket is sent from the AO server to the client if an error
 * occurred while attempting to authenticate or login the client.
 * </p>
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: S <br>
 * DIRECTION: in
 * </p>
 *
 * @author Paul Smith
 */
public class LoginErrorPacket extends Fact {

    public static final short TYPE = 6;

    private String msg;

    public LoginErrorPacket() {
        super(TYPE);
    }

    @Override
    public void decode(IoBuffer buff) {
        msg = decodeString(buff);
    }

    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), msg);
    }
}
