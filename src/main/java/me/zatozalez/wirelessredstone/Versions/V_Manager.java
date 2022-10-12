package me.zatozalez.wirelessredstone.Versions;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class V_Manager {
    public static String getVersion(){
        String version = Bukkit.getVersion();
        version = version.substring(version.indexOf("(") + 1);
        version = version.substring(0, version.indexOf(")"));
        version = version.substring(4);
        return (version.split("[.]")[0] + "." +  version.split("[.]")[1]);
    }

    public static boolean isCompatible(){
        switch(getVersion()) {
            case "1.8":
            case "1.12":
            case "1.18":
            default:
                return false;
        }
    }

    public static ItemStack[] getItemStack(){
        switch(getVersion()) {
            case "1.8":
                return V_1_8.getItemStack();
            case "1.12":
                return V_1_12.getItemStack();
            case "1.18":
                return V_1_18.getItemStack();
        }
        return null;
    }
}
