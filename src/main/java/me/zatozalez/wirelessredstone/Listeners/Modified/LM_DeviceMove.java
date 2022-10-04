package me.zatozalez.wirelessredstone.Listeners.Modified;

import me.zatozalez.wirelessredstone.Events.E_DeviceMove;
import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class LM_DeviceMove implements Listener {
    public LM_DeviceMove(WirelessRedstone plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEvent(E_DeviceMove e) {
        R_Device device = e.getDevice();

        device.setLocation(e.getNewLocation());
        new BukkitRunnable() {
            @Override
            public void run() {
                if(device.getDeviceType().equals(R_Device.DeviceType.RedstoneSender)){
                    device.setSignalPower(device.getBlock().getBlockPower());
                    device.sendSignal();
                }
                else
                    device.emitSignal(device.getSignalPower());
            }

        }.runTaskLater(WirelessRedstone.getPlugin(), 0);
    }
}
