package me.zatozalez.wirelessredstone.Versions;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class V_1_12 {
    public static ItemStack[] getItemStack(){
        return new ItemStack[] {
                new ItemStack(Material.valueOf("concrete".toUpperCase()), 1, (short)13),
                new ItemStack(Material.valueOf("concrete".toUpperCase()), 1, (short)14)
        };
    }
}
