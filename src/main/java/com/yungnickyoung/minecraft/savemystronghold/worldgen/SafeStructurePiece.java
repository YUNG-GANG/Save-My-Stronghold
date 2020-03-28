package com.yungnickyoung.minecraft.savemystronghold.worldgen;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;

import java.util.Random;

public abstract class SafeStructurePiece extends StructurePiece {
    protected SafeStructurePiece(IStructurePieceType structurePieceTypeIn, int componentTypeIn) {
        super(structurePieceTypeIn, componentTypeIn);
    }

    public SafeStructurePiece(IStructurePieceType structurePierceTypeIn, CompoundNBT nbt) {
        super(structurePierceTypeIn, nbt);
    }

    @Override
    protected void fillWithBlocks(IWorld worldIn, MutableBoundingBox boundingboxIn, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, BlockState boundaryBlockState, BlockState insideBlockState, boolean existingOnly) {
        super.fillWithBlocks(worldIn, boundingboxIn, xMin, yMin, zMin, xMax, yMax, zMax, boundaryBlockState, insideBlockState, false);
    }

    @Override
    protected void fillWithRandomizedBlocks(IWorld worldIn, MutableBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean alwaysReplace, Random rand, StructurePiece.BlockSelector blockselector) {
        super.fillWithRandomizedBlocks(worldIn, boundingboxIn, minX, minY, minZ, maxX, maxY, maxZ, false, rand, blockselector);
    }
}
