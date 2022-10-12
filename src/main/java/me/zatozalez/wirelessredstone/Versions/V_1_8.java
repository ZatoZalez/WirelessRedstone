package me.zatozalez.wirelessredstone.Versions;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class V_1_8 {
    public static ItemStack[] getItemStack(){
        return new ItemStack[] {
                new ItemStack(Material.valueOf("stained_hardened_clay".toUpperCase()), 1, (short)5),
                new ItemStack(Material.valueOf("stained_hardened_clay".toUpperCase()), 1, (short)14)
        };
    }
}
