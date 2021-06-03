package com.dalorae.rsoneblockclock;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(RSOneBlockClock.MOD_ID)
public class RSOneBlockClock {
    private static final Logger LOGGER = LogManager.getLogger();

    // Mod ID Global
    public static final String MOD_ID = "rsobcmod";

    public RSOneBlockClock() {
        // Register the setup method for modloading
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);

        //BlockInit.BLOCKS.register(bus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        Init.BLOCKS.register(bus);
        Init.ITEMS.register(bus);
        Init.TET.register(bus);
        Init.CONTAINERTYPES.register(bus);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("RSOneBlockClock Loaded");
    }
}
