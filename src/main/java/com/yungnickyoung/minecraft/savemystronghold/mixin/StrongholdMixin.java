package com.yungnickyoung.minecraft.savemystronghold.mixin;

import com.yungnickyoung.minecraft.savemystronghold.SaveMyStronghold;
import net.minecraft.block.BlockState;
import net.minecraft.structure.StrongholdGenerator;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.math.BlockBox;
import net.minecraft.world.StructureWorldAccess;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(StrongholdGenerator.Piece.class)
public abstract class StrongholdMixin extends StructurePiece {
    protected StrongholdMixin(StructurePieceType type, int length) {
        super(type, length);
    }

    @Override
    protected void fillWithOutline(StructureWorldAccess structureWorldAccess, BlockBox blockBox, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, BlockState outline, BlockState inside, boolean cantReplaceAir) {
        super.fillWithOutline(structureWorldAccess, blockBox, minX, minY, minZ, maxX, maxY, maxZ, outline, inside, false);
    }

    @Override
    protected void fillWithOutline(StructureWorldAccess structureWorldAccess, BlockBox blockBox, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean cantReplaceAir, Random random, BlockRandomizer blockRandomizer) {
        super.fillWithOutline(structureWorldAccess, blockBox, minX, minY, minZ, maxX, maxY, maxZ, false, random, blockRandomizer);
    }
}