package me.zatozalez.wirelessredstone.Commands.Device;

import me.zatozalez.wirelessredstone.Redstone.DeviceType;
import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import me.zatozalez.wirelessredstone.Redstone.R_Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static me.zatozalez.wirelessredstone.Commands.Device.CD_Push.pushInvalidTargetBlock;

//REWORKED
public class CD_Utility {
    public static R_Device getDeviceByTarget(Player player){
        Block block = player.getTargetBlockExact(10);
        if(block == null)
            return null;
        return R_Devices.get(block.getLocation());
    }

    public static boolean isLinkedWithDevice(Player player, R_Device device, String[] args){
        if(device != null  && !device.isLinked() && !args[1].equals("cancel"))
            return pushInvalidTargetBlock(player);
        return true;
    }

    public static void giveDeviceItemStack(Player player, int amount, ItemStack[] itemStacks){
        for(ItemStack itemStack : itemStacks){
            giveItemStack(player, amount, itemStack.clone());
        }
    }

    public static void giveItemStack(Player player, int amount, ItemStack itemStack){
        itemStack.setAmount(amount);
        player.getInventory().addItem(itemStack);
    }

    public static String getPlayerString(Player target, boolean allPlayers){
        if(!allPlayers && target != null)
            return target.getDisplayName();
        if(Bukkit.getOnlinePlayers().size() > 1)
            return Bukkit.getOnlinePlayers().size() + " players";
        return Bukkit.getOnlinePlayers().size() + " player";
    }

    public static String getDeviceTypeString(DeviceType deviceType, int amount, boolean bothDevices){
        String deviceString = "RedstoneDevice Set";
        if(!bothDevices && deviceType != null)
            deviceString = deviceType.toString();
        if(amount > 1)
            deviceString += "s";
        return deviceString;
    }

    public static ItemStack[] getItemStacks(DeviceType deviceType, boolean bothDevices){
        ItemStack[] itemStack = new ItemStack[] { R_Items.RedstoneReceiver.itemStack, R_Items.RedstoneSender.itemStack };
        if(!bothDevices){
            if(deviceType.equals(DeviceType.RedstoneSender))
            {
                return new ItemStack[] { R_Items.RedstoneSender.itemStack };
            }
            else
            {
                return new ItemStack[] { R_Items.RedstoneReceiver.itemStack };
            }
        }
        return itemStack;
    }
}
