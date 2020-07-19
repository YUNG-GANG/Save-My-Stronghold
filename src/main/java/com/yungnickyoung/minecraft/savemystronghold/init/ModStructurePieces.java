package com.yungnickyoung.minecraft.savemystronghold.init;

import com.yungnickyoung.minecraft.savemystronghold.SaveMyStronghold;
import com.yungnickyoung.minecraft.savemystronghold.worldgen.SafeStrongholdPieces;
import com.yungnickyoung.minecraft.savemystronghold.worldgen.SafeStrongholdStructurePieceType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

import java.util.Locale;

public class ModStructurePieces {
    public static void init() {
        SafeStrongholdStructurePieceType.SSHFC = register("SSHFC", SafeStrongholdPieces.Corridor::new);
        SafeStrongholdStructurePieceType.SSH5C = register("SSH5C", SafeStrongholdPieces.Crossing::new);
        SafeStrongholdStructurePieceType.SSHLT = register("SSHLT", SafeStrongholdPieces.LeftTurn::new);
        SafeStrongholdStructurePieceType.SSHLI = register("SSHLI", SafeStrongholdPieces.Library::new);
        SafeStrongholdStructurePieceType.SSHPR = register("SSHPR", SafeStrongholdPieces.PortalRoom::new);
        SafeStrongholdStructurePieceType.SSHPH = register("SSHPH", SafeStrongholdPieces.Prison::new);
        SafeStrongholdStructurePieceType.SSHRT = register("SSHRT", SafeStrongholdPieces.RightTurn::new);
        SafeStrongholdStructurePieceType.SSHRC = register("SSHRC", SafeStrongholdPieces.RoomCrossing::new);
        SafeStrongholdStructurePieceType.SSHSD = register("SSHSD", SafeStrongholdPieces.Stairs::new);
        SafeStrongholdStructurePieceType.SSHSTART = register("SSHStart", SafeStrongholdPieces.Stairs2::new);
        SafeStrongholdStructurePieceType.SSHS = register("SSHS", SafeStrongholdPieces.Straight::new);
        SafeStrongholdStructurePieceType.SSHSSD = register("SSHSSD", SafeStrongholdPieces.StairsStraight::new);
        SafeStrongholdStructurePieceType.SSHCC = register("SSHCC", SafeStrongholdPieces.ChestCorridor::new);
    }

    private static IStructurePieceType register(String id, IStructurePieceType piece) {
        return Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(SaveMyStronghold.MOD_ID, id.toLowerCase(Locale.ROOT)), piece);
    }
}
