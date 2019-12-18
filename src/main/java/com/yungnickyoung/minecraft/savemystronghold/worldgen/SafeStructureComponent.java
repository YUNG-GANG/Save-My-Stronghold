package com.yungnickyoung.minecraft.savemystronghold.worldgen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import org.apache.logging.log4j.LogManager;

import java.util.Random;

public abstract class SafeStructureComponent extends StructureComponent {
    public SafeStructureComponent(int type) {
        super(type);
    }

    public SafeStructureComponent() {
    }

    @Override
    protected void fillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, IBlockState boundaryBlockState, IBlockState insideBlockState, boolean existingOnly) {
        super.fillWithBlocks(worldIn, boundingboxIn, xMin, yMin, zMin, xMax, yMax, zMax, boundaryBlockState, insideBlockState, false);
    }

    @Override
    protected void fillWithRandomizedBlocks(World worldIn, StructureBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean alwaysReplace, Random rand, BlockSelector blockselector) {
        super.fillWithRandomizedBlocks(worldIn, boundingboxIn, minX, minY, minZ, maxX, maxY, maxZ, false, rand, blockselector);
    }

    @Override
    protected void randomlyRareFillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, IBlockState blockstateIn, boolean excludeAir) {
        super.randomlyRareFillWithBlocks(worldIn, boundingboxIn, minX, minY, minZ, maxX, maxY, maxZ, blockstateIn, false);
    }
}