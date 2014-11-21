/*
 * SimplePacketFactory.java
 *
 * Created on May 12, 2007, 3:49 PM
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

package ao.protocol.packets.utils;

import ao.db.MMDBDatabase;
import ao.protocol.packets.MalformedPacketException;
import ao.protocol.packets.Packet;
import ao.protocol.packets.UnparsablePacket;

/**
 * {@code SimplePacketFactory} is an implementation of 
 * {@link ao.protocol.packets.utils.PacketFactory} 
 * that parses packets into all known and implemented packet types.
 *
 * @author Paul Smith
 * @see #toPacket(short, byte[])
 * @see ao.protocol.packets.utils.PacketFactory
 * @see ao.protocol.packets.Packet
 */
public class SimplePacketFactory implements PacketFactory {

    private final MMDBDatabase database;
    /** Creates a new instance of SimplePacketFactory */

    public SimplePacketFactory() {
        database = null;
    }

    public SimplePacketFactory(MMDBDatabase db) {
        database = db;
    }   // end SimplePacketFactory()
    
    public Packet toPacket(short type, byte[] data) throws MalformedPacketException {
        return new UnparsablePacket(type, data, Packet.Direction.TO_CLIENT);
    }   // end toPacket()
}   // end class SimplePacketFactory
