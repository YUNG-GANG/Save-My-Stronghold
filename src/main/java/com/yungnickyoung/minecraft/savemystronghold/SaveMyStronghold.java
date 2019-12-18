package com.yungnickyoung.minecraft.savemystronghold;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "savemystronghold", useMetadata = true, acceptableRemoteVersions = "*")
public class SaveMyStronghold {

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.TERRAIN_GEN_BUS.register(new EventStrongholdGen());
    }
}
