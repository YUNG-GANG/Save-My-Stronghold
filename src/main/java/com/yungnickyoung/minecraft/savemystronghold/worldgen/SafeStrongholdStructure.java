package com.yungnickyoung.minecraft.savemystronghold.worldgen;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Function;

public class SafeStrongholdStructure extends StrongholdStructure {
    private final List<StructureStart> structureStarts = Lists.newArrayList();

    public SafeStrongholdStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51428_1_) {
        super(p_i51428_1_);
    }

    @Nonnull
    @Override
    public Structure.IStartFactory getStartFactory() {
        return SafeStrongholdStructure.SafeStart::new;
    }

    public static class SafeStart extends StrongholdStructure.Start {
        public SafeStart(Structure<?> p_i50780_1_, int p_i50780_2_, int p_i50780_3_, MutableBoundingBox p_i50780_5_, int p_i50780_6_, long p_i50780_7_) {
            super(p_i50780_1_, p_i50780_2_, p_i50780_3_, p_i50780_5_, p_i50780_6_, p_i50780_7_);
        }

        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            int i = 0;
            long j = generator.getSeed();

            while (true) {
                this.components.clear();
                this.bounds = MutableBoundingBox.getNewBoundingBox();
                this.rand.setLargeFeatureSeed(j + (long)(i++), chunkX, chunkZ);
                SafeStrongholdPieces.prepareStructurePieces();
                SafeStrongholdPieces.Stairs2 strongholdpieces$stairs2 = new SafeStrongholdPieces.Stairs2(this.rand, (chunkX << 4) + 2, (chunkZ << 4) + 2);
                this.components.add(strongholdpieces$stairs2);
                strongholdpieces$stairs2.buildComponent(strongholdpieces$stairs2, this.components, this.rand);
                List<StructurePiece> list = strongholdpieces$stairs2.pendingChildren;

                while(!list.isEmpty()) {
                    int k = this.rand.nextInt(list.size());
                    StructurePiece structurepiece = list.remove(k);
                    structurepiece.buildComponent(strongholdpieces$stairs2, this.components, this.rand);
                }

                this.recalculateStructureSize();
                this.func_214628_a(generator.getSeaLevel(), this.rand, 10);
                if (!this.components.isEmpty() && strongholdpieces$stairs2.strongholdPortalRoom != null) {
                    break;
                }
            }

            ((SafeStrongholdStructure)this.getStructure()).structureStarts.add(this);
        }
    }
}
