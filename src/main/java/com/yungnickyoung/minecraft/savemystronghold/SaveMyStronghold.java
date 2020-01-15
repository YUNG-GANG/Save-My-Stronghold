package com.yungnickyoung.minecraft.savemystronghold;

import com.yungnickyoung.minecraft.savemystronghold.worldgen.SafeStrongholdWorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "savemystronghold", useMetadata = true, acceptableRemoteVersions = "*")
public class SaveMyStronghold {
    public static final Logger LOGGER = LogManager.getLogger("savemystronghold");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new SafeStrongholdWorldGenerator(), Integer.MAX_VALUE);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.TERRAIN_GEN_BUS.register(new EventStrongholdGen());
    }
}
