package me.zatozalez.wirelessredstone.Versions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Piston;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.inventory.ItemStack;

public class V_1_17 {
    public static ItemStack[] getItemStack(){
        return new ItemStack[] {
                new ItemStack(Material.LIME_TERRACOTTA),
                new ItemStack(Material.RED_TERRACOTTA)
        };
    }

    public static boolean cancelPistonEvent(BlockPhysicsEvent e, Block pistonBlock, Piston piston){
        if(!e.getSourceBlock().equals(pistonBlock) || (!pistonBlock.isBlockIndirectlyPowered() && !piston.isExtended()))
        {
            e.setCancelled(true);
            return true;
        }
        return false;
    }
}