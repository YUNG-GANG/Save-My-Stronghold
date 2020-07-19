package com.yungnickyoung.minecraft.savemystronghold.worldgen;

import com.mojang.serialization.Codec;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;

@MethodsReturnNonnullByDefault
public class SafeStrongholdStructure extends StrongholdStructure {
    public SafeStrongholdStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long p_230363_3_, SharedSeedRandom random, int x, int z, Biome biome, ChunkPos chunkPos, NoFeatureConfig config) {
        return chunkGenerator.func_235952_a_(new ChunkPos(x, z));
    }

    @Override
    public Structure.IStartFactory<NoFeatureConfig> getStartFactory() {
        return SafeStrongholdStructure.SafeStart::new;
    }

    @Override
    public String getStructureName() {
        return "Stronghold";
    }

    public static class SafeStart extends StrongholdStructure.Start {
        private final long seed;

        public SafeStart(Structure<NoFeatureConfig> structure, int chunkX, int chunkZ, MutableBoundingBox box, int references, long seed) {
            super(structure, chunkX, chunkZ, box, references, seed);
            this.seed = seed;
        }

        public void init(ChunkGenerator generator, TemplateManager templateManager, int chunkX, int chunkZ, Biome biome, NoFeatureConfig config) {
            int i = 0;

            while (true) {
                this.components.clear();
                this.bounds = MutableBoundingBox.getNewBoundingBox();
                this.rand.setLargeFeatureSeed(this.seed + (long)(i++), chunkX, chunkZ);
                SafeStrongholdPieces.prepareStructurePieces();
                SafeStrongholdPieces.Stairs2 strongholdpieces$stairs2 = new SafeStrongholdPieces.Stairs2(this.rand, (chunkX << 4) + 2, (chunkZ << 4) + 2);
                this.components.add(strongholdpieces$stairs2);
                strongholdpieces$stairs2.buildComponent(strongholdpieces$stairs2, this.components, this.rand);
                List<StructurePiece> list = strongholdpieces$stairs2.pendingChildren;

                while(!list.isEmpty()) {
                    int j = this.rand.nextInt(list.size());
                    StructurePiece structurepiece = list.remove(j);
                    structurepiece.buildComponent(strongholdpieces$stairs2, this.components, this.rand);
                }

                this.recalculateStructureSize();
                this.func_214628_a(generator.func_230356_f_(), this.rand, 10);
                if (!this.components.isEmpty() && strongholdpieces$stairs2.strongholdPortalRoom != null) {
                    break;
                }
            }
        }
    }
}
