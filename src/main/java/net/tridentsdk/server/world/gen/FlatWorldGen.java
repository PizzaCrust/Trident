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

package net.tridentsdk.server.world.gen;

import net.tridentsdk.base.Substance;
import net.tridentsdk.util.TridentLogger;
import net.tridentsdk.world.gen.AbstractGenerator;
import net.tridentsdk.world.gen.ChunkTile;

/**
 * Generates a flat world using
 */
public class FlatWorldGen extends AbstractGenerator {
    @Override
    public int height(int x, int z) {
        return 4;
    }

    @Override
    public ChunkTile atCoordinate(int x, int y, int z) {
        switch (y) {
            case 0:
                return ChunkTile.create(x, y, z, Substance.BEDROCK);
            case 1:
            case 2:
                return ChunkTile.create(x, y, z, Substance.DIRT);
            case 3:
                return ChunkTile.create(x, y, z, Substance.GRASS);
            default:
                TridentLogger.error(new IllegalArgumentException("Cannot parse over/under 4 block height for flats"));
        }

        return null;
    }
}
