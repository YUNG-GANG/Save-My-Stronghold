package com.yungnickyoung.minecraft.savemystronghold;

import com.yungnickyoung.minecraft.savemystronghold.worldgen.MapGenSafeStronghold;
import com.yungnickyoung.minecraft.savemystronghold.worldgen.SafeStrongholdWorldGenerator;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventStrongholdGen {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onStrongholdGen(InitMapGenEvent event) {
        if (event.getType() == InitMapGenEvent.EventType.STRONGHOLD) {
            MapGenSafeStronghold safeStronghold = new MapGenSafeStronghold();
            event.setNewGen(safeStronghold);
            SafeStrongholdWorldGenerator.activeStronghold = safeStronghold;
//            SaveMyStronghold.LOGGER.info("EVENTDONE");
        }
    }
}
