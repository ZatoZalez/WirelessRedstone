package me.zatozalez.wirelessredstone.Commands.WirelessRedstone;

import me.zatozalez.wirelessredstone.Messages.M_Utility;
import me.zatozalez.wirelessredstone.Utils.U_Log;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.entity.Player;


public class CW_Push {
    public static boolean pushInvalidArguments(Player player, String[] commands){
        String s = "";
        for(String str : commands)
            s = s + "\n" + str;
        player.sendMessage(M_Utility.getMessage("invalid_arguments", M_Utility.placeHolder("${commandlist}", s)));
        return true;
    }

    public static boolean pushInvalidPermission(Player player){
        player.sendMessage(M_Utility.getMessage("invalid_permission"));
        return true;
    }

    public static boolean pushReloading(Player player){
        player.sendMessage(M_Utility.getMessage("plugin_reload"));
        WirelessRedstone.Log(new U_Log(U_Log.LogType.WARNING, "Reloading plugin..."));
        return true;
    }

    public static boolean pushReloaded(Player player){
        player.sendMessage(M_Utility.getMessage("plugin_reloaded"));
        return true;
    }

    public static boolean pushDisabling(Player player){
        player.sendMessage(M_Utility.getMessage("plugin_disable"));
        WirelessRedstone.Log(new U_Log(U_Log.LogType.WARNING, "Disabling plugin..."));
        return true;
    }
}
