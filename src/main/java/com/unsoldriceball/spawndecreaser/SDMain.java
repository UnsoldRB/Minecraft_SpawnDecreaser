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
import java.util.Objects;
import java.util.Random;




@Mod(modid = SDMain.ID_MOD, acceptableRemoteVersions = "*")
public class SDMain
{
    public static final String ID_MOD = "spawndecreaser";
    private static final String ABYSSAL_ID = "abyssalcraft";
    private static final String  ABYSSALZOMBIE_ID = "abyssalcraft:abyssalzombie";
    private static final String MOCREATURES_ID = "mocreatures";
    private static final String BETTERSLIMES_ID = "null";
    private static final String TAKUMI_ID = "takumicraft";
    private static final String[] ABYSSAL_DIM = new String[]
            {
                    "Omothol",
                    "The Dreadlands",
                    "The Dark Realm",
                    "The Abyssal Wasteland"
            };
    private static final String TAKUMI_DIM = "takumiworld";




    //ModがInitializeを呼び出す前に発生するイベント。
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //これでこのクラス内でForgeのイベントが動作するようになるらしい。
        MinecraftForge.EVENT_BUS.register(this);
    }




    //Entityが自然スポーン「しようとしたとき」のイベント
    @SubscribeEvent
    public void WhenSpawn(CheckSpawn event)
    {
        if (!event.getWorld().isRemote)
        {
            final Entity L_TARGET = event.getEntity();
            final String L_ID_RAW = Objects.requireNonNull(EntityList.getKey(L_TARGET)).toString();
            final String[] L_ID = L_ID_RAW.split(":", 2);
            final World L_WOR = L_TARGET.world;
            final String L_DIM = L_WOR.provider.getDimensionType().getName();
            final String[] L_BIO = L_WOR.getBiome(L_TARGET.getPosition()).getRegistryName().toString().split(":", 2);


            //対象がAbyssalZombieなら
            if (L_ID_RAW.equals(ABYSSALZOMBIE_ID))
            {
                //対象のバイオームがmodで追加されていないものなら
                if (!L_BIO[0].equals(ABYSSAL_ID))
                {
                    //対象のディメンションがmodで追加されたものなら
                    if (Arrays.asList(ABYSSAL_DIM).contains(L_DIM)) return;
                    if (Random(SDConfig.probability_AbyssalCraft)) return;
                    event.setResult(Event.Result.DENY);
                }

            }
            //対象がMoCreaturesで追加されたmobなら
            else if (L_ID[0].equals(MOCREATURES_ID))
            {
                if (Random(SDConfig.probability_MoCreatures)) return;
                event.setResult(Event.Result.DENY);
            }
            //対象がBetterSlimesで追加されたmobなら
            else if (L_ID[0].equals(BETTERSLIMES_ID))
            {
                if (Random(SDConfig.probability_BetterSlimes)) return;
                event.setResult(Event.Result.DENY);
            }
            //対象がTakumiCraftで追加されたmobなら
            else if (L_ID[0].equals(TAKUMI_ID))
            {
                //対象のバイオームがmodで追加されていないものなら
                if (!L_BIO[0].equals(TAKUMI_ID))
                {
                    //対象のディメンションがmodで追加されたものなら
                    if (L_DIM.equals(TAKUMI_DIM)) return;
                    if (Random(SDConfig.probability_TakumiCraft)) return;
                    event.setResult(Event.Result.DENY);
                }
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
