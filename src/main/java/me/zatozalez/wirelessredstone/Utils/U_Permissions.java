package me.zatozalez.wirelessredstone.Utils;

import me.zatozalez.wirelessredstone.Config.C_Value;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

//REWORKED
public class U_Permissions {
    public enum Permissions{
        WIRELESSREDSTONE_,

        WIRELESSREDSTONE_COMMANDS_,
        WIRELESSREDSTONE_COMMANDS_WIRELESSREDSTONE_,
        WIRELESSREDSTONE_COMMANDS_WIRELESSREDSTONE_RELOAD,
        WIRELESSREDSTONE_COMMANDS_WIRELESSREDSTONE_DISABLE,


        WIRELESSREDSTONE_COMMANDS_DEVICE_,
        WIRELESSREDSTONE_COMMANDS_DEVICE_GIVE,
        WIRELESSREDSTONE_COMMANDS_DEVICE_DELETEALL,
        WIRELESSREDSTONE_COMMANDS_DEVICE_INFO,
        WIRELESSREDSTONE_COMMANDS_DEVICE_LINK_CANCEL,
        WIRELESSREDSTONE_COMMANDS_DEVICE_LINK_BREAKALL,
        WIRELESSREDSTONE_COMMANDS_DEVICE_LINK_BREAKFIRST,
        WIRELESSREDSTONE_COMMANDS_DEVICE_LINK_BREAKLAST,

        WIRELESSREDSTONE_LINK_,
        WIRELESSREDSTONE_LINK_CREATE,
        WIRELESSREDSTONE_LINK_BREAK,
        WIRELESSREDSTONE_LINK_NOLIMIT,
        WIRELESSREDSTONE_LINK_INFINITEDISTANCE,
        WIRELESSREDSTONE_LINK_CROSSWORLD,
        WIRELESSREDSTONE_LINK_DESTROYTHIRDLINKS,
        WIRELESSREDSTONE_LINK_CREATETHIRDLINKS,

        WIRELESSREDSTONE_DEVICE_,
        WIRELESSREDSTONE_DEVICE_PLACE,
        WIRELESSREDSTONE_DEVICE_BREAK,
        WIRELESSREDSTONE_DEVICE_NOPLACELIMIT,
        WIRELESSREDSTONE_DEVICE_NOLINKLIMIT,
        WIRELESSREDSTONE_DEVICE_BREAKTHIRDDEVICES,
    }

    public static void register(){
        for(Permissions perm : Permissions.values()){
            Permission p = new Permission(getNode(perm));
            Bukkit.getPluginManager().addPermission(p);
        }
    }

    public static boolean isEnabled(){
        return C_Value.allowPermissions();
    }

    public static boolean check(Player player, Permissions perm){
        return (hasPermission(player, getNode(perm)) || player.isOp());
    }

    private static String getNode(Permissions perm){
        String p = perm.toString();
        if(p.endsWith("_"))
            p = p.substring(0,p.length() - 1) + ".*";
        return p.toLowerCase().replace("_", ".");
    }

    private static boolean hasPermission(Player player, String node){
        String n = "";
        long count = node.chars().filter(ch -> ch == '.').count();
        for(int i = 0; i < count; i++){
            n += node.split("\\.")[i];
            if(!n.equals(node))
                n += ".";
            if(player.hasPermission(n + "*"))
                return true;
        }
        return player.hasPermission(node);
    }
}
