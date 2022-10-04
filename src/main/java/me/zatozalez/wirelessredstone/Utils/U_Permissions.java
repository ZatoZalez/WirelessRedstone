package me.zatozalez.wirelessredstone.Utils;

import me.zatozalez.wirelessredstone.Config.C_Value;
import org.bukkit.entity.Player;

public class U_Permissions {
    //wirelessredstone.commands.cancellink
    //wirelessredstone.commands.givedevice.redstonesender
    //wirelessredstone.commands.givedevice.redstonereceiver

    //wirelessredstone.link.create
    //wirelessredstone.link.break
    //wirelessredstone.link.nolimit
    //wirelessredstone.link.infinitedistance
    //wirelessredstone.link.crossworld

    //wirelessredstone.device.place
    //wirelessredstone.device.break
    //wirelessredstone.device.noplacelimit
    //wirelessredstone.device.nolinklimit

    public static boolean wirelessRedstonePermissionsEnabled(){
        return C_Value.permissionsEnabled();
    }

    public static boolean wirelessRedstoneCommandsCancelLink(Player player){
        if(player.hasPermission("wirelessredstone.commands.cancellink"))
            return true;
        return false;
    }

    public static boolean wirelessRedstoneCommandsGiveDeviceRedstoneSender(Player player){
        if(player.hasPermission("wirelessredstone.commands.givedevice.redstonesender"))
            return true;
        return false;
    }

    public static boolean wirelessRedstoneCommandsGiveDeviceRedstoneReceiver(Player player){
        if(player.hasPermission("wirelessredstone.commands.givedevice.redstonereceiver"))
            return true;
        return false;
    }

    public static boolean wirelessRedstoneLinkCreate(Player player){
        if(player.hasPermission("wirelessredstone.link.create"))
            return true;
        return false;
    }

    public static boolean wirelessRedstoneLinkBreak(Player player){
        if(player.hasPermission("wirelessredstone.link.break"))
            return true;
        return false;
    }

    public static boolean wirelessRedstoneLinkNoLimit(Player player){
        if(player.hasPermission("wirelessredstone.link.nolimit"))
            return true;
        return false;
    }

    public static boolean wirelessRedstoneLinkInfiniteDistance(Player player){
        if(player.hasPermission("wirelessredstone.link.infinitedistance"))
            return true;
        return false;
    }

    public static boolean wirelessRedstoneLinkCrossWorld(Player player){
        if(player.hasPermission("wirelessredstone.link.crossworld"))
            return true;
        return false;
    }

    public static boolean wirelessRedstoneDevicePlace(Player player){
        if(player.hasPermission("wirelessredstone.device.place"))
            return true;
        return false;
    }

    public static boolean wirelessRedstoneDeviceBreak(Player player){
        if(player.hasPermission("wirelessredstone.device.break"))
            return true;
        return false;
    }

    public static boolean wirelessRedstoneDeviceNoPlaceLimit(Player player){
        if(player.hasPermission("wirelessredstone.device.noplacelimit"))
            return true;
        return false;
    }

    public static boolean wirelessRedstoneDeviceNoLinkLimit(Player player){
        if(player.hasPermission("wirelessredstone.device.noplacelimit"))
            return true;
        return false;
    }
}
