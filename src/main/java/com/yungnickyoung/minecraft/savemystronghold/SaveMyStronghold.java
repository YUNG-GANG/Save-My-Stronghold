package com.yungnickyoung.minecraft.savemystronghold;

import com.yungnickyoung.minecraft.savemystronghold.worldgen.SafeStrongholdStructure;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StrongholdStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Set;

/**
 * Save My Stronghold! mod entry point
 */
@Mod("savemystronghold")
public class SaveMyStronghold {
    public static final Logger LOGGER = LogManager.getLogger("savemystronghold");

    public static final Structure<NoFeatureConfig> safeStronghold = new SafeStrongholdStructure(NoFeatureConfig::deserialize);

    public SaveMyStronghold() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerFeature);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
    }

    /**
     * Listens for minecraft:feature registry Register event and replaces
     * normal strongholds with safe strongholds.
     */
    public void registerFeature(RegistryEvent.Register<Feature<?>> event) {
        if (event.getRegistry().getRegistryName().toString().equals("minecraft:feature")) {
            safeStronghold.setRegistryName("minecraft:stronghold");
            event.getRegistry().register(safeStronghold);
        }
    }

    /**
     * Adds safe stronghold structure to all biomes except Nether and End biomes.
     */
    public void commonSetup(FMLCommonSetupEvent event) {
        Set<Map.Entry<ResourceLocation, Biome>> biomesList = ForgeRegistries.BIOMES.getEntries();
        biomesList.forEach(e -> {
            Biome b = e.getValue();
            if (b.getCategory() != Biome.Category.NETHER && b.getCategory() != Biome.Category.THEEND)
                b.addStructure(safeStronghold.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
        });
    }
}
