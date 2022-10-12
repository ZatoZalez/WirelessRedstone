package me.zatozalez.wirelessredstone.Commands;

import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Manager;
import me.zatozalez.wirelessredstone.Utils.U_Permissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class C_GiveDevice implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args){
        if(!(sender instanceof Player))
            return false;

        Player player = ((Player) sender).getPlayer();
        Player target;

        R_Device.DeviceType deviceType;
        ItemStack item;
        int amount;

        if(!hasValidArguments(player, args))
            return true;

        target = hasValidTarget(player, args);
        if(target == null)
            return true;

        deviceType = hasValidDevice(player, args);
        if(deviceType == null)
            return true;

        if(!hasValidPermission(player, deviceType))
            return true;

        amount = hasValidAmount(player, args);
        if(amount <= 0)
            return true;

        if(player != null)
            if(!player.equals(target))
                player.sendMessage(ChatColor.GREEN + "Giving "
                        + ChatColor.GOLD + target.getDisplayName() + " "
                        + ChatColor.GOLD + amount + " "
                        + ChatColor.GOLD + deviceType
                        + ChatColor.GREEN+ ".");

        target.sendMessage(ChatColor.GREEN + "You have received "
                        + ChatColor.GOLD + amount + " "
                        + ChatColor.GOLD + deviceType
                        + ChatColor.GREEN + ".");

        if(deviceType.equals(R_Device.DeviceType.RedstoneSender))
            item = R_Manager.RedstoneSender;
        else
            item = R_Manager.RedstoneReceiver;

        item.setAmount(amount);
        PlayerInventory inv = target.getInventory();
        inv.addItem(item);
        return true;
    }

    private boolean hasValidArguments(Player player, String[] args){
        if(args.length < 2 || args.length > 3) {
            player.sendMessage(ChatColor.RED + "Invalid amount of arguments used. Please use");
            player.sendMessage(ChatColor.RED + "/givedevice <player> <item> <amount>");
            return false;
        }
        return true;
    }

    private Player hasValidTarget(Player player, String[] args){
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            player.sendMessage(ChatColor.RED + "Could not find "
                    + ChatColor.DARK_RED + args[0]
                    + ChatColor.RED + ".");
            player.sendMessage(ChatColor.RED + "Please use a valid online player.");
            return null;
        }
        return target;
    }

    private R_Device.DeviceType hasValidDevice(Player player, String[] args) {
        R_Device.DeviceType deviceType;
        if(args[1].equalsIgnoreCase(R_Device.DeviceType.RedstoneSender.toString().toLowerCase()) || args[1].equalsIgnoreCase(R_Device.DeviceType.RedstoneSender.toString().substring(8)))
            deviceType = R_Device.DeviceType.RedstoneSender;
        else if(args[1].equalsIgnoreCase(R_Device.DeviceType.RedstoneReceiver.toString().toLowerCase()) || args[1].equalsIgnoreCase(R_Device.DeviceType.RedstoneReceiver.toString().substring(8)))
            deviceType = R_Device.DeviceType.RedstoneReceiver;
        else {
            player.sendMessage(ChatColor.RED + "Could not find a "
                    + ChatColor.DARK_RED + args[1]
                    + ChatColor.RED + ".");
            player.sendMessage(ChatColor.RED + "Please use "
                    + ChatColor.DARK_RED + "RedstoneSender"
                    + " or "
                    + ChatColor.DARK_RED + "RedstoneReceiver"
                    + ChatColor.RED + ".");
            return null;
        }
        return deviceType;
    }

    private boolean hasValidPermission(Player player, R_Device.DeviceType deviceType){
        if(U_Permissions.wirelessRedstonePermissionsEnabled()) {
            if (!U_Permissions.wirelessRedstoneCommandsGiveDeviceRedstoneSender(player) && deviceType.equals(R_Device.DeviceType.RedstoneSender)) {
                player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return false;
            } else if (!U_Permissions.wirelessRedstoneCommandsGiveDeviceRedstoneReceiver(player) && deviceType.equals(R_Device.DeviceType.RedstoneReceiver)) {
                player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return false;
            }
        }
        return true;
    }

    private int hasValidAmount(Player player, String[] args){
        try {
            if(args.length != 3)
                return 1;
            int i = Integer.parseInt(args[2]);
            if(i > 0)
                return i;
        }catch (Exception ignored) { }
        player.sendMessage(ChatColor.RED + "Invalid amount of '" + ChatColor.DARK_RED + args[2] + "'.");
        player.sendMessage(ChatColor.RED + "Please use a valid amount above 0.");
        return 0;
    }
}
