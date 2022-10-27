package me.zatozalez.wirelessredstone.Listeners.Natural;

import me.zatozalez.wirelessredstone.Events.E_DeviceBreak;
import me.zatozalez.wirelessredstone.Messages.M_Utility;
import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import me.zatozalez.wirelessredstone.Redstone.R_Items;
import me.zatozalez.wirelessredstone.Utils.U_Permissions;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

public class LN_BlockBreak implements Listener {
    public LN_BlockBreak(WirelessRedstone plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEvent(BlockBreakEvent e) {
        Location location = e.getBlock().getLocation();
        R_Device device = R_Devices.get(location);
        Player player = e.getPlayer();
        if(device == null)
            return;

        if(U_Permissions.isEnabled())
            if(!U_Permissions.check(player, U_Permissions.Permissions.WIRELESSREDSTONE_DEVICE_BREAK)){
                player.sendMessage(M_Utility.getMessage("device_no_permission_break"));
                e.setCancelled(true);
                return;
            }

        if(U_Permissions.isEnabled())
            if(!U_Permissions.check(player, U_Permissions.Permissions.WIRELESSREDSTONE_LINK_BREAK) && device.isLinked()){
                player.sendMessage(M_Utility.getMessage("device_no_permission_break_link"));
                e.setCancelled(true);
                return;
            }

        if(e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            ItemStack item;
            if (device.isSender())
                item = R_Items.RedstoneSender.itemStack;
            else
                item = R_Items.RedstoneReceiver.itemStack;
            device.getWorld().dropItemNaturally(device.getLocation(), item);
        }

        Bukkit.getServer().getPluginManager().callEvent(new E_DeviceBreak(device, e.getPlayer(), e));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent2(EntityExplodeEvent e) {
        for(Block block : e.blockList())
        {
            Location location = block.getLocation();
            R_Device device = R_Devices.get(location);

            if(device == null)
                continue;

            ItemStack item;
            if(device.isSender())
                item = R_Items.RedstoneSender.itemStack;
            else
                item = R_Items.RedstoneReceiver.itemStack;
            block.setType(Material.AIR);
            block.getWorld().dropItemNaturally(block.getLocation(), item);
            Bukkit.getServer().getPluginManager().callEvent(new E_DeviceBreak(device, e));
        }
    }
}