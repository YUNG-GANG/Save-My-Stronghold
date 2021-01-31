package com.yungnickyoung.minecraft.savemystronghold.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StrongholdPieces;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(StrongholdPieces.Stronghold.class)
public abstract class StrongholdMixin extends StructurePiece {
    protected StrongholdMixin(IStructurePieceType p_i51342_1_, int p_i51342_2_) {
        super(p_i51342_1_, p_i51342_2_);
    }

    @Override
    protected void fillWithBlocks(ISeedReader worldIn, MutableBoundingBox boundingboxIn, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, BlockState boundaryBlockState, BlockState insideBlockState, boolean existingOnly) {
        super.fillWithBlocks(worldIn, boundingboxIn, xMin, yMin, zMin, xMax, yMax, zMax, boundaryBlockState, insideBlockState, false);
    }

    @Override
    protected void fillWithRandomizedBlocks(ISeedReader worldIn, MutableBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean alwaysReplace, Random rand, StructurePiece.BlockSelector blockselector) {
        super.fillWithRandomizedBlocks(worldIn, boundingboxIn, minX, minY, minZ, maxX, maxY, maxZ, false, rand, blockselector);
    }
}