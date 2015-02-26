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
package ao.apbot.pkg.auth;

import org.apache.mina.core.buffer.IoBuffer;

import ao.apbot.codec.Fact;
import ao.apbot.codec.LoginKeyGenerator;

/**
 * <p>
 * LoginRequestPacket is sent from the client to the AO server in an attempt to
 * authenticate the client.
 * </p>
 *
 * <p>
 * PACKET TYPE: {@value #TYPE} <br>
 * FORMAT: ISS <br>
 * DIRECTION: out
 * </p>
 */
public class LoginRequestPacket extends Fact {

    public static final short TYPE = 2;

    private int version;
    private String username;
    private String key;

    public LoginRequestPacket(LoginSeedPacket seed, String username, String password) {
        super(TYPE);
        this.version = LoginKeyGenerator.PROTOCOL_VERSION;
        this.username = username;
        this.key = LoginKeyGenerator.generateLoginKey(seed.getServerSeed(), username, password);
    }

    public void encode(IoBuffer buff) {
        // buff.allocate(username.length() + key.length());
        // buff.setAutoExpand(true);
        // buff.order(ByteOrder.BIG_ENDIAN);
        buff.putInt(version);
        encodeString(buff, username);
        encodeString(buff, key);
    }

    @Override
    public String toString() {
        return String.format("%s V[%s] %s K[%s]", super.toString(), version, username, key);
    }
}
