package net.ilexiconn.llibrary;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.ilexiconn.llibrary.server.ServerProxy;
import net.ilexiconn.llibrary.server.config.ConfigHandler;
import net.ilexiconn.llibrary.server.config.LLibraryConfig;
import net.ilexiconn.llibrary.server.network.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "llibrary", name = "LLibrary", version = LLibrary.VERSION, guiFactory = "net.ilexiconn.llibrary.client.gui.LLibraryGUIFactory")
public class LLibrary {
    public static final String VERSION = "1.5.1";
    public static final Logger LOGGER = LogManager.getLogger("LLibrary");
    @SidedProxy(serverSide = "net.ilexiconn.llibrary.server.ServerProxy", clientSide = "net.ilexiconn.llibrary.client.ClientProxy")
    public static ServerProxy PROXY;
    @Mod.Instance("llibrary")
    public static LLibrary INSTANCE;
    public static LLibraryConfig CONFIG = new LLibraryConfig();
    @NetworkWrapper({AnimationMessage.class, PropertiesMessage.class, SnackbarMessage.class, BlockEntityMessage.class, SurvivalTabMessage.class})
    public static SimpleNetworkWrapper NETWORK_WRAPPER;
    public static int QUBBLE_VERSION = 1;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        for (ModContainer mod : Loader.instance().getModList()) {
            ConfigHandler.INSTANCE.injectConfig(mod, event.getAsmData());
            NetworkHandler.INSTANCE.injectNetworkWrapper(mod, event.getAsmData());
        }
        LLibrary.CONFIG.load();
        LLibrary.PROXY.onPreInit();
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        LLibrary.PROXY.onInit();
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        LLibrary.PROXY.onPostInit();
    }
}
