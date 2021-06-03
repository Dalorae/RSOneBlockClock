package com.dalorae.rsoneblockclock.client;


import com.dalorae.rsoneblockclock.Init;
import com.dalorae.rsoneblockclock.RSOneBlockClock;
import com.dalorae.rsoneblockclock.client.screen.ClockBlockScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = RSOneBlockClock.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.register(Init.Container_RSOBC.get(), ClockBlockScreen::new);
    }
}
