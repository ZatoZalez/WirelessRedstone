package me.zatozalez.wirelessredstone.Redstone;

import me.zatozalez.wirelessredstone.Config.C_Utility;
import me.zatozalez.wirelessredstone.Config.C_Value;
import me.zatozalez.wirelessredstone.Utils.U_Converter;
import me.zatozalez.wirelessredstone.Utils.U_Log;
import me.zatozalez.wirelessredstone.Versions.V_Manager;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public final class R_Manager {
    public static ItemStack RedstoneSender;
    public static ItemStack RedstoneReceiver;
    public static Material RedstoneSenderMaterial;
    public static Material RedstoneReceiverMaterial;

    public static void initialize() {
        if(V_Manager.getItemStack() == null){
            WirelessRedstone.Log(new U_Log(U_Log.LogType.ERROR, "Invalid materials for RedstoneSender and/or RedstoneReceiver. Disabling WirelessRedstone."));
            Bukkit.getPluginManager().disablePlugin(WirelessRedstone.getPlugin());
            return;
        }

        setRedstoneSender();
        setRedstoneReceiver();
        createRedstoneSender();
        createRedstoneReceiver();
    }

    private static void createRedstoneSender(){
        ItemMeta meta = RedstoneSender.getItemMeta();
        RedstoneSenderMaterial = RedstoneSender.getType();

        assert meta != null;
        meta.setDisplayName(ChatColor.WHITE + "RedstoneSender");
        List<String> lore = new ArrayList<>();
        lore.add("Sends wireless Redstone signals");
        meta.setLore(lore);

        RedstoneSender.setItemMeta(meta);
    }
    private static void createRedstoneReceiver(){
        ItemMeta meta = RedstoneReceiver.getItemMeta();
        RedstoneReceiverMaterial = RedstoneReceiver.getType();

        assert meta != null;
        meta.setDisplayName(ChatColor.WHITE + "RedstoneReceiver");
        List<String> lore = new ArrayList<>();
        lore.add("Receives wireless Redstone signals");
        meta.setLore(lore);

        RedstoneReceiver.setItemMeta(meta);
    }

    private static void setRedstoneSender(){
        String value = C_Value.getSenderBlockType();
        if(value != null)
        {
            if(value.contains(":"))
                value = value.split(":")[1];

            try {
                ItemStack itemStack = new ItemStack(getMaterial(value), 1, (short) getData(value));
                if (itemStack != null && itemStack.getType().isBlock()){
                    RedstoneSender = itemStack;
                    return;
                }
            }catch (Exception ignored) { }
        }

        RedstoneSender = V_Manager.getItemStack()[0];
        String key = RedstoneSender.getType().toString().toLowerCase();
        int data = getData(RedstoneSender.getData().toString());
        if (data != 0)
            key += " (" + data + ")";
        C_Utility.getData("SenderBlockType").setValue(key);
        C_Utility.save();
        if(!value.equals("null"))
            WirelessRedstone.Log(new U_Log(U_Log.LogType.WARNING, "Invalid configuration for SenderBlockType. '" + value + "' is not a solid block. Block type has been set to '" + key + "'."));
    }
    private static void setRedstoneReceiver(){
        String value = C_Value.getReceiverBlockType();
        if(value != null)
        {
            if(value.contains(":"))
                value = value.split(":")[1];

            try {
                ItemStack itemStack = new ItemStack(getMaterial(value), 1, (short) getData(value));
                if (itemStack != null && itemStack.getType().isBlock()){
                    RedstoneReceiver = itemStack;
                    return;
                }
            }catch (Exception ignored) { }
        }

        RedstoneReceiver = V_Manager.getItemStack()[1];
        String key = RedstoneReceiver.getType().toString().toLowerCase();
        int data = getData(RedstoneReceiver.getData().toString());
        if (data != 0)
            key += " (" + data + ")";
        C_Utility.getData("ReceiverBlockType").setValue(key);
        C_Utility.save();

        if(!value.equals("null"))
            WirelessRedstone.Log(new U_Log(U_Log.LogType.WARNING, "Invalid configuration for ReceiverBlockType. '" + value + "' is not a solid block. Block type has been set to '" + key + "'."));
    }

    private static int getData(String string){
        String data = string;
        if(!data.contains("(") && !data.contains(")"))
            return 0;
        data = data.substring(data.indexOf("(") + 1);
        data = data.substring(0, data.indexOf(")"));
        return U_Converter.getIntFromString(data.trim());
    }
    private static Material getMaterial(String string){
        String data = string;
        if(data.contains("("))
            data = data.split("[(]")[0];
        return Material.valueOf(data.trim().toUpperCase());
    }
}
