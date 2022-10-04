package me.zatozalez.wirelessredstone.Commands;

import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import me.zatozalez.wirelessredstone.Redstone.R_Manager;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class C_Debug implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String [] args) {
        if (!(sender instanceof Player))
            return false;


        //Command variables
        Player player = ((Player) sender).getPlayer();
        Block b = player.getTargetBlockExact(10);
        BlockState s = b.getState();

        //s.update(true, true);
        R_Device device = R_Devices.get(b.getLocation());
        if(device == null)
            return true;

        player.sendMessage("POWER: " + device.getBlock().getBlockPower());

        if(true)
            return true;
        PlayerInventory inv = player.getInventory();
        ItemStack item = null;

        item = R_Manager.RedstoneSender;
        item.setAmount(1);

        inv.addItem(item);
        item = R_Manager.RedstoneReceiver;
        inv.addItem(item);
        return true;
    }
}

