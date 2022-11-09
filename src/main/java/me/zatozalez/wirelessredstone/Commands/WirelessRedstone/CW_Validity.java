package me.zatozalez.wirelessredstone.Commands.WirelessRedstone;

import me.zatozalez.wirelessredstone.Utils.U_Permissions;
import org.bukkit.entity.Player;

import static me.zatozalez.wirelessredstone.Commands.WirelessRedstone.CW_Push.pushInvalidArguments;
import static me.zatozalez.wirelessredstone.Commands.WirelessRedstone.CW_Push.pushInvalidPermission;

public class CW_Validity {
    public static boolean hasValidArguments(Player player, String[] args, int min, int max, String[] commands){
        if(args.length < min || args.length > max)
            return (!pushInvalidArguments(player, commands));
        return true;
    }

    public static boolean hasValidArguments(Player player, String[] args, int range, String[] commands){
        if(args.length != range)
            return (!pushInvalidArguments(player, commands));
        return true;
    }

    public static boolean hasValidPermission(Player player){
        if(U_Permissions.isEnabled())
            if (!U_Permissions.check(player, U_Permissions.Permissions.WIRELESSREDSTONE_COMMANDS_DEVICE_GIVE)) {
                pushInvalidPermission(player);
                return false;
            }
        return true;
    }
}
