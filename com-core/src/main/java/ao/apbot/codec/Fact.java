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
package ao.apbot.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;

public abstract class Fact {

    protected Charset UTF8 = Charset.forName("UTF-8");

    protected short type;

    public short getType() {
        return type;
    }

    public Fact(short type) {
        this.type = type;
    }

    public String toString() {
        return String.format("[%s]%s :", type, this.getClass().getSimpleName());
    }

    public void encode(IoBuffer buff) {
    }

    public void decode(IoBuffer buff) {
    }

    protected void encodeString(IoBuffer buff, String value) {
        if (value == null) {
            buff.putShort((short) 1);
            buff.put((byte) 0);
        } else {
            byte[] bytes = value.getBytes(UTF8);
            buff.putShort((short) bytes.length);
            buff.put(bytes);
        }
    }

    protected String decodeString(IoBuffer buff) {
        byte[] bytes = new byte[buff.getShort()];
        buff.get(bytes);
        return new String(bytes, UTF8);
    }
}
