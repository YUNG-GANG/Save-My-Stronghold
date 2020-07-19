package com.yungnickyoung.minecraft.savemystronghold.init;

import com.yungnickyoung.minecraft.savemystronghold.worldgen.SafeStrongholdStructure;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ModStructures {
    // We use "minecraft" as the mod ID to override vanilla
    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, "minecraft");
    public static final RegistryObject<Structure<NoFeatureConfig>> SAFESTRONGHOLD = register("Stronghold", new SafeStrongholdStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.STRONGHOLDS);

    private static <T extends Structure<?>> RegistryObject<T> register(String name, T structure, GenerationStage.Decoration stage) {
        Structure.field_236385_u_.put(structure, stage);
        Structure.field_236365_a_.put(name, structure);
        return STRUCTURES.register(name.toLowerCase(Locale.ROOT), () -> structure);
    }

    public static void init() {
        STRUCTURES.register(FMLJavaModLoadingContext.get().getModEventBus());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModStructures::commonSetup);
    }

    /**
     * Adds safe stronghold structure to all applicable biomes.
     */
    public static void commonSetup(FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            ModStructurePieces.init();

            Set<Map.Entry<ResourceLocation, Biome>> biomesList = ForgeRegistries.BIOMES.getEntries();
            for (Map.Entry<ResourceLocation, Biome> entry : biomesList) {
                Biome biome = entry.getValue();

                // Only operate on biomes that have strongholds
                if (!biome.hasStructure(Structure.field_236375_k_)) {
                    continue;
                }

                // Remove vanilla stronghold
                biome.structures.remove(Structure.field_236375_k_);

                // Add Safe Stronghold
                biome.func_235063_a_(SAFESTRONGHOLD.get().func_236391_a_(NoFeatureConfig.field_236559_b_));
            }
        });
    }
}
