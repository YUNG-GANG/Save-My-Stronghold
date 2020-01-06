package com.yungnickyoung.minecraft.savemystronghold.worldgen;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.List;
import java.util.Random;

public class MapGenSafeStronghold extends MapGenStronghold {
    public static World sWorld = null;
    public static Random sRand = null;
    public static Start sStart = null;

    public static boolean foundStronghold = false;
    public static int foundStrongholdX = 0;
    public static int foundStrongholdZ = 0;

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        if (sWorld == null && this.world != null)
            sWorld = this.world;
        if (sRand == null && this.rand != null)
            sRand = this.rand;

        updateCurrStronghold(chunkX, chunkZ);
        return sStart;
    }

    private void updateCurrStronghold(int chunkX, int chunkZ) {
        foundStronghold = true;
        foundStrongholdX = chunkX;
        foundStrongholdZ = chunkZ;
        sStart = new Start(sWorld, sRand, chunkX, chunkZ);
    }

    public static class Start extends StructureStart {
        public List<StructureComponent> list;
        public World world;
        public Random rand;
        public StructureSafeStrongholdPieces.Stairs2 structurestrongholdpieces$stairs2;

        public Start() {
        }

        public Start(World worldIn, Random random, int chunkX, int chunkZ) {
            super(chunkX, chunkZ);

            this.world = worldIn;
            this.rand = random;

            MapGenStructureIO.registerStructure(Start.class, "Stronghold");
            StructureSafeStrongholdPieces.registerStrongholdPieces();

            StructureSafeStrongholdPieces.prepareStructurePieces();
            structurestrongholdpieces$stairs2 = new StructureSafeStrongholdPieces.Stairs2(0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2);
            this.components.add(structurestrongholdpieces$stairs2);
            structurestrongholdpieces$stairs2.buildComponent(structurestrongholdpieces$stairs2, this.components, random);
            list = structurestrongholdpieces$stairs2.pendingChildren;

            this.updateBoundingBox();
        }

        public void buildStronghold() {
            while (!list.isEmpty()) {
                int i = rand.nextInt(list.size());
                StructureComponent structurecomponent = list.remove(i);
                structurecomponent.buildComponent(structurestrongholdpieces$stairs2, this.components, rand);
            }

            this.updateBoundingBox();
            this.markAvailableHeight(world, rand, 10);
        }
    }
}
