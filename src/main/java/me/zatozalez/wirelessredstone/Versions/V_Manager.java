package me.zatozalez.wirelessredstone.Versions;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Piston;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.inventory.ItemStack;

public class V_Manager {
    public static String version = "0.0.0";
    public static void setVersion(){
        String v = Bukkit.getVersion();
        v = v.substring(v.indexOf("(") + 1);
        v = v.substring(0, v.indexOf(")"));
        v = v.substring(4);
        version = (v.split("[.]")[0] + "." +  v.split("[.]")[1]);
    }

    public static boolean isCompatible(){
        switch(version) {
            case "1.16":
            case "1.17":
            case "1.18":
            case "1.19":
                return true;
            default:
                return false;
        }
    }

    public static ItemStack[] getItemStack(){
        switch(version) {
            case "1.16":
                return V_1_16.getItemStack();
            case "1.17":
                return V_1_17.getItemStack();
            case "1.18":
                return V_1_18.getItemStack();
            case "1.19":
                return V_1_19.getItemStack();
        }
        return null;
    }

    public static boolean cancelPistonEvent(BlockPhysicsEvent e, Block pistonBlock, Piston piston){
        switch(version) {
            case "1.16":
                return V_1_16.cancelPistonEvent(e, pistonBlock, piston);
            case "1.17":
                return V_1_17.cancelPistonEvent(e, pistonBlock, piston);
            case "1.18":
                return V_1_18.cancelPistonEvent(e, pistonBlock, piston);
            case "1.19":
                return V_1_19.cancelPistonEvent(e);
            default:
                return false;
        }
    }
}
