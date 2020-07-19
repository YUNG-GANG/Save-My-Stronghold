package com.yungnickyoung.minecraft.savemystronghold.worldgen;

import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SafeStrongholdPieces {
    private static final SafeStrongholdPieces.PieceWeight[] PIECE_WEIGHTS = new SafeStrongholdPieces.PieceWeight[]{new SafeStrongholdPieces.PieceWeight(SafeStrongholdPieces.Straight.class, 40, 0), new SafeStrongholdPieces.PieceWeight(SafeStrongholdPieces.Prison.class, 5, 5), new SafeStrongholdPieces.PieceWeight(SafeStrongholdPieces.LeftTurn.class, 20, 0), new SafeStrongholdPieces.PieceWeight(SafeStrongholdPieces.RightTurn.class, 20, 0), new SafeStrongholdPieces.PieceWeight(SafeStrongholdPieces.RoomCrossing.class, 10, 6), new SafeStrongholdPieces.PieceWeight(SafeStrongholdPieces.StairsStraight.class, 5, 5), new SafeStrongholdPieces.PieceWeight(SafeStrongholdPieces.Stairs.class, 5, 5), new SafeStrongholdPieces.PieceWeight(SafeStrongholdPieces.Crossing.class, 5, 4), new SafeStrongholdPieces.PieceWeight(SafeStrongholdPieces.ChestCorridor.class, 5, 4), new SafeStrongholdPieces.PieceWeight(SafeStrongholdPieces.Library.class, 10, 2) {
        public boolean canSpawnMoreStructuresOfType(int p_75189_1_) {
            return super.canSpawnMoreStructuresOfType(p_75189_1_) && p_75189_1_ > 4;
        }
    }, new SafeStrongholdPieces.PieceWeight(SafeStrongholdPieces.PortalRoom.class, 20, 1) {
        public boolean canSpawnMoreStructuresOfType(int p_75189_1_) {
            return super.canSpawnMoreStructuresOfType(p_75189_1_) && p_75189_1_ > 5;
        }
    }};
    private static List<SafeStrongholdPieces.PieceWeight> structurePieceList;
    private static Class<? extends SafeStrongholdPieces.Stronghold> strongComponentType;
    private static int totalWeight;
    private static final SafeStrongholdPieces.Stones STRONGHOLD_STONES = new SafeStrongholdPieces.Stones();

    public static void prepareStructurePieces() {
        structurePieceList = Lists.newArrayList();

        for (PieceWeight lvt_3_1_ : PIECE_WEIGHTS) {
            lvt_3_1_.instancesSpawned = 0;
            structurePieceList.add(lvt_3_1_);
        }

        strongComponentType = null;
    }

    private static boolean canAddStructurePieces() {
        boolean lvt_0_1_ = false;
        totalWeight = 0;

        SafeStrongholdPieces.PieceWeight lvt_2_1_;
        for(Iterator<SafeStrongholdPieces.PieceWeight> var1 = structurePieceList.iterator(); var1.hasNext(); totalWeight += lvt_2_1_.pieceWeight) {
            lvt_2_1_ = var1.next();
            if (lvt_2_1_.instancesLimit > 0 && lvt_2_1_.instancesSpawned < lvt_2_1_.instancesLimit) {
                lvt_0_1_ = true;
            }
        }

        return lvt_0_1_;
    }

    private static SafeStrongholdPieces.Stronghold findAndCreatePieceFactory(Class<? extends SafeStrongholdPieces.Stronghold> p_175954_0_, List<StructurePiece> p_175954_1_, Random p_175954_2_, int p_175954_3_, int p_175954_4_, int p_175954_5_, @Nullable Direction p_175954_6_, int p_175954_7_) {
        SafeStrongholdPieces.Stronghold lvt_8_1_ = null;
        if (p_175954_0_ == SafeStrongholdPieces.Straight.class) {
            lvt_8_1_ = SafeStrongholdPieces.Straight.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        } else if (p_175954_0_ == SafeStrongholdPieces.Prison.class) {
            lvt_8_1_ = SafeStrongholdPieces.Prison.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        } else if (p_175954_0_ == SafeStrongholdPieces.LeftTurn.class) {
            lvt_8_1_ = SafeStrongholdPieces.LeftTurn.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        } else if (p_175954_0_ == SafeStrongholdPieces.RightTurn.class) {
            lvt_8_1_ = SafeStrongholdPieces.RightTurn.func_214824_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        } else if (p_175954_0_ == SafeStrongholdPieces.RoomCrossing.class) {
            lvt_8_1_ = SafeStrongholdPieces.RoomCrossing.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        } else if (p_175954_0_ == SafeStrongholdPieces.StairsStraight.class) {
            lvt_8_1_ = SafeStrongholdPieces.StairsStraight.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        } else if (p_175954_0_ == SafeStrongholdPieces.Stairs.class) {
            lvt_8_1_ = SafeStrongholdPieces.Stairs.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        } else if (p_175954_0_ == SafeStrongholdPieces.Crossing.class) {
            lvt_8_1_ = SafeStrongholdPieces.Crossing.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        } else if (p_175954_0_ == SafeStrongholdPieces.ChestCorridor.class) {
            lvt_8_1_ = SafeStrongholdPieces.ChestCorridor.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        } else if (p_175954_0_ == SafeStrongholdPieces.Library.class) {
            lvt_8_1_ = SafeStrongholdPieces.Library.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        } else if (p_175954_0_ == SafeStrongholdPieces.PortalRoom.class) {
            lvt_8_1_ = SafeStrongholdPieces.PortalRoom.createPiece(p_175954_1_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }

        return lvt_8_1_;
    }

    private static SafeStrongholdPieces.Stronghold generatePieceFromSmallDoor(SafeStrongholdPieces.Stairs2 p_175955_0_, List<StructurePiece> p_175955_1_, Random p_175955_2_, int p_175955_3_, int p_175955_4_, int p_175955_5_, Direction p_175955_6_, int p_175955_7_) {
        if (!canAddStructurePieces()) {
            return null;
        } else {
            if (strongComponentType != null) {
                SafeStrongholdPieces.Stronghold lvt_8_1_ = findAndCreatePieceFactory(strongComponentType, p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_, p_175955_7_);
                strongComponentType = null;
                if (lvt_8_1_ != null) {
                    return lvt_8_1_;
                }
            }

            int lvt_8_2_ = 0;

            while(lvt_8_2_ < 5) {
                ++lvt_8_2_;
                int lvt_9_1_ = p_175955_2_.nextInt(totalWeight);

                for (PieceWeight lvt_11_1_ : structurePieceList) {
                    lvt_9_1_ -= lvt_11_1_.pieceWeight;
                    if (lvt_9_1_ < 0) {
                        if (!lvt_11_1_.canSpawnMoreStructuresOfType(p_175955_7_) || lvt_11_1_ == p_175955_0_.lastPlaced) {
                            break;
                        }

                        Stronghold lvt_12_1_ = findAndCreatePieceFactory(lvt_11_1_.pieceClass, p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_, p_175955_7_);
                        if (lvt_12_1_ != null) {
                            ++lvt_11_1_.instancesSpawned;
                            p_175955_0_.lastPlaced = lvt_11_1_;
                            if (!lvt_11_1_.canSpawnMoreStructures()) {
                                structurePieceList.remove(lvt_11_1_);
                            }

                            return lvt_12_1_;
                        }
                    }
                }
            }

            MutableBoundingBox lvt_9_2_ = SafeStrongholdPieces.Corridor.findPieceBox(p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_);
            if (lvt_9_2_ != null && lvt_9_2_.minY > 1) {
                return new SafeStrongholdPieces.Corridor(p_175955_7_, lvt_9_2_, p_175955_6_);
            } else {
                return null;
            }
        }
    }

    private static StructurePiece generateAndAddPiece(SafeStrongholdPieces.Stairs2 p_175953_0_, List<StructurePiece> p_175953_1_, Random p_175953_2_, int p_175953_3_, int p_175953_4_, int p_175953_5_, @Nullable Direction p_175953_6_, int p_175953_7_) {
        if (p_175953_7_ > 50) {
            return null;
        } else if (Math.abs(p_175953_3_ - p_175953_0_.getBoundingBox().minX) <= 112 && Math.abs(p_175953_5_ - p_175953_0_.getBoundingBox().minZ) <= 112) {
            StructurePiece lvt_8_1_ = generatePieceFromSmallDoor(p_175953_0_, p_175953_1_, p_175953_2_, p_175953_3_, p_175953_4_, p_175953_5_, p_175953_6_, p_175953_7_ + 1);
            if (lvt_8_1_ != null) {
                p_175953_1_.add(lvt_8_1_);
                p_175953_0_.pendingChildren.add(lvt_8_1_);
            }

            return lvt_8_1_;
        } else {
            return null;
        }
    }

    static class Stones extends StructurePiece.BlockSelector {
        private Stones() {
        }

        @ParametersAreNonnullByDefault
        public void selectBlocks(Random p_75062_1_, int p_75062_2_, int p_75062_3_, int p_75062_4_, boolean p_75062_5_) {
            if (p_75062_5_) {
                float lvt_6_1_ = p_75062_1_.nextFloat();
                if (lvt_6_1_ < 0.2F) {
                    this.blockstate = Blocks.CRACKED_STONE_BRICKS.getDefaultState();
                } else if (lvt_6_1_ < 0.5F) {
                    this.blockstate = Blocks.MOSSY_STONE_BRICKS.getDefaultState();
                } else if (lvt_6_1_ < 0.55F) {
                    this.blockstate = Blocks.INFESTED_STONE_BRICKS.getDefaultState();
                } else {
                    this.blockstate = Blocks.STONE_BRICKS.getDefaultState();
                }
            } else {
                this.blockstate = Blocks.CAVE_AIR.getDefaultState();
            }

        }
    }

    public static class PortalRoom extends SafeStrongholdPieces.Stronghold {
        private boolean hasSpawner;

        public PortalRoom(int p_i50131_1_, MutableBoundingBox p_i50131_2_, Direction p_i50131_3_) {
            super(SafeStrongholdStructurePieceType.SSHPR, p_i50131_1_);
            this.setCoordBaseMode(p_i50131_3_);
            this.boundingBox = p_i50131_2_;
        }

        public PortalRoom(TemplateManager p_i50132_1_, CompoundNBT p_i50132_2_) {
            super(SafeStrongholdStructurePieceType.SSHPR, p_i50132_2_);
            this.hasSpawner = p_i50132_2_.getBoolean("Mob");
        }

        protected void readAdditional(CompoundNBT p_143011_1_) {
            super.readAdditional(p_143011_1_);
            p_143011_1_.putBoolean("Mob", this.hasSpawner);
        }

        public void buildComponent(StructurePiece p_74861_1_, List<StructurePiece> p_74861_2_, Random p_74861_3_) {
            if (p_74861_1_ != null) {
                ((SafeStrongholdPieces.Stairs2)p_74861_1_).strongholdPortalRoom = this;
            }

        }

        public static SafeStrongholdPieces.PortalRoom createPiece(List<StructurePiece> p_175865_0_, int p_175865_1_, int p_175865_2_, int p_175865_3_, Direction p_175865_4_, int p_175865_5_) {
            MutableBoundingBox lvt_6_1_ = MutableBoundingBox.getComponentToAddBoundingBox(p_175865_1_, p_175865_2_, p_175865_3_, -4, -1, 0, 11, 8, 16, p_175865_4_);
            return canStrongholdGoDeeper(lvt_6_1_) && StructurePiece.findIntersecting(p_175865_0_, lvt_6_1_) == null ? new SafeStrongholdPieces.PortalRoom(p_175865_5_, lvt_6_1_, p_175865_4_) : null;
        }

        @ParametersAreNonnullByDefault
        public boolean func_230383_a_(ISeedReader p_225577_1_, StructureManager structureManager, ChunkGenerator p_225577_2_, Random p_225577_3_, MutableBoundingBox p_225577_4_, ChunkPos p_225577_5_, BlockPos blockPos) {
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 0, 0, 0, 10, 7, 15, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.placeDoor(p_225577_1_, p_225577_3_, p_225577_4_, SafeStrongholdPieces.Stronghold.Door.GRATES, 4, 1, 0);
            int lvt_6_1_ = 6;
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 1, lvt_6_1_, 1, 1, lvt_6_1_, 14, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 9, lvt_6_1_, 1, 9, lvt_6_1_, 14, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 2, lvt_6_1_, 1, 8, lvt_6_1_, 2, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 2, lvt_6_1_, 14, 8, lvt_6_1_, 14, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 1, 1, 1, 2, 1, 4, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 8, 1, 1, 9, 1, 4, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithBlocks(p_225577_1_, p_225577_4_, 1, 1, 1, 1, 1, 3, Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState(), false);
            this.fillWithBlocks(p_225577_1_, p_225577_4_, 9, 1, 1, 9, 1, 3, Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState(), false);
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 3, 1, 8, 7, 1, 12, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithBlocks(p_225577_1_, p_225577_4_, 4, 1, 9, 6, 1, 11, Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState(), false);
            BlockState lvt_7_1_ = (Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true);
            BlockState lvt_8_1_ =(Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true)).with(PaneBlock.EAST, true);

            int lvt_9_2_;
            for(lvt_9_2_ = 3; lvt_9_2_ < 14; lvt_9_2_ += 2) {
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 0, 3, lvt_9_2_, 0, 4, lvt_9_2_, lvt_7_1_, lvt_7_1_, false);
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 10, 3, lvt_9_2_, 10, 4, lvt_9_2_, lvt_7_1_, lvt_7_1_, false);
            }

            for(lvt_9_2_ = 2; lvt_9_2_ < 9; lvt_9_2_ += 2) {
                this.fillWithBlocks(p_225577_1_, p_225577_4_, lvt_9_2_, 3, 15, lvt_9_2_, 4, 15, lvt_8_1_, lvt_8_1_, false);
            }

            BlockState lvt_9_3_ = Blocks.STONE_BRICK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH);
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 4, 1, 5, 6, 1, 7, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 4, 2, 6, 6, 2, 7, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 4, 3, 7, 6, 3, 7, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);

            for(int lvt_10_1_ = 4; lvt_10_1_ <= 6; ++lvt_10_1_) {
                this.setBlockState(p_225577_1_, lvt_9_3_, lvt_10_1_, 1, 4, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_9_3_, lvt_10_1_, 2, 5, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_9_3_, lvt_10_1_, 3, 6, p_225577_4_);
            }

            BlockState lvt_10_2_ = Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.NORTH);
            BlockState lvt_11_1_ = Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.SOUTH);
            BlockState lvt_12_1_ = Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.EAST);
            BlockState lvt_13_1_ = Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.WEST);
            boolean lvt_14_1_ = true;
            boolean[] lvt_15_1_ = new boolean[12];

            for(int lvt_16_1_ = 0; lvt_16_1_ < lvt_15_1_.length; ++lvt_16_1_) {
                lvt_15_1_[lvt_16_1_] = p_225577_3_.nextFloat() > 0.9F;
                lvt_14_1_ &= lvt_15_1_[lvt_16_1_];
            }

            this.setBlockState(p_225577_1_, lvt_10_2_.with(EndPortalFrameBlock.EYE, lvt_15_1_[0]), 4, 3, 8, p_225577_4_);
            this.setBlockState(p_225577_1_, lvt_10_2_.with(EndPortalFrameBlock.EYE, lvt_15_1_[1]), 5, 3, 8, p_225577_4_);
            this.setBlockState(p_225577_1_, lvt_10_2_.with(EndPortalFrameBlock.EYE, lvt_15_1_[2]), 6, 3, 8, p_225577_4_);
            this.setBlockState(p_225577_1_, lvt_11_1_.with(EndPortalFrameBlock.EYE, lvt_15_1_[3]), 4, 3, 12, p_225577_4_);
            this.setBlockState(p_225577_1_, lvt_11_1_.with(EndPortalFrameBlock.EYE, lvt_15_1_[4]), 5, 3, 12, p_225577_4_);
            this.setBlockState(p_225577_1_, lvt_11_1_.with(EndPortalFrameBlock.EYE, lvt_15_1_[5]), 6, 3, 12, p_225577_4_);
            this.setBlockState(p_225577_1_, lvt_12_1_.with(EndPortalFrameBlock.EYE, lvt_15_1_[6]), 3, 3, 9, p_225577_4_);
            this.setBlockState(p_225577_1_, lvt_12_1_.with(EndPortalFrameBlock.EYE, lvt_15_1_[7]), 3, 3, 10, p_225577_4_);
            this.setBlockState(p_225577_1_, lvt_12_1_.with(EndPortalFrameBlock.EYE, lvt_15_1_[8]), 3, 3, 11, p_225577_4_);
            this.setBlockState(p_225577_1_, lvt_13_1_.with(EndPortalFrameBlock.EYE, lvt_15_1_[9]), 7, 3, 9, p_225577_4_);
            this.setBlockState(p_225577_1_, lvt_13_1_.with(EndPortalFrameBlock.EYE, lvt_15_1_[10]), 7, 3, 10, p_225577_4_);
            this.setBlockState(p_225577_1_, lvt_13_1_.with(EndPortalFrameBlock.EYE, lvt_15_1_[11]), 7, 3, 11, p_225577_4_);
            if (lvt_14_1_) {
                BlockState lvt_16_2_ = Blocks.END_PORTAL.getDefaultState();
                this.setBlockState(p_225577_1_, lvt_16_2_, 4, 3, 9, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_16_2_, 5, 3, 9, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_16_2_, 6, 3, 9, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_16_2_, 4, 3, 10, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_16_2_, 5, 3, 10, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_16_2_, 6, 3, 10, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_16_2_, 4, 3, 11, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_16_2_, 5, 3, 11, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_16_2_, 6, 3, 11, p_225577_4_);
            }

            if (!this.hasSpawner) {
                int lvt_6_1_x = this.getYWithOffset(3);
                BlockPos lvt_16_3_ = new BlockPos(this.getXWithOffset(5, 6), lvt_6_1_x, this.getZWithOffset(5, 6));
                if (p_225577_4_.isVecInside(lvt_16_3_)) {
                    this.hasSpawner = true;
                    p_225577_1_.setBlockState(lvt_16_3_, Blocks.SPAWNER.getDefaultState(), 2);
                    TileEntity lvt_17_1_ = p_225577_1_.getTileEntity(lvt_16_3_);
                    if (lvt_17_1_ instanceof MobSpawnerTileEntity) {
                        ((MobSpawnerTileEntity)lvt_17_1_).getSpawnerBaseLogic().setEntityType(EntityType.SILVERFISH);
                    }
                }
            }

            return true;
        }
    }

    public static class Crossing extends SafeStrongholdPieces.Stronghold {
        private final boolean leftLow;
        private final boolean leftHigh;
        private final boolean rightLow;
        private final boolean rightHigh;

        public Crossing(int p_i45580_1_, Random p_i45580_2_, MutableBoundingBox p_i45580_3_, Direction p_i45580_4_) {
            super(SafeStrongholdStructurePieceType.SSH5C, p_i45580_1_);
            this.setCoordBaseMode(p_i45580_4_);
            this.entryDoor = this.getRandomDoor(p_i45580_2_);
            this.boundingBox = p_i45580_3_;
            this.leftLow = p_i45580_2_.nextBoolean();
            this.leftHigh = p_i45580_2_.nextBoolean();
            this.rightLow = p_i45580_2_.nextBoolean();
            this.rightHigh = p_i45580_2_.nextInt(3) > 0;
        }

        public Crossing(TemplateManager p_i50136_1_, CompoundNBT p_i50136_2_) {
            super(SafeStrongholdStructurePieceType.SSH5C, p_i50136_2_);
            this.leftLow = p_i50136_2_.getBoolean("leftLow");
            this.leftHigh = p_i50136_2_.getBoolean("leftHigh");
            this.rightLow = p_i50136_2_.getBoolean("rightLow");
            this.rightHigh = p_i50136_2_.getBoolean("rightHigh");
        }

        protected void readAdditional(CompoundNBT p_143011_1_) {
            super.readAdditional(p_143011_1_);
            p_143011_1_.putBoolean("leftLow", this.leftLow);
            p_143011_1_.putBoolean("leftHigh", this.leftHigh);
            p_143011_1_.putBoolean("rightLow", this.rightLow);
            p_143011_1_.putBoolean("rightHigh", this.rightHigh);
        }

        public void buildComponent(StructurePiece p_74861_1_, List<StructurePiece> p_74861_2_, Random p_74861_3_) {
            int lvt_4_1_ = 3;
            int lvt_5_1_ = 5;
            Direction lvt_6_1_ = this.getCoordBaseMode();
            if (lvt_6_1_ == Direction.WEST || lvt_6_1_ == Direction.NORTH) {
                lvt_4_1_ = 8 - lvt_4_1_;
                lvt_5_1_ = 8 - lvt_5_1_;
            }

            this.getNextComponentNormal((SafeStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 5, 1);
            if (this.leftLow) {
                this.getNextComponentX((SafeStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, lvt_4_1_, 1);
            }

            if (this.leftHigh) {
                this.getNextComponentX((SafeStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, lvt_5_1_, 7);
            }

            if (this.rightLow) {
                this.getNextComponentZ((SafeStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, lvt_4_1_, 1);
            }

            if (this.rightHigh) {
                this.getNextComponentZ((SafeStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, lvt_5_1_, 7);
            }

        }

        public static SafeStrongholdPieces.Crossing createPiece(List<StructurePiece> p_175866_0_, Random p_175866_1_, int p_175866_2_, int p_175866_3_, int p_175866_4_, Direction p_175866_5_, int p_175866_6_) {
            MutableBoundingBox lvt_7_1_ = MutableBoundingBox.getComponentToAddBoundingBox(p_175866_2_, p_175866_3_, p_175866_4_, -4, -3, 0, 10, 9, 11, p_175866_5_);
            return canStrongholdGoDeeper(lvt_7_1_) && StructurePiece.findIntersecting(p_175866_0_, lvt_7_1_) == null ? new SafeStrongholdPieces.Crossing(p_175866_6_, p_175866_1_, lvt_7_1_, p_175866_5_) : null;
        }

        @ParametersAreNonnullByDefault
        public boolean func_230383_a_(ISeedReader p_225577_1_, StructureManager structureManager, ChunkGenerator p_225577_2_, Random p_225577_3_, MutableBoundingBox p_225577_4_, ChunkPos p_225577_5_, BlockPos blockPos) {
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 0, 0, 0, 9, 8, 10, true, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.placeDoor(p_225577_1_, p_225577_3_, p_225577_4_, this.entryDoor, 4, 3, 0);
            if (this.leftLow) {
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 0, 3, 1, 0, 5, 3, CAVE_AIR, CAVE_AIR, false);
            }

            if (this.rightLow) {
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 9, 3, 1, 9, 5, 3, CAVE_AIR, CAVE_AIR, false);
            }

            if (this.leftHigh) {
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 0, 5, 7, 0, 7, 9, CAVE_AIR, CAVE_AIR, false);
            }

            if (this.rightHigh) {
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 9, 5, 7, 9, 7, 9, CAVE_AIR, CAVE_AIR, false);
            }

            this.fillWithBlocks(p_225577_1_, p_225577_4_, 5, 1, 10, 7, 3, 10, CAVE_AIR, CAVE_AIR, false);
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 1, 2, 1, 8, 2, 6, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 4, 1, 5, 4, 4, 9, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 8, 1, 5, 8, 4, 9, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 1, 4, 7, 3, 4, 9, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 1, 3, 5, 3, 3, 6, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithBlocks(p_225577_1_, p_225577_4_, 1, 3, 4, 3, 3, 4, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), false);
            this.fillWithBlocks(p_225577_1_, p_225577_4_, 1, 4, 6, 3, 4, 6, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), false);
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 5, 1, 7, 7, 1, 8, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithBlocks(p_225577_1_, p_225577_4_, 5, 1, 9, 7, 1, 9, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), false);
            this.fillWithBlocks(p_225577_1_, p_225577_4_, 5, 2, 7, 7, 2, 7, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), false);
            this.fillWithBlocks(p_225577_1_, p_225577_4_, 4, 5, 7, 4, 5, 9, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), false);
            this.fillWithBlocks(p_225577_1_, p_225577_4_, 8, 5, 7, 8, 5, 9, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), false);
            this.fillWithBlocks(p_225577_1_, p_225577_4_, 5, 5, 7, 7, 5, 9, Blocks.SMOOTH_STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.DOUBLE), Blocks.SMOOTH_STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.DOUBLE), false);
            this.setBlockState(p_225577_1_, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.SOUTH), 6, 5, 6, p_225577_4_);
            return true;
        }
    }

    public static class Library extends SafeStrongholdPieces.Stronghold {
        private final boolean isLargeRoom;

        public Library(int p_i45578_1_, Random p_i45578_2_, MutableBoundingBox p_i45578_3_, Direction p_i45578_4_) {
            super(SafeStrongholdStructurePieceType.SSHLI, p_i45578_1_);
            this.setCoordBaseMode(p_i45578_4_);
            this.entryDoor = this.getRandomDoor(p_i45578_2_);
            this.boundingBox = p_i45578_3_;
            this.isLargeRoom = p_i45578_3_.getYSize() > 6;
        }

        public Library(TemplateManager p_i50133_1_, CompoundNBT p_i50133_2_) {
            super(SafeStrongholdStructurePieceType.SSHLI, p_i50133_2_);
            this.isLargeRoom = p_i50133_2_.getBoolean("Tall");
        }

        protected void readAdditional(CompoundNBT p_143011_1_) {
            super.readAdditional(p_143011_1_);
            p_143011_1_.putBoolean("Tall", this.isLargeRoom);
        }

        public static SafeStrongholdPieces.Library createPiece(List<StructurePiece> p_175864_0_, Random p_175864_1_, int p_175864_2_, int p_175864_3_, int p_175864_4_, Direction p_175864_5_, int p_175864_6_) {
            MutableBoundingBox lvt_7_1_ = MutableBoundingBox.getComponentToAddBoundingBox(p_175864_2_, p_175864_3_, p_175864_4_, -4, -1, 0, 14, 11, 15, p_175864_5_);
            if (!canStrongholdGoDeeper(lvt_7_1_) || StructurePiece.findIntersecting(p_175864_0_, lvt_7_1_) != null) {
                lvt_7_1_ = MutableBoundingBox.getComponentToAddBoundingBox(p_175864_2_, p_175864_3_, p_175864_4_, -4, -1, 0, 14, 6, 15, p_175864_5_);
                if (!canStrongholdGoDeeper(lvt_7_1_) || StructurePiece.findIntersecting(p_175864_0_, lvt_7_1_) != null) {
                    return null;
                }
            }

            return new SafeStrongholdPieces.Library(p_175864_6_, p_175864_1_, lvt_7_1_, p_175864_5_);
        }

        @ParametersAreNonnullByDefault
        public boolean func_230383_a_(ISeedReader p_225577_1_, StructureManager structureManager, ChunkGenerator p_225577_2_, Random p_225577_3_, MutableBoundingBox p_225577_4_, ChunkPos p_225577_5_, BlockPos blockPos) {
            int lvt_6_1_ = 11;
            if (!this.isLargeRoom) {
                lvt_6_1_ = 6;
            }

            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 0, 0, 0, 13, lvt_6_1_ - 1, 14, true, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.placeDoor(p_225577_1_, p_225577_3_, p_225577_4_, this.entryDoor, 4, 1, 0);
            this.generateMaybeBox(p_225577_1_, p_225577_4_, p_225577_3_, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.COBWEB.getDefaultState(), Blocks.COBWEB.getDefaultState(), false, false);

            int lvt_9_1_;
            for(lvt_9_1_ = 1; lvt_9_1_ <= 13; ++lvt_9_1_) {
                if ((lvt_9_1_ - 1) % 4 == 0) {
                    this.fillWithBlocks(p_225577_1_, p_225577_4_, 1, 1, lvt_9_1_, 1, 4, lvt_9_1_, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                    this.fillWithBlocks(p_225577_1_, p_225577_4_, 12, 1, lvt_9_1_, 12, 4, lvt_9_1_, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                    this.setBlockState(p_225577_1_, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.EAST), 2, 3, lvt_9_1_, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.WEST), 11, 3, lvt_9_1_, p_225577_4_);
                    if (this.isLargeRoom) {
                        this.fillWithBlocks(p_225577_1_, p_225577_4_, 1, 6, lvt_9_1_, 1, 9, lvt_9_1_, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                        this.fillWithBlocks(p_225577_1_, p_225577_4_, 12, 6, lvt_9_1_, 12, 9, lvt_9_1_, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                    }
                } else {
                    this.fillWithBlocks(p_225577_1_, p_225577_4_, 1, 1, lvt_9_1_, 1, 4, lvt_9_1_, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                    this.fillWithBlocks(p_225577_1_, p_225577_4_, 12, 1, lvt_9_1_, 12, 4, lvt_9_1_, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                    if (this.isLargeRoom) {
                        this.fillWithBlocks(p_225577_1_, p_225577_4_, 1, 6, lvt_9_1_, 1, 9, lvt_9_1_, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                        this.fillWithBlocks(p_225577_1_, p_225577_4_, 12, 6, lvt_9_1_, 12, 9, lvt_9_1_, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                    }
                }
            }

            for(lvt_9_1_ = 3; lvt_9_1_ < 12; lvt_9_1_ += 2) {
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 3, 1, lvt_9_1_, 4, 3, lvt_9_1_, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 6, 1, lvt_9_1_, 7, 3, lvt_9_1_, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 9, 1, lvt_9_1_, 10, 3, lvt_9_1_, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
            }

            if (this.isLargeRoom) {
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 1, 5, 1, 3, 5, 13, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 10, 5, 1, 12, 5, 13, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 4, 5, 1, 9, 5, 2, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 4, 5, 12, 9, 5, 13, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), false);
                this.setBlockState(p_225577_1_, Blocks.OAK_PLANKS.getDefaultState(), 9, 5, 11, p_225577_4_);
                this.setBlockState(p_225577_1_, Blocks.OAK_PLANKS.getDefaultState(), 8, 5, 11, p_225577_4_);
                this.setBlockState(p_225577_1_, Blocks.OAK_PLANKS.getDefaultState(), 9, 5, 10, p_225577_4_);
                BlockState lvt_9_3_ = (Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
                BlockState lvt_10_1_ = (Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 3, 6, 3, 3, 6, 11, lvt_10_1_, lvt_10_1_, false);
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 10, 6, 3, 10, 6, 9, lvt_10_1_, lvt_10_1_, false);
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 4, 6, 2, 9, 6, 2, lvt_9_3_, lvt_9_3_, false);
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 4, 6, 12, 7, 6, 12, lvt_9_3_, lvt_9_3_, false);
                this.setBlockState(p_225577_1_, (Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.EAST, true), 3, 6, 2, p_225577_4_);
                this.setBlockState(p_225577_1_, (Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.SOUTH, true)).with(FenceBlock.EAST, true), 3, 6, 12, p_225577_4_);
                this.setBlockState(p_225577_1_, (Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.WEST, true), 10, 6, 2, p_225577_4_);

                for(int lvt_11_1_ = 0; lvt_11_1_ <= 2; ++lvt_11_1_) {
                    this.setBlockState(p_225577_1_, (Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.SOUTH, true)).with(FenceBlock.WEST, true), 8 + lvt_11_1_, 6, 12 - lvt_11_1_, p_225577_4_);
                    if (lvt_11_1_ != 2) {
                        this.setBlockState(p_225577_1_, (Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.EAST, true), 8 + lvt_11_1_, 6, 11 - lvt_11_1_, p_225577_4_);
                    }
                }

                BlockState lvt_11_2_ = Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.SOUTH);
                this.setBlockState(p_225577_1_, lvt_11_2_, 10, 1, 13, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_11_2_, 10, 2, 13, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_11_2_, 10, 3, 13, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_11_2_, 10, 4, 13, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_11_2_, 10, 5, 13, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_11_2_, 10, 6, 13, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_11_2_, 10, 7, 13, p_225577_4_);
                BlockState lvt_14_1_ = Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.EAST, true);
                this.setBlockState(p_225577_1_, lvt_14_1_, 6, 9, 7, p_225577_4_);
                BlockState lvt_15_1_ = Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.WEST, true);
                this.setBlockState(p_225577_1_, lvt_15_1_, 7, 9, 7, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_14_1_, 6, 8, 7, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_15_1_, 7, 8, 7, p_225577_4_);
                BlockState lvt_16_1_ = (lvt_10_1_.with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
                this.setBlockState(p_225577_1_, lvt_16_1_, 6, 7, 7, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_16_1_, 7, 7, 7, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_14_1_, 5, 7, 7, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_15_1_, 8, 7, 7, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_14_1_.with(FenceBlock.NORTH, true), 6, 7, 6, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_14_1_.with(FenceBlock.SOUTH, true), 6, 7, 8, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_15_1_.with(FenceBlock.NORTH, true), 7, 7, 6, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_15_1_.with(FenceBlock.SOUTH, true), 7, 7, 8, p_225577_4_);
                BlockState lvt_17_1_ = Blocks.TORCH.getDefaultState();
                this.setBlockState(p_225577_1_, lvt_17_1_, 5, 8, 7, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_17_1_, 8, 8, 7, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_17_1_, 6, 8, 6, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_17_1_, 6, 8, 8, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_17_1_, 7, 8, 6, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_17_1_, 7, 8, 8, p_225577_4_);
            }

            this.generateChest(p_225577_1_, p_225577_4_, p_225577_3_, 3, 3, 5, LootTables.CHESTS_STRONGHOLD_LIBRARY);
            if (this.isLargeRoom) {
                this.setBlockState(p_225577_1_, CAVE_AIR, 12, 9, 1, p_225577_4_);
                this.generateChest(p_225577_1_, p_225577_4_, p_225577_3_, 12, 8, 1, LootTables.CHESTS_STRONGHOLD_LIBRARY);
            }

            return true;
        }
    }

    public static class Prison extends SafeStrongholdPieces.Stronghold {
        public Prison(int p_i45576_1_, Random p_i45576_2_, MutableBoundingBox p_i45576_3_, Direction p_i45576_4_) {
            super(SafeStrongholdStructurePieceType.SSHPH, p_i45576_1_);
            this.setCoordBaseMode(p_i45576_4_);
            this.entryDoor = this.getRandomDoor(p_i45576_2_);
            this.boundingBox = p_i45576_3_;
        }

        public Prison(TemplateManager p_i50130_1_, CompoundNBT p_i50130_2_) {
            super(SafeStrongholdStructurePieceType.SSHPH, p_i50130_2_);
        }

        public void buildComponent(StructurePiece p_74861_1_, List<StructurePiece> p_74861_2_, Random p_74861_3_) {
            this.getNextComponentNormal((SafeStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
        }

        public static SafeStrongholdPieces.Prison createPiece(List<StructurePiece> p_175860_0_, Random p_175860_1_, int p_175860_2_, int p_175860_3_, int p_175860_4_, Direction p_175860_5_, int p_175860_6_) {
            MutableBoundingBox lvt_7_1_ = MutableBoundingBox.getComponentToAddBoundingBox(p_175860_2_, p_175860_3_, p_175860_4_, -1, -1, 0, 9, 5, 11, p_175860_5_);
            return canStrongholdGoDeeper(lvt_7_1_) && StructurePiece.findIntersecting(p_175860_0_, lvt_7_1_) == null ? new SafeStrongholdPieces.Prison(p_175860_6_, p_175860_1_, lvt_7_1_, p_175860_5_) : null;
        }

        @ParametersAreNonnullByDefault
        public boolean func_230383_a_(ISeedReader p_225577_1_, StructureManager structureManager, ChunkGenerator p_225577_2_, Random p_225577_3_, MutableBoundingBox p_225577_4_, ChunkPos p_225577_5_, BlockPos blockPos) {
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 0, 0, 0, 8, 4, 10, true, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.placeDoor(p_225577_1_, p_225577_3_, p_225577_4_, this.entryDoor, 1, 1, 0);
            this.fillWithBlocks(p_225577_1_, p_225577_4_, 1, 1, 10, 3, 3, 10, CAVE_AIR, CAVE_AIR, false);
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 4, 1, 1, 4, 3, 1, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 4, 1, 3, 4, 3, 3, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 4, 1, 7, 4, 3, 7, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 4, 1, 9, 4, 3, 9, false, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);

            for(int lvt_6_1_ = 1; lvt_6_1_ <= 3; ++lvt_6_1_) {
                this.setBlockState(p_225577_1_, (Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true), 4, lvt_6_1_, 4, p_225577_4_);
                this.setBlockState(p_225577_1_, ((Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true)).with(PaneBlock.EAST, true), 4, lvt_6_1_, 5, p_225577_4_);
                this.setBlockState(p_225577_1_, (Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true), 4, lvt_6_1_, 6, p_225577_4_);
                this.setBlockState(p_225577_1_, (Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true)).with(PaneBlock.EAST, true), 5, lvt_6_1_, 5, p_225577_4_);
                this.setBlockState(p_225577_1_, (Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true)).with(PaneBlock.EAST, true), 6, lvt_6_1_, 5, p_225577_4_);
                this.setBlockState(p_225577_1_, (Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true)).with(PaneBlock.EAST, true), 7, lvt_6_1_, 5, p_225577_4_);
            }

            this.setBlockState(p_225577_1_, (Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true), 4, 3, 2, p_225577_4_);
            this.setBlockState(p_225577_1_, (Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true), 4, 3, 8, p_225577_4_);
            BlockState lvt_6_2_ = Blocks.IRON_DOOR.getDefaultState().with(DoorBlock.FACING, Direction.WEST);
            BlockState lvt_7_1_ = (Blocks.IRON_DOOR.getDefaultState().with(DoorBlock.FACING, Direction.WEST)).with(DoorBlock.HALF, DoubleBlockHalf.UPPER);
            this.setBlockState(p_225577_1_, lvt_6_2_, 4, 1, 2, p_225577_4_);
            this.setBlockState(p_225577_1_, lvt_7_1_, 4, 2, 2, p_225577_4_);
            this.setBlockState(p_225577_1_, lvt_6_2_, 4, 1, 8, p_225577_4_);
            this.setBlockState(p_225577_1_, lvt_7_1_, 4, 2, 8, p_225577_4_);
            return true;
        }
    }

    public static class RoomCrossing extends SafeStrongholdPieces.Stronghold {
        protected final int roomType;

        public RoomCrossing(int p_i45575_1_, Random p_i45575_2_, MutableBoundingBox p_i45575_3_, Direction p_i45575_4_) {
            super(SafeStrongholdStructurePieceType.SSHRC, p_i45575_1_);
            this.setCoordBaseMode(p_i45575_4_);
            this.entryDoor = this.getRandomDoor(p_i45575_2_);
            this.boundingBox = p_i45575_3_;
            this.roomType = p_i45575_2_.nextInt(5);
        }

        public RoomCrossing(TemplateManager p_i50125_1_, CompoundNBT p_i50125_2_) {
            super(SafeStrongholdStructurePieceType.SSHRC, p_i50125_2_);
            this.roomType = p_i50125_2_.getInt("Type");
        }

        protected void readAdditional(CompoundNBT p_143011_1_) {
            super.readAdditional(p_143011_1_);
            p_143011_1_.putInt("Type", this.roomType);
        }

        public void buildComponent(StructurePiece p_74861_1_, List<StructurePiece> p_74861_2_, Random p_74861_3_) {
            this.getNextComponentNormal((SafeStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 4, 1);
            this.getNextComponentX((SafeStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 4);
            this.getNextComponentZ((SafeStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 4);
        }

        public static SafeStrongholdPieces.RoomCrossing createPiece(List<StructurePiece> p_175859_0_, Random p_175859_1_, int p_175859_2_, int p_175859_3_, int p_175859_4_, Direction p_175859_5_, int p_175859_6_) {
            MutableBoundingBox lvt_7_1_ = MutableBoundingBox.getComponentToAddBoundingBox(p_175859_2_, p_175859_3_, p_175859_4_, -4, -1, 0, 11, 7, 11, p_175859_5_);
            return canStrongholdGoDeeper(lvt_7_1_) && StructurePiece.findIntersecting(p_175859_0_, lvt_7_1_) == null ? new SafeStrongholdPieces.RoomCrossing(p_175859_6_, p_175859_1_, lvt_7_1_, p_175859_5_) : null;
        }

        @ParametersAreNonnullByDefault
        public boolean func_230383_a_(ISeedReader p_225577_1_, StructureManager structureManager, ChunkGenerator p_225577_2_, Random p_225577_3_, MutableBoundingBox p_225577_4_, ChunkPos p_225577_5_, BlockPos blockPos) {
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 0, 0, 0, 10, 6, 10, true, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.placeDoor(p_225577_1_, p_225577_3_, p_225577_4_, this.entryDoor, 4, 1, 0);
            this.fillWithBlocks(p_225577_1_, p_225577_4_, 4, 1, 10, 6, 3, 10, CAVE_AIR, CAVE_AIR, false);
            this.fillWithBlocks(p_225577_1_, p_225577_4_, 0, 1, 4, 0, 3, 6, CAVE_AIR, CAVE_AIR, false);
            this.fillWithBlocks(p_225577_1_, p_225577_4_, 10, 1, 4, 10, 3, 6, CAVE_AIR, CAVE_AIR, false);
            int lvt_6_5_;
            switch(this.roomType) {
                case 0:
                    this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 5, 1, 5, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 5, 2, 5, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 5, 3, 5, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.WEST), 4, 3, 5, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.EAST), 6, 3, 5, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.SOUTH), 5, 3, 4, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.NORTH), 5, 3, 6, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 4, 1, 4, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 4, 1, 5, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 4, 1, 6, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 6, 1, 4, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 6, 1, 5, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 6, 1, 6, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 5, 1, 4, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 5, 1, 6, p_225577_4_);
                    break;
                case 1:
                    for(lvt_6_5_ = 0; lvt_6_5_ < 5; ++lvt_6_5_) {
                        this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 3, 1, 3 + lvt_6_5_, p_225577_4_);
                        this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 7, 1, 3 + lvt_6_5_, p_225577_4_);
                        this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 3 + lvt_6_5_, 1, 3, p_225577_4_);
                        this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 3 + lvt_6_5_, 1, 7, p_225577_4_);
                    }

                    this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 5, 1, 5, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 5, 2, 5, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 5, 3, 5, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.WATER.getDefaultState(), 5, 4, 5, p_225577_4_);
                    break;
                case 2:
                    for(lvt_6_5_ = 1; lvt_6_5_ <= 9; ++lvt_6_5_) {
                        this.setBlockState(p_225577_1_, Blocks.COBBLESTONE.getDefaultState(), 1, 3, lvt_6_5_, p_225577_4_);
                        this.setBlockState(p_225577_1_, Blocks.COBBLESTONE.getDefaultState(), 9, 3, lvt_6_5_, p_225577_4_);
                    }

                    for(lvt_6_5_ = 1; lvt_6_5_ <= 9; ++lvt_6_5_) {
                        this.setBlockState(p_225577_1_, Blocks.COBBLESTONE.getDefaultState(), lvt_6_5_, 3, 1, p_225577_4_);
                        this.setBlockState(p_225577_1_, Blocks.COBBLESTONE.getDefaultState(), lvt_6_5_, 3, 9, p_225577_4_);
                    }

                    this.setBlockState(p_225577_1_, Blocks.COBBLESTONE.getDefaultState(), 5, 1, 4, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.COBBLESTONE.getDefaultState(), 5, 1, 6, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.COBBLESTONE.getDefaultState(), 5, 3, 4, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.COBBLESTONE.getDefaultState(), 5, 3, 6, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.COBBLESTONE.getDefaultState(), 4, 1, 5, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.COBBLESTONE.getDefaultState(), 6, 1, 5, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.COBBLESTONE.getDefaultState(), 4, 3, 5, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.COBBLESTONE.getDefaultState(), 6, 3, 5, p_225577_4_);

                    for(lvt_6_5_ = 1; lvt_6_5_ <= 3; ++lvt_6_5_) {
                        this.setBlockState(p_225577_1_, Blocks.COBBLESTONE.getDefaultState(), 4, lvt_6_5_, 4, p_225577_4_);
                        this.setBlockState(p_225577_1_, Blocks.COBBLESTONE.getDefaultState(), 6, lvt_6_5_, 4, p_225577_4_);
                        this.setBlockState(p_225577_1_, Blocks.COBBLESTONE.getDefaultState(), 4, lvt_6_5_, 6, p_225577_4_);
                        this.setBlockState(p_225577_1_, Blocks.COBBLESTONE.getDefaultState(), 6, lvt_6_5_, 6, p_225577_4_);
                    }

                    this.setBlockState(p_225577_1_, Blocks.TORCH.getDefaultState(), 5, 3, 5, p_225577_4_);

                    for(lvt_6_5_ = 2; lvt_6_5_ <= 8; ++lvt_6_5_) {
                        this.setBlockState(p_225577_1_, Blocks.OAK_PLANKS.getDefaultState(), 2, 3, lvt_6_5_, p_225577_4_);
                        this.setBlockState(p_225577_1_, Blocks.OAK_PLANKS.getDefaultState(), 3, 3, lvt_6_5_, p_225577_4_);
                        if (lvt_6_5_ <= 3 || lvt_6_5_ >= 7) {
                            this.setBlockState(p_225577_1_, Blocks.OAK_PLANKS.getDefaultState(), 4, 3, lvt_6_5_, p_225577_4_);
                            this.setBlockState(p_225577_1_, Blocks.OAK_PLANKS.getDefaultState(), 5, 3, lvt_6_5_, p_225577_4_);
                            this.setBlockState(p_225577_1_, Blocks.OAK_PLANKS.getDefaultState(), 6, 3, lvt_6_5_, p_225577_4_);
                        }

                        this.setBlockState(p_225577_1_, Blocks.OAK_PLANKS.getDefaultState(), 7, 3, lvt_6_5_, p_225577_4_);
                        this.setBlockState(p_225577_1_, Blocks.OAK_PLANKS.getDefaultState(), 8, 3, lvt_6_5_, p_225577_4_);
                    }

                    BlockState lvt_6_6_ = Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.WEST);
                    this.setBlockState(p_225577_1_, lvt_6_6_, 9, 1, 3, p_225577_4_);
                    this.setBlockState(p_225577_1_, lvt_6_6_, 9, 2, 3, p_225577_4_);
                    this.setBlockState(p_225577_1_, lvt_6_6_, 9, 3, 3, p_225577_4_);
                    this.generateChest(p_225577_1_, p_225577_4_, p_225577_3_, 3, 4, 8, LootTables.CHESTS_STRONGHOLD_CROSSING);
            }

            return true;
        }
    }

    public static class RightTurn extends SafeStrongholdPieces.Turn {
        public RightTurn(int p_i50127_1_, Random p_i50127_2_, MutableBoundingBox p_i50127_3_, Direction p_i50127_4_) {
            super(SafeStrongholdStructurePieceType.SSHRT, p_i50127_1_);
            this.setCoordBaseMode(p_i50127_4_);
            this.entryDoor = this.getRandomDoor(p_i50127_2_);
            this.boundingBox = p_i50127_3_;
        }

        public RightTurn(TemplateManager p_i50128_1_, CompoundNBT p_i50128_2_) {
            super(SafeStrongholdStructurePieceType.SSHRT, p_i50128_2_);
        }

        public void buildComponent(StructurePiece p_74861_1_, List<StructurePiece> p_74861_2_, Random p_74861_3_) {
            Direction lvt_4_1_ = this.getCoordBaseMode();
            if (lvt_4_1_ != Direction.NORTH && lvt_4_1_ != Direction.EAST) {
                this.getNextComponentX((SafeStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
            } else {
                this.getNextComponentZ((SafeStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
            }

        }

        public static SafeStrongholdPieces.RightTurn func_214824_a(List<StructurePiece> p_214824_0_, Random p_214824_1_, int p_214824_2_, int p_214824_3_, int p_214824_4_, Direction p_214824_5_, int p_214824_6_) {
            MutableBoundingBox lvt_7_1_ = MutableBoundingBox.getComponentToAddBoundingBox(p_214824_2_, p_214824_3_, p_214824_4_, -1, -1, 0, 5, 5, 5, p_214824_5_);
            return canStrongholdGoDeeper(lvt_7_1_) && StructurePiece.findIntersecting(p_214824_0_, lvt_7_1_) == null ? new SafeStrongholdPieces.RightTurn(p_214824_6_, p_214824_1_, lvt_7_1_, p_214824_5_) : null;
        }

        @ParametersAreNonnullByDefault
        public boolean func_230383_a_(ISeedReader p_225577_1_, StructureManager structureManager, ChunkGenerator p_225577_2_, Random p_225577_3_, MutableBoundingBox p_225577_4_, ChunkPos p_225577_5_, BlockPos blockPos) {
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 0, 0, 0, 4, 4, 4, true, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.placeDoor(p_225577_1_, p_225577_3_, p_225577_4_, this.entryDoor, 1, 1, 0);
            Direction lvt_6_1_ = this.getCoordBaseMode();
            if (lvt_6_1_ != Direction.NORTH && lvt_6_1_ != Direction.EAST) {
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 0, 1, 1, 0, 3, 3, CAVE_AIR, CAVE_AIR, false);
            } else {
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 4, 1, 1, 4, 3, 3, CAVE_AIR, CAVE_AIR, false);
            }

            return true;
        }
    }

    public static class LeftTurn extends SafeStrongholdPieces.Turn {
        public LeftTurn(int p_i45579_1_, Random p_i45579_2_, MutableBoundingBox p_i45579_3_, Direction p_i45579_4_) {
            super(SafeStrongholdStructurePieceType.SSHLT, p_i45579_1_);
            this.setCoordBaseMode(p_i45579_4_);
            this.entryDoor = this.getRandomDoor(p_i45579_2_);
            this.boundingBox = p_i45579_3_;
        }

        public LeftTurn(TemplateManager p_i50134_1_, CompoundNBT p_i50134_2_) {
            super(SafeStrongholdStructurePieceType.SSHLT, p_i50134_2_);
        }

        public void buildComponent(StructurePiece p_74861_1_, List<StructurePiece> p_74861_2_, Random p_74861_3_) {
            Direction lvt_4_1_ = this.getCoordBaseMode();
            if (lvt_4_1_ != Direction.NORTH && lvt_4_1_ != Direction.EAST) {
                this.getNextComponentZ((SafeStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
            } else {
                this.getNextComponentX((SafeStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
            }

        }

        public static SafeStrongholdPieces.LeftTurn createPiece(List<StructurePiece> p_175867_0_, Random p_175867_1_, int p_175867_2_, int p_175867_3_, int p_175867_4_, Direction p_175867_5_, int p_175867_6_) {
            MutableBoundingBox lvt_7_1_ = MutableBoundingBox.getComponentToAddBoundingBox(p_175867_2_, p_175867_3_, p_175867_4_, -1, -1, 0, 5, 5, 5, p_175867_5_);
            return canStrongholdGoDeeper(lvt_7_1_) && StructurePiece.findIntersecting(p_175867_0_, lvt_7_1_) == null ? new SafeStrongholdPieces.LeftTurn(p_175867_6_, p_175867_1_, lvt_7_1_, p_175867_5_) : null;
        }

        @ParametersAreNonnullByDefault
        public boolean func_230383_a_(ISeedReader p_225577_1_, StructureManager structureManager, ChunkGenerator p_225577_2_, Random p_225577_3_, MutableBoundingBox p_225577_4_, ChunkPos p_225577_5_, BlockPos blockPos) {
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 0, 0, 0, 4, 4, 4, true, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.placeDoor(p_225577_1_, p_225577_3_, p_225577_4_, this.entryDoor, 1, 1, 0);
            Direction lvt_6_1_ = this.getCoordBaseMode();
            if (lvt_6_1_ != Direction.NORTH && lvt_6_1_ != Direction.EAST) {
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 4, 1, 1, 4, 3, 3, CAVE_AIR, CAVE_AIR, false);
            } else {
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 0, 1, 1, 0, 3, 3, CAVE_AIR, CAVE_AIR, false);
            }

            return true;
        }
    }

    public abstract static class Turn extends SafeStrongholdPieces.Stronghold {
        protected Turn(IStructurePieceType p_i50108_1_, int p_i50108_2_) {
            super(p_i50108_1_, p_i50108_2_);
        }

        public Turn(IStructurePieceType p_i50109_1_, CompoundNBT p_i50109_2_) {
            super(p_i50109_1_, p_i50109_2_);
        }
    }

    public static class StairsStraight extends SafeStrongholdPieces.Stronghold {
        public StairsStraight(int p_i45572_1_, Random p_i45572_2_, MutableBoundingBox p_i45572_3_, Direction p_i45572_4_) {
            super(SafeStrongholdStructurePieceType.SSHSSD, p_i45572_1_);
            this.setCoordBaseMode(p_i45572_4_);
            this.entryDoor = this.getRandomDoor(p_i45572_2_);
            this.boundingBox = p_i45572_3_;
        }

        public StairsStraight(TemplateManager p_i50113_1_, CompoundNBT p_i50113_2_) {
            super(SafeStrongholdStructurePieceType.SSHSSD, p_i50113_2_);
        }

        public void buildComponent(StructurePiece p_74861_1_, List<StructurePiece> p_74861_2_, Random p_74861_3_) {
            this.getNextComponentNormal((SafeStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
        }

        public static SafeStrongholdPieces.StairsStraight createPiece(List<StructurePiece> p_175861_0_, Random p_175861_1_, int p_175861_2_, int p_175861_3_, int p_175861_4_, Direction p_175861_5_, int p_175861_6_) {
            MutableBoundingBox lvt_7_1_ = MutableBoundingBox.getComponentToAddBoundingBox(p_175861_2_, p_175861_3_, p_175861_4_, -1, -7, 0, 5, 11, 8, p_175861_5_);
            return canStrongholdGoDeeper(lvt_7_1_) && StructurePiece.findIntersecting(p_175861_0_, lvt_7_1_) == null ? new SafeStrongholdPieces.StairsStraight(p_175861_6_, p_175861_1_, lvt_7_1_, p_175861_5_) : null;
        }

        @ParametersAreNonnullByDefault
        public boolean func_230383_a_(ISeedReader p_225577_1_, StructureManager structureManager, ChunkGenerator p_225577_2_, Random p_225577_3_, MutableBoundingBox p_225577_4_, ChunkPos p_225577_5_, BlockPos blockPos) {
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 0, 0, 0, 4, 10, 7, true, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.placeDoor(p_225577_1_, p_225577_3_, p_225577_4_, this.entryDoor, 1, 7, 0);
            this.placeDoor(p_225577_1_, p_225577_3_, p_225577_4_, SafeStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 7);
            BlockState lvt_6_1_ = Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH);

            for(int lvt_7_1_ = 0; lvt_7_1_ < 6; ++lvt_7_1_) {
                this.setBlockState(p_225577_1_, lvt_6_1_, 1, 6 - lvt_7_1_, 1 + lvt_7_1_, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_6_1_, 2, 6 - lvt_7_1_, 1 + lvt_7_1_, p_225577_4_);
                this.setBlockState(p_225577_1_, lvt_6_1_, 3, 6 - lvt_7_1_, 1 + lvt_7_1_, p_225577_4_);
                if (lvt_7_1_ < 5) {
                    this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 1, 5 - lvt_7_1_, 1 + lvt_7_1_, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 2, 5 - lvt_7_1_, 1 + lvt_7_1_, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 3, 5 - lvt_7_1_, 1 + lvt_7_1_, p_225577_4_);
                }
            }

            return true;
        }
    }

    public static class ChestCorridor extends SafeStrongholdPieces.Stronghold {
        private boolean hasMadeChest;

        public ChestCorridor(int p_i45582_1_, Random p_i45582_2_, MutableBoundingBox p_i45582_3_, Direction p_i45582_4_) {
            super(SafeStrongholdStructurePieceType.SSHCC, p_i45582_1_);
            this.setCoordBaseMode(p_i45582_4_);
            this.entryDoor = this.getRandomDoor(p_i45582_2_);
            this.boundingBox = p_i45582_3_;
        }

        public ChestCorridor(TemplateManager p_i50140_1_, CompoundNBT p_i50140_2_) {
            super(SafeStrongholdStructurePieceType.SSHCC, p_i50140_2_);
            this.hasMadeChest = p_i50140_2_.getBoolean("Chest");
        }

        protected void readAdditional(CompoundNBT p_143011_1_) {
            super.readAdditional(p_143011_1_);
            p_143011_1_.putBoolean("Chest", this.hasMadeChest);
        }

        public void buildComponent(StructurePiece p_74861_1_, List<StructurePiece> p_74861_2_, Random p_74861_3_) {
            this.getNextComponentNormal((SafeStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
        }

        public static SafeStrongholdPieces.ChestCorridor createPiece(List<StructurePiece> p_175868_0_, Random p_175868_1_, int p_175868_2_, int p_175868_3_, int p_175868_4_, Direction p_175868_5_, int p_175868_6_) {
            MutableBoundingBox lvt_7_1_ = MutableBoundingBox.getComponentToAddBoundingBox(p_175868_2_, p_175868_3_, p_175868_4_, -1, -1, 0, 5, 5, 7, p_175868_5_);
            return canStrongholdGoDeeper(lvt_7_1_) && StructurePiece.findIntersecting(p_175868_0_, lvt_7_1_) == null ? new SafeStrongholdPieces.ChestCorridor(p_175868_6_, p_175868_1_, lvt_7_1_, p_175868_5_) : null;
        }

        @ParametersAreNonnullByDefault
        public boolean func_230383_a_(ISeedReader p_225577_1_, StructureManager structureManager, ChunkGenerator p_225577_2_, Random p_225577_3_, MutableBoundingBox p_225577_4_, ChunkPos p_225577_5_, BlockPos blockPos) {
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 0, 0, 0, 4, 4, 6, true, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.placeDoor(p_225577_1_, p_225577_3_, p_225577_4_, this.entryDoor, 1, 1, 0);
            this.placeDoor(p_225577_1_, p_225577_3_, p_225577_4_, SafeStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
            this.fillWithBlocks(p_225577_1_, p_225577_4_, 3, 1, 2, 3, 1, 4, Blocks.STONE_BRICKS.getDefaultState(), Blocks.STONE_BRICKS.getDefaultState(), false);
            this.setBlockState(p_225577_1_, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 1, 1, p_225577_4_);
            this.setBlockState(p_225577_1_, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 1, 5, p_225577_4_);
            this.setBlockState(p_225577_1_, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 2, 2, p_225577_4_);
            this.setBlockState(p_225577_1_, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 2, 4, p_225577_4_);

            for(int lvt_6_1_ = 2; lvt_6_1_ <= 4; ++lvt_6_1_) {
                this.setBlockState(p_225577_1_, Blocks.STONE_BRICK_SLAB.getDefaultState(), 2, 1, lvt_6_1_, p_225577_4_);
            }

            if (!this.hasMadeChest && p_225577_4_.isVecInside(new BlockPos(this.getXWithOffset(3, 3), this.getYWithOffset(2), this.getZWithOffset(3, 3)))) {
                this.hasMadeChest = true;
                this.generateChest(p_225577_1_, p_225577_4_, p_225577_3_, 3, 2, 3, LootTables.CHESTS_STRONGHOLD_CORRIDOR);
            }

            return true;
        }
    }

    public static class Straight extends SafeStrongholdPieces.Stronghold {
        private final boolean expandsX;
        private final boolean expandsZ;

        public Straight(int p_i45573_1_, Random p_i45573_2_, MutableBoundingBox p_i45573_3_, Direction p_i45573_4_) {
            super(SafeStrongholdStructurePieceType.SSHS, p_i45573_1_);
            this.setCoordBaseMode(p_i45573_4_);
            this.entryDoor = this.getRandomDoor(p_i45573_2_);
            this.boundingBox = p_i45573_3_;
            this.expandsX = p_i45573_2_.nextInt(2) == 0;
            this.expandsZ = p_i45573_2_.nextInt(2) == 0;
        }

        public Straight(TemplateManager p_i50115_1_, CompoundNBT p_i50115_2_) {
            super(SafeStrongholdStructurePieceType.SSHS, p_i50115_2_);
            this.expandsX = p_i50115_2_.getBoolean("Left");
            this.expandsZ = p_i50115_2_.getBoolean("Right");
        }

        protected void readAdditional(CompoundNBT p_143011_1_) {
            super.readAdditional(p_143011_1_);
            p_143011_1_.putBoolean("Left", this.expandsX);
            p_143011_1_.putBoolean("Right", this.expandsZ);
        }

        public void buildComponent(StructurePiece p_74861_1_, List<StructurePiece> p_74861_2_, Random p_74861_3_) {
            this.getNextComponentNormal((SafeStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
            if (this.expandsX) {
                this.getNextComponentX((SafeStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 2);
            }

            if (this.expandsZ) {
                this.getNextComponentZ((SafeStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 2);
            }

        }

        public static SafeStrongholdPieces.Straight createPiece(List<StructurePiece> p_175862_0_, Random p_175862_1_, int p_175862_2_, int p_175862_3_, int p_175862_4_, Direction p_175862_5_, int p_175862_6_) {
            MutableBoundingBox lvt_7_1_ = MutableBoundingBox.getComponentToAddBoundingBox(p_175862_2_, p_175862_3_, p_175862_4_, -1, -1, 0, 5, 5, 7, p_175862_5_);
            return canStrongholdGoDeeper(lvt_7_1_) && StructurePiece.findIntersecting(p_175862_0_, lvt_7_1_) == null ? new SafeStrongholdPieces.Straight(p_175862_6_, p_175862_1_, lvt_7_1_, p_175862_5_) : null;
        }

        @ParametersAreNonnullByDefault
        public boolean func_230383_a_(ISeedReader p_225577_1_, StructureManager structureManager, ChunkGenerator p_225577_2_, Random p_225577_3_, MutableBoundingBox p_225577_4_, ChunkPos p_225577_5_, BlockPos blockPos) {
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 0, 0, 0, 4, 4, 6, true, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.placeDoor(p_225577_1_, p_225577_3_, p_225577_4_, this.entryDoor, 1, 1, 0);
            this.placeDoor(p_225577_1_, p_225577_3_, p_225577_4_, SafeStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
            BlockState lvt_6_1_ = Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.EAST);
            BlockState lvt_7_1_ = Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.WEST);
            this.randomlyPlaceBlock(p_225577_1_, p_225577_4_, p_225577_3_, 0.1F, 1, 2, 1, lvt_6_1_);
            this.randomlyPlaceBlock(p_225577_1_, p_225577_4_, p_225577_3_, 0.1F, 3, 2, 1, lvt_7_1_);
            this.randomlyPlaceBlock(p_225577_1_, p_225577_4_, p_225577_3_, 0.1F, 1, 2, 5, lvt_6_1_);
            this.randomlyPlaceBlock(p_225577_1_, p_225577_4_, p_225577_3_, 0.1F, 3, 2, 5, lvt_7_1_);
            if (this.expandsX) {
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 0, 1, 2, 0, 3, 4, CAVE_AIR, CAVE_AIR, false);
            }

            if (this.expandsZ) {
                this.fillWithBlocks(p_225577_1_, p_225577_4_, 4, 1, 2, 4, 3, 4, CAVE_AIR, CAVE_AIR, false);
            }

            return true;
        }
    }

    public static class Stairs2 extends SafeStrongholdPieces.Stairs {
        public SafeStrongholdPieces.PieceWeight lastPlaced;
        @Nullable
        public SafeStrongholdPieces.PortalRoom strongholdPortalRoom;
        public final List<StructurePiece> pendingChildren = Lists.newArrayList();

        public Stairs2(Random p_i50117_1_, int p_i50117_2_, int p_i50117_3_) {
            super(SafeStrongholdStructurePieceType.SSHSTART, 0, p_i50117_1_, p_i50117_2_, p_i50117_3_);
        }

        public Stairs2(TemplateManager p_i50118_1_, CompoundNBT p_i50118_2_) {
            super(SafeStrongholdStructurePieceType.SSHSTART, p_i50118_2_);
        }
    }

    public static class Stairs extends SafeStrongholdPieces.Stronghold {
        private final boolean source;

        public Stairs(IStructurePieceType p_i50120_1_, int p_i50120_2_, Random p_i50120_3_, int p_i50120_4_, int p_i50120_5_) {
            super(p_i50120_1_, p_i50120_2_);
            this.source = true;
            this.setCoordBaseMode(Direction.Plane.HORIZONTAL.random(p_i50120_3_));
            this.entryDoor = SafeStrongholdPieces.Stronghold.Door.OPENING;
            if (this.getCoordBaseMode().getAxis() == Direction.Axis.Z) {
                this.boundingBox = new MutableBoundingBox(p_i50120_4_, 64, p_i50120_5_, p_i50120_4_ + 5 - 1, 74, p_i50120_5_ + 5 - 1);
            } else {
                this.boundingBox = new MutableBoundingBox(p_i50120_4_, 64, p_i50120_5_, p_i50120_4_ + 5 - 1, 74, p_i50120_5_ + 5 - 1);
            }

        }

        public Stairs(int p_i45574_1_, Random p_i45574_2_, MutableBoundingBox p_i45574_3_, Direction p_i45574_4_) {
            super(SafeStrongholdStructurePieceType.SSHSD, p_i45574_1_);
            this.source = false;
            this.setCoordBaseMode(p_i45574_4_);
            this.entryDoor = this.getRandomDoor(p_i45574_2_);
            this.boundingBox = p_i45574_3_;
        }

        public Stairs(IStructurePieceType p_i50121_1_, CompoundNBT p_i50121_2_) {
            super(p_i50121_1_, p_i50121_2_);
            this.source = p_i50121_2_.getBoolean("Source");
        }

        public Stairs(TemplateManager p_i50122_1_, CompoundNBT p_i50122_2_) {
            this(SafeStrongholdStructurePieceType.SSHSD, p_i50122_2_);
        }

        protected void readAdditional(CompoundNBT p_143011_1_) {
            super.readAdditional(p_143011_1_);
            p_143011_1_.putBoolean("Source", this.source);
        }

        public void buildComponent(StructurePiece p_74861_1_, List<StructurePiece> p_74861_2_, Random p_74861_3_) {
            if (this.source) {
                SafeStrongholdPieces.strongComponentType = SafeStrongholdPieces.Crossing.class;
            }

            this.getNextComponentNormal((SafeStrongholdPieces.Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
        }

        public static SafeStrongholdPieces.Stairs createPiece(List<StructurePiece> p_175863_0_, Random p_175863_1_, int p_175863_2_, int p_175863_3_, int p_175863_4_, Direction p_175863_5_, int p_175863_6_) {
            MutableBoundingBox lvt_7_1_ = MutableBoundingBox.getComponentToAddBoundingBox(p_175863_2_, p_175863_3_, p_175863_4_, -1, -7, 0, 5, 11, 5, p_175863_5_);
            return canStrongholdGoDeeper(lvt_7_1_) && StructurePiece.findIntersecting(p_175863_0_, lvt_7_1_) == null ? new SafeStrongholdPieces.Stairs(p_175863_6_, p_175863_1_, lvt_7_1_, p_175863_5_) : null;
        }

        @ParametersAreNonnullByDefault
        public boolean func_230383_a_(ISeedReader p_225577_1_, StructureManager structureManager, ChunkGenerator p_225577_2_, Random p_225577_3_, MutableBoundingBox p_225577_4_, ChunkPos p_225577_5_, BlockPos blockPos) {
            this.fillWithRandomizedBlocks(p_225577_1_, p_225577_4_, 0, 0, 0, 4, 10, 4, true, p_225577_3_, SafeStrongholdPieces.STRONGHOLD_STONES);
            this.placeDoor(p_225577_1_, p_225577_3_, p_225577_4_, this.entryDoor, 1, 7, 0);
            this.placeDoor(p_225577_1_, p_225577_3_, p_225577_4_, SafeStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 4);
            this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 2, 6, 1, p_225577_4_);
            this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 1, 5, 1, p_225577_4_);
            this.setBlockState(p_225577_1_, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 1, 6, 1, p_225577_4_);
            this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 1, 5, 2, p_225577_4_);
            this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 1, 4, 3, p_225577_4_);
            this.setBlockState(p_225577_1_, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 1, 5, 3, p_225577_4_);
            this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 2, 4, 3, p_225577_4_);
            this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 3, 3, 3, p_225577_4_);
            this.setBlockState(p_225577_1_, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 3, 4, 3, p_225577_4_);
            this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 3, 3, 2, p_225577_4_);
            this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 3, 2, 1, p_225577_4_);
            this.setBlockState(p_225577_1_, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 3, 3, 1, p_225577_4_);
            this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 2, 2, 1, p_225577_4_);
            this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 1, 1, 1, p_225577_4_);
            this.setBlockState(p_225577_1_, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 1, 2, 1, p_225577_4_);
            this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 1, 1, 2, p_225577_4_);
            this.setBlockState(p_225577_1_, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 1, 1, 3, p_225577_4_);
            return true;
        }
    }

    public static class Corridor extends SafeStrongholdPieces.Stronghold {
        private final int steps;

        public Corridor(int p_i50137_1_, MutableBoundingBox p_i50137_2_, Direction p_i50137_3_) {
            super(SafeStrongholdStructurePieceType.SSHFC, p_i50137_1_);
            this.setCoordBaseMode(p_i50137_3_);
            this.boundingBox = p_i50137_2_;
            this.steps = p_i50137_3_ != Direction.NORTH && p_i50137_3_ != Direction.SOUTH ? p_i50137_2_.getXSize() : p_i50137_2_.getZSize();
        }

        public Corridor(TemplateManager p_i50138_1_, CompoundNBT p_i50138_2_) {
            super(SafeStrongholdStructurePieceType.SSHFC, p_i50138_2_);
            this.steps = p_i50138_2_.getInt("Steps");
        }

        protected void readAdditional(CompoundNBT p_143011_1_) {
            super.readAdditional(p_143011_1_);
            p_143011_1_.putInt("Steps", this.steps);
        }

        public static MutableBoundingBox findPieceBox(List<StructurePiece> p_175869_0_, Random p_175869_1_, int p_175869_2_, int p_175869_3_, int p_175869_4_, Direction p_175869_5_) {
            MutableBoundingBox lvt_7_1_ = MutableBoundingBox.getComponentToAddBoundingBox(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, 4, p_175869_5_);
            StructurePiece lvt_8_1_ = StructurePiece.findIntersecting(p_175869_0_, lvt_7_1_);
            if (lvt_8_1_ == null) {
                return null;
            } else {
                if (lvt_8_1_.getBoundingBox().minY == lvt_7_1_.minY) {
                    for(int lvt_9_1_ = 3; lvt_9_1_ >= 1; --lvt_9_1_) {
                        lvt_7_1_ = MutableBoundingBox.getComponentToAddBoundingBox(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, lvt_9_1_ - 1, p_175869_5_);
                        if (!lvt_8_1_.getBoundingBox().intersectsWith(lvt_7_1_)) {
                            return MutableBoundingBox.getComponentToAddBoundingBox(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, lvt_9_1_, p_175869_5_);
                        }
                    }
                }

                return null;
            }
        }

        @ParametersAreNonnullByDefault
        public boolean func_230383_a_(ISeedReader p_225577_1_, StructureManager structureManager, ChunkGenerator p_225577_2_, Random p_225577_3_, MutableBoundingBox p_225577_4_, ChunkPos p_225577_5_, BlockPos blockPos) {
            for(int lvt_6_1_ = 0; lvt_6_1_ < this.steps; ++lvt_6_1_) {
                this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 0, 0, lvt_6_1_, p_225577_4_);
                this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 1, 0, lvt_6_1_, p_225577_4_);
                this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 2, 0, lvt_6_1_, p_225577_4_);
                this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 3, 0, lvt_6_1_, p_225577_4_);
                this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 4, 0, lvt_6_1_, p_225577_4_);

                for(int lvt_7_1_ = 1; lvt_7_1_ <= 3; ++lvt_7_1_) {
                    this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 0, lvt_7_1_, lvt_6_1_, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.CAVE_AIR.getDefaultState(), 1, lvt_7_1_, lvt_6_1_, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.CAVE_AIR.getDefaultState(), 2, lvt_7_1_, lvt_6_1_, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.CAVE_AIR.getDefaultState(), 3, lvt_7_1_, lvt_6_1_, p_225577_4_);
                    this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 4, lvt_7_1_, lvt_6_1_, p_225577_4_);
                }

                this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 0, 4, lvt_6_1_, p_225577_4_);
                this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 1, 4, lvt_6_1_, p_225577_4_);
                this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 2, 4, lvt_6_1_, p_225577_4_);
                this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 3, 4, lvt_6_1_, p_225577_4_);
                this.setBlockState(p_225577_1_, Blocks.STONE_BRICKS.getDefaultState(), 4, 4, lvt_6_1_, p_225577_4_);
            }

            return true;
        }
    }

    abstract static class Stronghold extends SafeStructurePiece {
        protected SafeStrongholdPieces.Stronghold.Door entryDoor = Door.OPENING;

        protected Stronghold(IStructurePieceType p_i50110_1_, int p_i50110_2_) {
            super(p_i50110_1_, p_i50110_2_);
        }

        public Stronghold(IStructurePieceType p_i50111_1_, CompoundNBT p_i50111_2_) {
            super(p_i50111_1_, p_i50111_2_);
            this.entryDoor = SafeStrongholdPieces.Stronghold.Door.valueOf(p_i50111_2_.getString("EntryDoor"));
        }

        protected void readAdditional(CompoundNBT p_143011_1_) {
            p_143011_1_.putString("EntryDoor", this.entryDoor.name());
        }

        protected void placeDoor(IWorld p_74990_1_, Random p_74990_2_, MutableBoundingBox p_74990_3_, SafeStrongholdPieces.Stronghold.Door p_74990_4_, int p_74990_5_, int p_74990_6_, int p_74990_7_) {
            switch(p_74990_4_) {
                case OPENING:
                    this.fillWithBlocks(p_74990_1_, p_74990_3_, p_74990_5_, p_74990_6_, p_74990_7_, p_74990_5_ + 3 - 1, p_74990_6_ + 3 - 1, p_74990_7_, CAVE_AIR, CAVE_AIR, false);
                    break;
                case WOOD_DOOR:
                    this.setBlockState(p_74990_1_, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.OAK_DOOR.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.OAK_DOOR.getDefaultState().with(DoorBlock.HALF, DoubleBlockHalf.UPPER), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    break;
                case GRATES:
                    this.setBlockState(p_74990_1_, Blocks.CAVE_AIR.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.CAVE_AIR.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, (Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true)).with(PaneBlock.WEST, true), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, (Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true)).with(PaneBlock.WEST, true), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, (Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true)).with(PaneBlock.WEST, true), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
                    break;
                case IRON_DOOR:
                    this.setBlockState(p_74990_1_, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.STONE_BRICKS.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.IRON_DOOR.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.IRON_DOOR.getDefaultState().with(DoorBlock.HALF, DoubleBlockHalf.UPPER), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.STONE_BUTTON.getDefaultState().with(AbstractButtonBlock.HORIZONTAL_FACING, Direction.NORTH), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ + 1, p_74990_3_);
                    this.setBlockState(p_74990_1_, Blocks.STONE_BUTTON.getDefaultState().with(AbstractButtonBlock.HORIZONTAL_FACING, Direction.SOUTH), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ - 1, p_74990_3_);
            }

        }

        protected SafeStrongholdPieces.Stronghold.Door getRandomDoor(Random p_74988_1_) {
            int lvt_2_1_ = p_74988_1_.nextInt(5);
            switch(lvt_2_1_) {
                case 0:
                case 1:
                default:
                    return SafeStrongholdPieces.Stronghold.Door.OPENING;
                case 2:
                    return SafeStrongholdPieces.Stronghold.Door.WOOD_DOOR;
                case 3:
                    return SafeStrongholdPieces.Stronghold.Door.GRATES;
                case 4:
                    return SafeStrongholdPieces.Stronghold.Door.IRON_DOOR;
            }
        }

        @Nullable
        protected StructurePiece getNextComponentNormal(SafeStrongholdPieces.Stairs2 p_74986_1_, List<StructurePiece> p_74986_2_, Random p_74986_3_, int p_74986_4_, int p_74986_5_) {
            Direction lvt_6_1_ = this.getCoordBaseMode();
            if (lvt_6_1_ != null) {
                switch(lvt_6_1_) {
                    case NORTH:
                        return SafeStrongholdPieces.generateAndAddPiece(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX + p_74986_4_, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ - 1, lvt_6_1_, this.getComponentType());
                    case SOUTH:
                        return SafeStrongholdPieces.generateAndAddPiece(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX + p_74986_4_, this.boundingBox.minY + p_74986_5_, this.boundingBox.maxZ + 1, lvt_6_1_, this.getComponentType());
                    case WEST:
                        return SafeStrongholdPieces.generateAndAddPiece(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ + p_74986_4_, lvt_6_1_, this.getComponentType());
                    case EAST:
                        return SafeStrongholdPieces.generateAndAddPiece(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ + p_74986_4_, lvt_6_1_, this.getComponentType());
                }
            }

            return null;
        }

        @Nullable
        protected StructurePiece getNextComponentX(SafeStrongholdPieces.Stairs2 p_74989_1_, List<StructurePiece> p_74989_2_, Random p_74989_3_, int p_74989_4_, int p_74989_5_) {
            Direction lvt_6_1_ = this.getCoordBaseMode();
            if (lvt_6_1_ != null) {
                switch(lvt_6_1_) {
                    case NORTH:
                        return SafeStrongholdPieces.generateAndAddPiece(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ + p_74989_5_, Direction.WEST, this.getComponentType());
                    case SOUTH:
                        return SafeStrongholdPieces.generateAndAddPiece(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ + p_74989_5_, Direction.WEST, this.getComponentType());
                    case WEST:
                        return SafeStrongholdPieces.generateAndAddPiece(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX + p_74989_5_, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ - 1, Direction.NORTH, this.getComponentType());
                    case EAST:
                        return SafeStrongholdPieces.generateAndAddPiece(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX + p_74989_5_, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ - 1, Direction.NORTH, this.getComponentType());
                }
            }

            return null;
        }

        @Nullable
        protected StructurePiece getNextComponentZ(SafeStrongholdPieces.Stairs2 p_74987_1_, List<StructurePiece> p_74987_2_, Random p_74987_3_, int p_74987_4_, int p_74987_5_) {
            Direction lvt_6_1_ = this.getCoordBaseMode();
            if (lvt_6_1_ != null) {
                switch(lvt_6_1_) {
                    case NORTH:
                        return SafeStrongholdPieces.generateAndAddPiece(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74987_4_, this.boundingBox.minZ + p_74987_5_, Direction.EAST, this.getComponentType());
                    case SOUTH:
                        return SafeStrongholdPieces.generateAndAddPiece(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74987_4_, this.boundingBox.minZ + p_74987_5_, Direction.EAST, this.getComponentType());
                    case WEST:
                        return SafeStrongholdPieces.generateAndAddPiece(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.minX + p_74987_5_, this.boundingBox.minY + p_74987_4_, this.boundingBox.maxZ + 1, Direction.SOUTH, this.getComponentType());
                    case EAST:
                        return SafeStrongholdPieces.generateAndAddPiece(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.minX + p_74987_5_, this.boundingBox.minY + p_74987_4_, this.boundingBox.maxZ + 1, Direction.SOUTH, this.getComponentType());
                }
            }

            return null;
        }

        protected static boolean canStrongholdGoDeeper(MutableBoundingBox p_74991_0_) {
            return p_74991_0_ != null && p_74991_0_.minY > 10;
        }

        public enum Door {
            OPENING,
            WOOD_DOOR,
            GRATES,
            IRON_DOOR;
        }
    }

    static class PieceWeight {
        public final Class<? extends SafeStrongholdPieces.Stronghold> pieceClass;
        public final int pieceWeight;
        public int instancesSpawned;
        public final int instancesLimit;

        public PieceWeight(Class<? extends SafeStrongholdPieces.Stronghold> p_i2076_1_, int p_i2076_2_, int p_i2076_3_) {
            this.pieceClass = p_i2076_1_;
            this.pieceWeight = p_i2076_2_;
            this.instancesLimit = p_i2076_3_;
        }

        public boolean canSpawnMoreStructuresOfType(int p_75189_1_) {
            return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
        }

        public boolean canSpawnMoreStructures() {
            return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
        }
    }


}
