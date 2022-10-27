package me.zatozalez.wirelessredstone.Versions;

import org.bukkit.Material;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.inventory.ItemStack;

public class V_1_19 {
    public static ItemStack[] getItemStack(){
        return new ItemStack[] {
                new ItemStack(Material.valueOf("lime_terracotta".toUpperCase())),
                new ItemStack(Material.valueOf("red_terracotta".toUpperCase()))
        };
    }

    public static boolean cancelPistonEvent(BlockPhysicsEvent e){
        e.setCancelled(true);
        return true;
    }
}
