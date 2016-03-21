package net.ilexiconn.llibrary.test;

import net.ilexiconn.llibrary.client.model.tabula.baked.BakedTabulaLoader;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = "BakedTabulaTest")
public class BakedTabulaTest {
    @SidedProxy
    public static ServerProxy PROXY;
    public static Item ITEM;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        BakedTabulaTest.PROXY.onPreInit();
    }

    public static class ServerProxy {
        public void onPreInit() {
            GameRegistry.registerItem(BakedTabulaTest.ITEM = new Item().setUnlocalizedName("tabula_baked_item").setCreativeTab(CreativeTabs.tabMisc), "tabula_baked_item");
        }
    }

    @SideOnly(Side.CLIENT)
    public static class ClientProxy extends ServerProxy {
        @Override
        public void onPreInit() {
            super.onPreInit();

            BakedTabulaLoader.INSTANCE.registerDomain("tabulatest");
            ModelLoader.setCustomModelResourceLocation(BakedTabulaTest.ITEM, 0, new ModelResourceLocation("tabulatest:tabula_baked_item.tbl", "inventory"));
        }
    }
}