package me.zatozalez.wirelessredstone.Listeners.Modified;

import me.zatozalez.wirelessredstone.Events.E_DevicePlace;
import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class LM_DevicePlace implements Listener {
    public LM_DevicePlace(WirelessRedstone plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEvent(E_DevicePlace e) {
        R_Device device = new R_Device(e.getDeviceType(), e.getBlockPlaceEvent().getBlock());
        if(e.getPlayer() != null){
            device.setPlayerId(e.getPlayer().getUniqueId());
            e.getPlayer().sendMessage(ChatColor.GOLD + "You have placed a " + ChatColor.GREEN + e.getDeviceType().toString() + ChatColor.GOLD + ".");
        }

        R_Devices.add(device);
        device.updateSignalPower();
    }
}

