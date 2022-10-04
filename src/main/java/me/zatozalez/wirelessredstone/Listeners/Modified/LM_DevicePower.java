package me.zatozalez.wirelessredstone.Listeners.Modified;

import me.zatozalez.wirelessredstone.Events.E_DevicePower;
import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LM_DevicePower implements Listener {
    public LM_DevicePower(WirelessRedstone plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEvent(E_DevicePower e) {
        R_Device device = e.getDevice();
        device.updateSignalPower();
    }
}
