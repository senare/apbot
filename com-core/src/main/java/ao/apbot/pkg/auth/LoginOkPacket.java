/*
 * LoginOkPacket.java
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

import ao.apbot.codec.Fact;

/**
 * <p>
 * LoginOkPacket is sent from the AO server to the client if the client was
 * successfully logged in.
 * </p>
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: empty <br>
 * DIRECTION: in
 * </p>
 *
 * @author Paul Smith
 * @see ao.protocol.auth.LoginErrorPacket
 * @see ao.protocol.auth.LoginSelectPacket
 */
public class LoginOkPacket extends Fact {

    public static final short TYPE = 5;

    public LoginOkPacket() {
        super(TYPE);
    }
}
