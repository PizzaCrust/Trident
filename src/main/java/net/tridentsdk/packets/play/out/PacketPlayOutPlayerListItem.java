/*
 * Copyright (c) 2014, The TridentSDK Team
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 *     3. Neither the name of TridentSDK nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.tridentsdk.packets.play.out;

import io.netty.buffer.ByteBuf;
import net.tridentsdk.server.netty.Codec;
import net.tridentsdk.server.netty.packet.OutPacket;

import java.util.UUID;

public class PacketPlayOutPlayerListItem extends OutPacket {

    private int action;
    private PlayerListDataBuilder[] playerListData;

    @Override
    public int getId() {
        return 0x37;
    }

    public int getAction() {
        return action;
    }

    public PlayerListDataBuilder[] getPlayerListData() {
        return playerListData;
    }

    @Override
    public void encode(ByteBuf buf) {
        Codec.writeVarInt32(buf, action);
        Codec.writeVarInt32(buf, playerListData.length);

        for(PlayerListDataBuilder data : playerListData) {
            data.write(buf);
        }
    }

    public class PlayerListDataBuilder {
        private UUID id;
        private Object[] values;

        public PlayerListDataBuilder() {
        }

        public UUID getId() {
            return id;
        }

        public PlayerListDataBuilder setId(UUID id) {
            this.id = id;

            return this;
        }

        public Object[] getValues() {
            return values;
        }

        public PlayerListDataBuilder setValues(Object[] values) {
            this.values = values;

            return this;
        }

        public void write(ByteBuf buf) {
            buf.writeLong(id.getMostSignificantBits());
            buf.writeLong(id.getLeastSignificantBits());

            // rip in organize
            for(Object o : values) {
                switch(o.getClass().getSimpleName()) {
                    case "String":
                        Codec.writeString(buf, (String) o);
                        break;

                    case "Integer":
                        Codec.writeVarInt32(buf, (Integer) o);
                        break;

                    case "Boolean":
                        buf.writeBoolean((Boolean) o);
                        break;

                    default:
                        // ignore bad developers
                        break;
                }
            }
        }
    }
}