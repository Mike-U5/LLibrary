package net.ilexiconn.llibrary.client.gui.survivaltab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author iLexiconn
 * @since 1.4.0
 */
@SideOnly(Side.CLIENT)
public enum SurvivalTabHandler {
    INSTANCE;

    private List<SurvivalTab> survivalTabList = new ArrayList<>();
    private int currentPage;

    /**
     * Create a new survival tab instance and register it.
     *
     * @param label     the unlocalized survival tab label
     * @param container the container class
     * @return the new survival tab instance
     */
    public SurvivalTab create(String label, Class<? extends GuiContainer> container) {
        SurvivalTab survivalTab = new SurvivalTab(this.survivalTabList.size(), label, container);
        this.survivalTabList.add(survivalTab);
        return survivalTab;
    }

    public List<SurvivalTab> getSurvivalTabList() {
        return this.survivalTabList;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int page) {
        this.currentPage = page;
    }
}
