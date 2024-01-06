package com.unsoldriceball.spawndecreaser;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;

import java.util.Arrays;
import java.util.Random;


@Mod(modid = Main.MOD_ID, acceptableRemoteVersions = "*")
public class Main {
    public static final String MOD_ID = "spawndecreaser";
    private static final String ABYSSAL_ID = "abyssalcraft";
    private static final String  ABYSSALZOMBIE_ID = "abyssalcraft:abyssalzombie";
    private static final String TAKUMI_ID = "takumicraft";
    private static final String[] ABYSSAL_DIM = new String[]
            {
                    "Omothol",
                    "The Dreadlands",
                    "The Dark Realm",
                    "The Abyssal Wasteland"
            }; ;
    private static final String TAKUMI_DIM = "takumiworld";


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){ //ModがInitializeを呼び出す前に発生するイベント。
        MinecraftForge.EVENT_BUS.register(this); //これでこのクラス内でForgeのイベントが動作するようになるらしい。
    }



    @SubscribeEvent
    public void WhenSpawn(CheckSpawn event)
    {
        Entity target = event.getEntity();
        String id_raw = EntityList.getKey(target).toString();
        String id[] = id_raw.split(":", 2);
        World wor = target.world;
        String dim = wor.provider.getDimensionType().getName();
        String bio[] = wor.getBiome(target.getPosition()).getRegistryName().toString().split(":", 2);

        if (id_raw.equals(ABYSSALZOMBIE_ID))                            //対象がAbyssalZombieか
        {
            if (!bio[0].equals(ABYSSAL_ID))                             //対象のバイオームがmodで追加されていないものか
            {
                if (Arrays.asList(ABYSSAL_DIM).contains(dim)) return;   //対象のディメンションがmodで追加されたものか
                if (Random(18)) return;
                event.setResult(Event.Result.DENY);
            }
        }
        else if (id[0].equals(TAKUMI_ID))                               //対象がmodで追加されたmobか
        {
            if (!bio[0].equals(TAKUMI_ID))                              //対象のバイオームがmodで追加されていないものか
            {
                if (dim.equals(TAKUMI_DIM)) return;                     //対象のディメンションがmodで追加されたものか。
                if (Random(1)) return;
                event.setResult(Event.Result.DENY);
            }
        }
    }



    //分母を4096として、引数以下の数字が出たかを返す。
    private static boolean Random(int chance)
    {
        Random rnd = new Random();
        int value = rnd.nextInt(4096) + 1;
        return value <= chance;
    }
}
