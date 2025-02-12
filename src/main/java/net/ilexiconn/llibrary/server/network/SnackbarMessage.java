package net.ilexiconn.llibrary.server.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.LLibrary;
import net.ilexiconn.llibrary.server.snackbar.Snackbar;
import net.ilexiconn.llibrary.server.snackbar.SnackbarPosition;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class SnackbarMessage extends AbstractMessage<SnackbarMessage> {
    private Snackbar snackbar;

    public SnackbarMessage() {

    }

    public SnackbarMessage(Snackbar snackbar) {
        this.snackbar = snackbar;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientReceived(Minecraft client, SnackbarMessage message, EntityPlayer player, MessageContext messageContext) {
        LLibrary.PROXY.showSnackbar(message.snackbar);
    }

    @Override
    public void onServerReceived(MinecraftServer server, SnackbarMessage message, EntityPlayer player, MessageContext messageContext) {

    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        Snackbar snackbar = Snackbar.create(ByteBufUtils.readUTF8String(byteBuf));
        snackbar.setDuration(byteBuf.readInt());
        snackbar.setColor(byteBuf.readInt());
        snackbar.setPosition(SnackbarPosition.values()[byteBuf.readInt()]);
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        ByteBufUtils.writeUTF8String(byteBuf, this.snackbar.getMessage());
        byteBuf.writeInt(this.snackbar.getDuration());
        byteBuf.writeInt(this.snackbar.getColor());
        byteBuf.writeInt(this.snackbar.getPosition().ordinal());
    }
}
