package com.yungnickyoung.minecraft.savemystronghold.worldgen;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.List;
import java.util.Random;

public class MapGenSafeStronghold extends MapGenStronghold {
    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        MapGenSafeStronghold.Start mapgenstronghold$start;

        for (mapgenstronghold$start = new MapGenSafeStronghold.Start(this.world, this.rand, chunkX, chunkZ);
             mapgenstronghold$start.getComponents().isEmpty()
                     || ((StructureSafeStrongholdPieces.Stairs2) mapgenstronghold$start.getComponents().get(0)).strongholdPortalRoom == null;
             mapgenstronghold$start = new MapGenSafeStronghold.Start(this.world, this.rand, chunkX, chunkZ)) {
            ;
        }

        return mapgenstronghold$start;
    }

    public static class Start extends StructureStart {
        public Start() {
        }

        public Start(World worldIn, Random random, int chunkX, int chunkZ) {
            super(chunkX, chunkZ);

            MapGenStructureIO.registerStructure(Start.class, "Stronghold");
            StructureSafeStrongholdPieces.registerStrongholdPieces();

            StructureSafeStrongholdPieces.prepareStructurePieces();
            StructureSafeStrongholdPieces.Stairs2 structurestrongholdpieces$stairs2 = new StructureSafeStrongholdPieces.Stairs2(0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2);
            this.components.add(structurestrongholdpieces$stairs2);
            structurestrongholdpieces$stairs2.buildComponent(structurestrongholdpieces$stairs2, this.components, random);
            List<StructureComponent> list = structurestrongholdpieces$stairs2.pendingChildren;

            while (!list.isEmpty()) {
                int i = random.nextInt(list.size());
                StructureComponent structurecomponent = list.remove(i);
                structurecomponent.buildComponent(structurestrongholdpieces$stairs2, this.components, random);
            }

            this.updateBoundingBox();
            this.markAvailableHeight(worldIn, random, 10);
        }
    }
}
