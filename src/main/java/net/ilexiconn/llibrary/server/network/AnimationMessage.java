package net.ilexiconn.llibrary.server.network;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AnimationMessage extends AbstractMessage<AnimationMessage> {
    private int entityID;
    private int animationID;

    public AnimationMessage() {

    }

    public AnimationMessage(int entityID, Animation animation) {
        this.entityID = entityID;
        this.animationID = animation.getID();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientReceived(Minecraft client, AnimationMessage message, EntityPlayer player, MessageContext messageContext) {
        IAnimatedEntity entity = (IAnimatedEntity) player.worldObj.getEntityByID(message.entityID);
        if (entity != null && message.animationID != 0) {
            entity.setAnimation(entity.getAnimations()[message.animationID]);
            entity.setAnimationTick(0);
        }
    }

    @Override
    public void onServerReceived(MinecraftServer server, AnimationMessage message, EntityPlayer player, MessageContext messageContext) {

    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.entityID = byteBuf.readInt();
        this.animationID = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(entityID);
        byteBuf.writeInt(animationID);
    }
}