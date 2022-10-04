package me.zatozalez.wirelessredstone.Redstone;

import me.zatozalez.wirelessredstone.Config.C_Value;
import me.zatozalez.wirelessredstone.Utils.U_Log;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public final class R_Manager {
    public static ItemStack RedstoneSender;
    public static ItemStack RedstoneReceiver;
    public static Material RedstoneSenderMaterial = Material.GREEN_CONCRETE;
    public static Material RedstoneReceiverMaterial = Material.RED_CONCRETE;

    public static void initialize() {
        String value = C_Value.getSenderBlockType();
        if(value != null){
            if(value.contains(":"))
                value = value.split(":")[1];
            try {
                Material material = Material.valueOf(value.toUpperCase());
                if (material.isBlock())
                    RedstoneSenderMaterial = material;
                else
                    WirelessRedstone.Log(new U_Log(U_Log.LogType.WARNING, "Invalid configuration for SenderBlockType. '" + value + "' is not a solid block. Block type has been set to the default."));
            }
            catch (Exception e) {  WirelessRedstone.Log(new U_Log(U_Log.LogType.WARNING, "Invalid configuration for SenderBlockType. '" + value + "' is not a valid block type. Block type has been set to the default."));
            }
        }

        value = C_Value.getReceiverBlockType();
        if(value != null){
            if(value.contains(":"))
                value = value.split(":")[1];
            try {
                Material material = Material.valueOf(value.toUpperCase());
                if (material.isBlock())
                    RedstoneReceiverMaterial = material;
                else
                    WirelessRedstone.Log(new U_Log(U_Log.LogType.WARNING, "Invalid configuration for ReceiverBlockType. '" + value + "' is not a solid block. Block type has been set to the default."));
            }
            catch (Exception e) {  WirelessRedstone.Log(new U_Log(U_Log.LogType.WARNING, "Invalid configuration for ReceiverBlockType. '" + value + "' is not a valid block type. Block type has been set to the default."));
            }
        }

        createRedstoneSender();
        createRedstoneReceiver();
    }

    private static void createRedstoneSender(){
        ItemStack item = new ItemStack(RedstoneSenderMaterial, 1);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.WHITE + "RedstoneSender");
        List<String> lore = new ArrayList<>();
        lore.add("Sends wireless Redstone signals");
        meta.setLore(lore);

        item.setItemMeta(meta);
        RedstoneSender = item;
    }
    private static void createRedstoneReceiver(){
        ItemStack item = new ItemStack(RedstoneReceiverMaterial, 1);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.WHITE + "RedstoneReceiver");
        List<String> lore = new ArrayList<>();
        lore.add("Receives wireless Redstone signals");
        meta.setLore(lore);

        item.setItemMeta(meta);
        RedstoneReceiver = item;
    }
}
