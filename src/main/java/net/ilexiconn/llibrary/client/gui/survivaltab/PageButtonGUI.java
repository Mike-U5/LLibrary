package net.ilexiconn.llibrary.client.gui.survivaltab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;

import java.util.Iterator;
import java.util.List;

@SideOnly(Side.CLIENT)
public class PageButtonGUI extends GuiButton {
    private GuiScreen screen;

    public PageButtonGUI(int id, int x, int y, GuiScreen screen) {
        super(id, x, y, 20, 20, id == -1 ? "<" : ">");
        this.screen = screen;
        this.updateState();
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        super.drawButton(mc, mouseX, mouseY);
        if (this.id == -1) {
            mc.fontRenderer.drawString((SurvivalTabHandler.INSTANCE.getCurrentPage() + 1) + "/" + SurvivalTabHandler.INSTANCE.getSurvivalTabList().size() / 8 + 1, this.xPosition + 31, this.yPosition + 6, 0xFFFFFFFF);
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            if (id == -1) {
                int currentPage = SurvivalTabHandler.INSTANCE.getCurrentPage();
                if (currentPage > 0) {
                    SurvivalTabHandler.INSTANCE.setCurrentPage(currentPage - 1);
                    this.initGui();
                    this.updateState();
                }
            } else if (id == -2) {
                int currentPage = SurvivalTabHandler.INSTANCE.getCurrentPage();
                if (currentPage < SurvivalTabHandler.INSTANCE.getSurvivalTabList().size() / 8) {
                    SurvivalTabHandler.INSTANCE.setCurrentPage(currentPage + 1);
                    this.initGui();
                    this.updateState();
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private void updateState() {
        for (GuiButton button : (List<GuiButton>) this.screen.buttonList) {
            if (button.id == -1) {
                button.enabled = SurvivalTabHandler.INSTANCE.getCurrentPage() >= SurvivalTabHandler.INSTANCE.getSurvivalTabList().size() / 8;
            } else if (button.id == -2) {
                button.enabled = SurvivalTabHandler.INSTANCE.getCurrentPage() <= 0;
            }
        }
    }

    public void initGui() {
        Iterator<GuiButton> iterator = this.screen.buttonList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof SurvivalTabGUI) {
                iterator.remove();
            }
        }
        this.screen.initGui();
        MinecraftForge.EVENT_BUS.post(new GuiScreenEvent.InitGuiEvent.Post(screen, screen.buttonList));
    }
}

