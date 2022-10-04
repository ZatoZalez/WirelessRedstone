package me.zatozalez.wirelessredstone.Listeners.Natural;

import me.zatozalez.wirelessredstone.Events.E_DeviceBreak;
import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import me.zatozalez.wirelessredstone.Redstone.R_Links;
import me.zatozalez.wirelessredstone.Redstone.R_Manager;
import me.zatozalez.wirelessredstone.Utils.U_Permissions;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.*;
import org.bukkit.block.Block;
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

        if(device == null)
            return;

        if(U_Permissions.wirelessRedstonePermissionsEnabled())
            if(!U_Permissions.wirelessRedstoneDeviceBreak(e.getPlayer())){
                e.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to destroy devices.");
                e.setCancelled(true);
                return;
            }

        if(U_Permissions.wirelessRedstonePermissionsEnabled())
            if(!U_Permissions.wirelessRedstoneLinkBreak(e.getPlayer()) && device.isLinked()){
                e.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to destroy linked devices.");
                e.setCancelled(true);
                return;
            }

        if(e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            ItemStack item;
            if (device.getDeviceType().equals(R_Device.DeviceType.RedstoneSender))
                item = R_Manager.RedstoneSender;
            else
                item = R_Manager.RedstoneReceiver;
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
            if(device.getDeviceType().equals(R_Device.DeviceType.RedstoneSender))
                item = R_Manager.RedstoneSender;
            else
                item = R_Manager.RedstoneReceiver;
            block.setType(Material.AIR);
            block.getWorld().dropItemNaturally(block.getLocation(), item);

            Bukkit.getServer().getPluginManager().callEvent(new E_DeviceBreak(device, e));
        }
    }
}