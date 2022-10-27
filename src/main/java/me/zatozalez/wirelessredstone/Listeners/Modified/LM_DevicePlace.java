package me.zatozalez.wirelessredstone.Listeners.Modified;

import me.zatozalez.wirelessredstone.Config.C_Value;
import me.zatozalez.wirelessredstone.Events.E_DevicePlace;
import me.zatozalez.wirelessredstone.Messages.M_Utility;
import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static me.zatozalez.wirelessredstone.Messages.M_Utility.placeHolder;

public class LM_DevicePlace implements Listener {
    public LM_DevicePlace(WirelessRedstone plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEvent(E_DevicePlace e) {
        R_Device device = new R_Device(e.getDeviceType(), e.getBlockPlaceEvent().getBlock());
        if(C_Value.allowMessages())
            if(e.getPlayer() != null){
                device.setPlayerId(e.getPlayer().getUniqueId());
                e.getPlayer().sendMessage(M_Utility.getMessage("device_place", placeHolder("${devicetype}", e.getDeviceType())));
            }
        R_Devices.add(device);
        device.updateSignalPower();
    }
}

