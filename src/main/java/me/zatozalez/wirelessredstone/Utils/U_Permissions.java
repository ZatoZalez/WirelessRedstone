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
        return C_Value.allowPermissions();
    }

    public static boolean wirelessRedstoneCommandsCancelLink(Player player){
        return player.hasPermission("wirelessredstone.commands.cancellink");
    }

    public static boolean wirelessRedstoneCommandsGiveDeviceRedstoneSender(Player player){
        return player.hasPermission("wirelessredstone.commands.givedevice.redstonesender");
    }

    public static boolean wirelessRedstoneCommandsGiveDeviceRedstoneReceiver(Player player){
        return player.hasPermission("wirelessredstone.commands.givedevice.redstonereceiver");
    }

    public static boolean wirelessRedstoneLinkCreate(Player player){
        return player.hasPermission("wirelessredstone.link.create");
    }

    public static boolean wirelessRedstoneLinkBreak(Player player){
        return player.hasPermission("wirelessredstone.link.break");
    }

    public static boolean wirelessRedstoneLinkNoLimit(Player player){
        return player.hasPermission("wirelessredstone.link.nolimit");
    }

    public static boolean wirelessRedstoneLinkInfiniteDistance(Player player){
        return player.hasPermission("wirelessredstone.link.infinitedistance");
    }

    public static boolean wirelessRedstoneLinkCrossWorld(Player player){
        return player.hasPermission("wirelessredstone.link.crossworld");
    }

    public static boolean wirelessRedstoneDevicePlace(Player player){
        return player.hasPermission("wirelessredstone.device.place");
    }

    public static boolean wirelessRedstoneDeviceBreak(Player player){
        return player.hasPermission("wirelessredstone.device.break");
    }

    public static boolean wirelessRedstoneDeviceNoPlaceLimit(Player player){
        return player.hasPermission("wirelessredstone.device.noplacelimit");
    }

    public static boolean wirelessRedstoneDeviceNoLinkLimit(Player player){
        return player.hasPermission("wirelessredstone.device.noplacelimit");
    }
}
