package com.unsoldriceball.spawndecreaser;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.unsoldriceball.spawndecreaser.SDMain.ID_MOD;




@Config(modid = ID_MOD)
public class SDConfig
{
    @Config.RangeInt(min = 0, max = 4096)
    public static int probability_AbyssalCraft = 18;
    @Config.RangeInt(min = 0, max = 4096)
    public static int probability_MoCreatures = 1536;
    @Config.RangeInt(min = 0, max = 4096)
    public static int probability_BetterSlimes = 1024;
    @Config.RangeInt(min = 0, max = 4096)
    public static int probability_TakumiCraft = 1;



    @Mod.EventBusSubscriber(modid = ID_MOD)
    private static class EventHandler
    {
        //Configが変更されたときに呼び出される。変更を適用する関数。
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(ID_MOD))
            {
                ConfigManager.sync(ID_MOD, Config.Type.INSTANCE);
            }
        }
    }
}