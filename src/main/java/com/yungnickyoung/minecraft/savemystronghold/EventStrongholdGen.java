package com.yungnickyoung.minecraft.savemystronghold;

import com.yungnickyoung.minecraft.savemystronghold.worldgen.MapGenSafeStronghold;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventStrongholdGen {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onStrongholdGen(InitMapGenEvent event) {
        if (event.getType() == InitMapGenEvent.EventType.STRONGHOLD && !event.getOriginalGen().getClass().equals(MapGenSafeStronghold.class)) {
            event.setNewGen(new MapGenSafeStronghold());
        }
    }
}
