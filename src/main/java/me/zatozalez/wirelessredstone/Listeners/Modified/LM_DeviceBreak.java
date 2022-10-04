package me.zatozalez.wirelessredstone.Listeners.Modified;

import me.zatozalez.wirelessredstone.Events.E_DeviceBreak;
import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LM_DeviceBreak implements Listener {
    public LM_DeviceBreak(WirelessRedstone plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEvent(E_DeviceBreak e) {
        R_Device device = e.getDevice();

        String message = ChatColor.DARK_RED + device.getDeviceType().toString();
        if(device.isLinked())
            message += ChatColor.RED + " with " + ChatColor.DARK_RED + device.getLinks().size() + ChatColor.RED + " Link(s)";
        message += ChatColor.RED + ".";

        device.destroyLinks();
        R_Devices.remove(device);

        if(e.getPlayer() != null){
            if(!device.getPlayerId().equals(e.getPlayer().getUniqueId())){
                e.getPlayer().sendMessage(ChatColor.RED + "You have destroyed a " + message);
            }
            else{
                e.getPlayer().sendMessage(ChatColor.RED + "You have destroyed your " + message);
            }
        }
        else{
            e.getPlayer().sendMessage(ChatColor.RED + "Something has destroyed your " + message);
        }
    }
}
