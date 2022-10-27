package me.zatozalez.wirelessredstone.Commands;

import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import me.zatozalez.wirelessredstone.Redstone.R_Manager;
import me.zatozalez.wirelessredstone.Utils.U_Environment;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Piston;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.Method;

public class C_Debug implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String [] args) {
        if (!(sender instanceof Player))
            return false;


        //Command variables
        Player player = ((Player) sender).getPlayer();
        Block block = player.getTargetBlockExact(10);

        R_Device device = R_Devices.get(block.getLocation());
        if(device != null)
            Bukkit.broadcastMessage("power: " + device.getSignalPower());

        Bukkit.broadcastMessage("block: " + block.getType().toString());
        return true;
    }
}

