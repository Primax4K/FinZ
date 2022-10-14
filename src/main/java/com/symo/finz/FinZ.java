package com.symo.finz;

import com.symo.finz.commands.CommandManager;
import com.symo.finz.config.FinZConfig;
import com.symo.finz.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.time.Duration;
import java.time.LocalDateTime;

@Mod(clientSideOnly = true, name = "FinZ", modid = FinZ.modId, version = FinZ.version, acceptedMinecraftVersions = "[1.8.9]")
public class FinZ {
    public static final String modId = "FinZ";
    public static final String version = "0.2";
    public static final String commandPrefix = "#";
    public static final Minecraft mc;
    public static final ModuleManager moduleManager = new ModuleManager();
    public static final CommandManager commandManager = new CommandManager();
    public static FinZConfig configFile;
    public static GuiScreen display;

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        System.out.println("FinZ is preInitializing...");
        ModMetadata modMetadata = event.getModMetadata();
        modMetadata.autogenerated = false;
        modMetadata.name = "FinZ";
        modMetadata.authorList.add(EnumChatFormatting.BOLD + "Symo");
        modMetadata.logoFile = "resources/finz/logo.png";
        modMetadata.description = "A Minecraft Forge 1.8.9 utility/cheat mod.";
        modMetadata.url = "https://github.com/SymoHTL/AdvancedMinecraft";
    }

    @EventHandler
    public void init(final FMLInitializationEvent event) {
        System.out.println("FinZ is initializing...");
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(moduleManager);
        moduleManager.init();
        commandManager.init();

        // read config
        //File configFile = new File(Loader.instance().getConfigDir(),
        //        modId.toLowerCase() + ".cfg");
        //config = new Configuration(configFile);
        //FinZConfig.readConfig();

        // load config
        // load modules
        // load commands

    }

    @EventHandler
    public void post(final FMLPostInitializationEvent event) {
        System.out.println("FinZ is done loading!");
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime firstRun = now.withSecond(0).plusMinutes(1L);
        final Duration initialDelay = Duration.between(now, firstRun);
        // FinZ

        //Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() ->
        //                MinecraftForge.EVENT_BUS.post(new MillisecondEvent()),
        //        initialDelay.getSeconds(), 1L, TimeUnit.MILLISECONDS);
    }

    @SubscribeEvent
    public void tick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        if (display != null) {
            try {
                mc.displayGuiScreen(display);
            } catch (Exception e) {
                e.printStackTrace();
            }
            display = null;
        }
    }


    static {
        display = null;
        mc = Minecraft.getMinecraft();
        configFile = FinZConfig.INSTANCE;
    }
}
