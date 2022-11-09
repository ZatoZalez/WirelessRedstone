package me.zatozalez.wirelessredstone.Redstone;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class R_Items {
    public static R_Item RedstoneSender;
    public static R_Item RedstoneReceiver;

    public static void initialize(){
        RedstoneSender = new R_Item(DeviceType.RedstoneSender);
        RedstoneReceiver = new R_Item(DeviceType.RedstoneReceiver);
    }

    public static void unload(){
        RedstoneSender.removeRecipe();
        RedstoneReceiver.removeRecipe();
    }

    public static boolean isRedstoneDevice(ItemStack itemStack){
        return (isRedstoneSender(itemStack) || isRedstoneReceiver(itemStack));
    }

    public static boolean isRedstoneSender(ItemStack itemStack){
        return isRedstoneDevice(RedstoneSender, itemStack);
    }

    public static boolean isRedstoneReceiver(ItemStack itemStack){
        return isRedstoneDevice(RedstoneReceiver, itemStack);
    }

    private static boolean isRedstoneDevice(R_Item item, ItemStack itemStack){
        if(itemStack == null || item == null)
            return false;

        if(!itemStack.hasItemMeta())
            return false;

        List<String> lore = itemStack.getItemMeta().getLore();
        if(lore == null || lore.size() < 1)
            return false;

        if(lore.get(0).replace(ChatColor.COLOR_CHAR, '&').endsWith(item.secretCode))
            return true;
        return false;
    }
}
