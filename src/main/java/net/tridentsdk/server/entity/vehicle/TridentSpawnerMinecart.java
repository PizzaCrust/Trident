/*
 * Trident - A Multithreaded Server Alternative
 * Copyright 2014 The TridentSDK Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.tridentsdk.server.entity.vehicle;

import net.tridentsdk.base.BlockSnapshot;
import net.tridentsdk.base.Position;
import net.tridentsdk.entity.traits.EntityProperties;
import net.tridentsdk.entity.types.EntityType;
import net.tridentsdk.entity.vehicle.SpawnerMinecart;
import net.tridentsdk.server.entity.TridentEntity;

import java.util.UUID;

/**
 * Represents a minecart that holds a spawner block
 *
 * @author The TridentSDK Team
 */
public class TridentSpawnerMinecart extends TridentEntity implements SpawnerMinecart {
    public TridentSpawnerMinecart(UUID uuid, Position spawnPosition) {
        super(uuid, spawnPosition);
    }

    @Override
    public EntityType spawnType() {
        return null;
    }

    @Override
    public EntityProperties appliedProperties() {
        return null;
    }

    @Override
    public BlockSnapshot displayTitle() {
        return null;
    }

    @Override
    public void setDisplayTile(BlockSnapshot blockState) {

    }

    @Override
    public int titleOffset() {
        return 0;
    }

    @Override
    public void setDisplayTileOffset(int offset) {

    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public EntityType type() {
        return EntityType.SPAWNER_MINECART;
    }
}
