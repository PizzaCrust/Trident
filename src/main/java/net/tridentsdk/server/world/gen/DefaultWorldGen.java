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
import net.tridentsdk.concurrent.SelectableThreadPool;
import net.tridentsdk.server.concurrent.ThreadsHandler;
import net.tridentsdk.server.world.ChunkSection;
import net.tridentsdk.server.world.WorldUtils;
import net.tridentsdk.util.TridentLogger;
import net.tridentsdk.world.ChunkLocation;
import net.tridentsdk.world.gen.ChunkGenerator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * Default world generator engine for Trident
 *
 * @author The TridentSDK Team
 */
public class DefaultWorldGen extends ChunkGenerator {
    private final SimplexOctaveGenerator generator = new SimplexOctaveGenerator(12, 0.5, (int) seed);
    private final SelectableThreadPool executor = ThreadsHandler.genExecutor();

    public DefaultWorldGen(long seed) {
        super(seed);
    }

    @Override
    public char[][] generateBlocks(final ChunkLocation location, AtomicReferenceArray<Integer> heights) {
        final char[][] data = new char[16][ChunkSection.LENGTH];
        final CountDownLatch release = new CountDownLatch(16);

        for (int x = 0; x < 16; x++) {
            final int finalX = x;

            executor.execute(() -> {
                for (int z = 0; z < 16; z++) {
                    final int i = WorldUtils.intScale(0, 140, generator.noise(finalX + (location.x() << 4), z + (location.z() << 4))) - 20;
                    heights.set(WorldUtils.heightIndex(finalX, z), i);

                    if (i < 40) {
                        for (int j = i; j <= 40; j++) {
                            data[j / 16][WorldUtils.blockArrayIndex(finalX, j % 16, z)] = Substance.WATER.asExtended();
                        }
                    }

                    for (int y = 0; y <= i; y++) {
                        if (i < 40) {
                            if (y == i) {
                                data[y / 16][WorldUtils.blockArrayIndex(finalX, y % 16, z)] = Substance.SAND.asExtended();
                                continue;
                            }
                        }

                        if (y == i) {
                            data[y / 16][WorldUtils.blockArrayIndex(finalX, i % 16, z)] = Substance.GRASS.asExtended();
                        } else
                            data[y / 16][WorldUtils.blockArrayIndex(finalX, y % 16, z)] = Substance.DIRT.asExtended();
                    }
                }

                release.countDown();
            });
        }

        try {
            release.await();
        } catch (InterruptedException e) {
            TridentLogger.get().error(e);
            return null;
        }

        return data;
    }

    @Override
    public byte[][] generateData(ChunkLocation location) {
        return new byte[0][];
    }
}
