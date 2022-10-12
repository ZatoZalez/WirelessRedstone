package me.zatozalez.wirelessredstone.Versions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.Powerable;
import org.bukkit.inventory.ItemStack;

public class V_1_18 {
    public static ItemStack[] getItemStack(){
        return new ItemStack[] {
                new ItemStack(Material.valueOf("lime_terracotta".toUpperCase())),
                new ItemStack(Material.valueOf("red_terracotta".toUpperCase()))
        };
    }

    public static boolean isInstanceOfAnaloguePowerableOrPowerable(Block block){
        if(!(block.getBlockData() instanceof AnaloguePowerable) && !(block.getBlockData() instanceof Powerable)) return false;
        return true;
    }
}
