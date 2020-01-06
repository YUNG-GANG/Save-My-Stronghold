package com.yungnickyoung.minecraft.savemystronghold;

import com.yungnickyoung.minecraft.savemystronghold.worldgen.SafeStrongholdWorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = "savemystronghold", useMetadata = true, acceptableRemoteVersions = "*")
public class SaveMyStronghold {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new SafeStrongholdWorldGenerator(), 99999);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.TERRAIN_GEN_BUS.register(new EventStrongholdGen());
    }
}
