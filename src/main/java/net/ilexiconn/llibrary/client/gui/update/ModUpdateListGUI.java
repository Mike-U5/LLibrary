package net.ilexiconn.llibrary.client.gui.update;

import cpw.mods.fml.client.GuiScrollingList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.llibrary.client.ClientProxy;
import net.ilexiconn.llibrary.server.update.UpdateContainer;
import net.ilexiconn.llibrary.server.update.UpdateHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * @author iLexiconn
 * @since 1.0.0
 */
@SideOnly(Side.CLIENT)
public class ModUpdateListGUI extends GuiScrollingList {
    private ModUpdateGUI parent;
    private Map<Integer, ResourceLocation> cachedLogo;
    private Map<Integer, Vector2f> cachedLogoDimensions;

    public ModUpdateListGUI(ModUpdateGUI parent, int width) {
        super(parent.mc, width, parent.height, 32, parent.height - 55, 10, 35);
        this.parent = parent;
        this.cachedLogo = new HashMap<>();
        this.cachedLogoDimensions = new HashMap<>();
    }

    @Override
    protected int getSize() {
        return UpdateHandler.INSTANCE.getOutdatedModList().size();
    }

    @Override
    protected void elementClicked(int index, boolean doubleClick) {
        this.parent.selectModIndex(index);
    }

    @Override
    protected boolean isSelected(int index) {
        return this.parent.modIndexSelected(index);
    }

    @Override
    protected void drawBackground() {
        this.parent.drawDefaultBackground();
    }

    @Override
    protected int getContentHeight() {
        return (this.getSize()) * 35 + 1;
    }

    @Override
    protected void drawSlot(int idx, int right, int top, int height, Tessellator tess) {
        UpdateContainer updateContainer = UpdateHandler.INSTANCE.getOutdatedModList().get(idx);
        String name = StringUtils.stripControlCodes(updateContainer.getModContainer().getName());
        String version = StringUtils.stripControlCodes(updateContainer.getLatestVersion().getVersionString());
        FontRenderer font = ClientProxy.MINECRAFT.fontRenderer;

        if (!this.cachedLogo.containsKey(idx)) {
            BufferedImage icon = updateContainer.getIcon();
            if (icon != null) {
                this.cachedLogo.put(idx, ClientProxy.MINECRAFT.getTextureManager().getDynamicTextureLocation("mod_icon", new DynamicTexture(icon)));
                this.cachedLogoDimensions.put(idx, new Vector2f(icon.getWidth(), icon.getHeight()));
            }
        }

        boolean hasIcon = this.cachedLogo.containsKey(idx);

        font.drawString(font.trimStringToWidth(name, this.listWidth - 10), hasIcon ? this.left + 36 : this.left + 6, top + 7, 0xFFFFFF);
        font.drawString(font.trimStringToWidth(version, this.listWidth - 10), hasIcon ? this.left + 36 : this.left + 6, top + 17, 0xCCCCCC);

        if (hasIcon) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            ClientProxy.MINECRAFT.renderEngine.bindTexture(this.cachedLogo.get(idx));
            float scaleX = this.cachedLogoDimensions.get(idx).getX() / 32.0F;
            float scaleY = this.cachedLogoDimensions.get(idx).getY() / 32.0F;
            float scale = 1.0F;

            if (scaleX > 1.0F || scaleY > 1.0F) {
                scale = 1.0F / Math.max(scaleX, scaleY);
            }

            float iconWidth = this.cachedLogoDimensions.get(idx).getX() * scale;
            float iconHeight = this.cachedLogoDimensions.get(idx).getY() * scale;
            int offset = 12;
            tess.startDrawingQuads();
            tess.addVertexWithUV(offset, top + iconHeight, 0, 0, 1);
            tess.addVertexWithUV(offset + iconWidth, top + iconHeight, 0, 1, 1);
            tess.addVertexWithUV(offset + iconWidth, top, 0, 1, 0);
            tess.addVertexWithUV(offset, top, 0, 0, 0);
            tess.draw();
        }
    }

    public int getWidth() {
        return this.listWidth;
    }
}