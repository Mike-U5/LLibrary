package net.ilexiconn.llibrary.server;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.server.FMLServerHandler;
import net.ilexiconn.llibrary.LLibrary;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.ilexiconn.llibrary.server.network.AnimationMessage;
import net.ilexiconn.llibrary.server.network.SnackbarMessage;
import net.ilexiconn.llibrary.server.snackbar.Snackbar;
import net.ilexiconn.llibrary.server.update.UpdateHandler;
import net.minecraftforge.common.MinecraftForge;

public class ServerProxy {
    public static final ServerEventHandler SERVER_EVENT_HANDLER = new ServerEventHandler();

    public void onPreInit() {
        MinecraftForge.EVENT_BUS.register(ServerProxy.SERVER_EVENT_HANDLER);
        FMLCommonHandler.instance().bus().register(ServerProxy.SERVER_EVENT_HANDLER);

        LLibrary.NETWORK_WRAPPER.registerMessage(AnimationMessage.class, AnimationMessage.class, 0, Side.CLIENT);
        LLibrary.NETWORK_WRAPPER.registerMessage(SnackbarMessage.class, SnackbarMessage.class, 1, Side.CLIENT);
    }

    public void onInit() {
        UpdateHandler.INSTANCE.registerUpdateChecker(LLibrary.INSTANCE, "http://pastebin.com/raw/4WCeUU9p");
    }

    public void onPostInit() {
        UpdateHandler.INSTANCE.searchForUpdates();
    }

    public <MESSAGE extends AbstractMessage<MESSAGE>> void handleMessage(final MESSAGE message, final MessageContext messageContext) {
        message.onServerReceived(FMLServerHandler.instance().getServer(), message, messageContext.getServerHandler().playerEntity, messageContext);
    }

    public float getPartialTicks() {
        return 0.0F;
    }

    public void showSnackbar(Snackbar snackbar) {
        LLibrary.NETWORK_WRAPPER.sendToAll(new SnackbarMessage(snackbar));
    }
}
