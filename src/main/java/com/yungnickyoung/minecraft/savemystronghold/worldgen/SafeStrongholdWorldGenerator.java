package com.yungnickyoung.minecraft.savemystronghold.worldgen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.List;
import java.util.Random;

public class SafeStrongholdWorldGenerator extends WorldGenerator implements IWorldGenerator {

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (!MapGenSafeStronghold.foundStronghold) return;
        if (chunkX != MapGenSafeStronghold.foundStrongholdX) return;
        if (chunkZ != MapGenSafeStronghold.foundStrongholdZ) return;

        MapGenSafeStronghold.Start mapgenstronghold$start = MapGenSafeStronghold.sStart;

        for (mapgenstronghold$start.buildStronghold();
             mapgenstronghold$start.getComponents().isEmpty()
                     || ((StructureSafeStrongholdPieces.Stairs2) mapgenstronghold$start.getComponents().get(0)).strongholdPortalRoom == null;
             mapgenstronghold$start.buildStronghold()) {
            ;
        }

        MapGenSafeStronghold.foundStronghold = false;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        return false;
    }
}
