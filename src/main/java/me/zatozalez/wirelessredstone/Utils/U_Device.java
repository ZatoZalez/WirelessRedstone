package me.zatozalez.wirelessredstone.Utils;

import me.zatozalez.wirelessredstone.Config.C_Value;
import me.zatozalez.wirelessredstone.Redstone.DeviceType;
import org.bukkit.ChatColor;

public class U_Device {
    public static String getDeviceName(DeviceType deviceType){
        if(deviceType.equals(DeviceType.RedstoneSender))
            if(C_Value.getSenderItemName() != null){
                return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', C_Value.getSenderItemName()));
            }
            else {
                return "Redstone Sender";
            }
        if(C_Value.getReceiverItemName() != null){
            return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', C_Value.getReceiverItemName()));
        }
        else {
            return "Redstone Receiver";
        }
    }

    public static String getDeviceNameWithColor(DeviceType deviceType){
        if(deviceType.equals(DeviceType.RedstoneSender))
            if(C_Value.getSenderItemName() != null){
                return ChatColor.translateAlternateColorCodes('&', C_Value.getSenderItemName());
            }
            else {
                return "Redstone Sender";
            }
        if(C_Value.getReceiverItemName() != null){
            return ChatColor.translateAlternateColorCodes('&', C_Value.getReceiverItemName());
        }
        else {
            return "Redstone Receiver";
        }
    }
}
