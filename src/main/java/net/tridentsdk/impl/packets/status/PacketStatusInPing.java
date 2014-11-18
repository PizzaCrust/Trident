/*
 *     Trident - A Multithreaded Server Alternative
 *     Copyright (C) 2014, The TridentSDK Team
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.tridentsdk.impl.packets.status;

import io.netty.buffer.ByteBuf;
import net.tridentsdk.impl.netty.ClientConnection;
import net.tridentsdk.impl.netty.Codec;
import net.tridentsdk.impl.netty.packet.InPacket;
import net.tridentsdk.impl.netty.packet.Packet;
import net.tridentsdk.impl.netty.packet.PacketType;
import net.tridentsdk.impl.netty.protocol.Protocol;

/**
 * Represents a ping packet sent in from the client
 *
 * @author The TridentSDK Team
 */
public class PacketStatusInPing extends InPacket {
    /**
     * System time of the client (ms)
     */
    protected long time;

    @Override
    public int getId() {
        return 0x01;
    }

    @Override
    public Packet decode(ByteBuf buf) {
        this.time = Codec.readVarInt64(buf);

        return this;
    }

    @Override
    public void handleReceived(ClientConnection connection) {
        connection.sendPacket(new PacketStatusOutPing().set("clientTime", this.time));
        connection.setStage(Protocol.ClientStage.LOGIN);
    }

    /**
     * TODO not an expert on this lol - AgentTroll
     */
    public long getTime() {
        return this.time;
    }

    @Override
    public PacketType getType() {
        return PacketType.IN;
    }
}