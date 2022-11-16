package me.zatozalez.wirelessredstone.Listeners.Modified;

import me.zatozalez.wirelessredstone.Config.C_Value;
import me.zatozalez.wirelessredstone.Events.E_DeviceBreak;
import me.zatozalez.wirelessredstone.Messages.M_Utility;
import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

//REWORKED
public class LM_DeviceBreak implements Listener {
    public LM_DeviceBreak(WirelessRedstone plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEvent(E_DeviceBreak e) {
        R_Device device = e.getDevice();

        pushMessage(device, e.getPlayer());
        device.destroyLinks();
        R_Devices.remove(device);
    }

    private static String getPrefix(R_Device device, Player player){
        String prefix = M_Utility.getMessage("device_destroyed_1", M_Utility.placeHolder("${devicetype}", device.getDeviceType()));
        if(player != null)
            if(!device.getPlayerId().equals(player.getUniqueId()))
                prefix = M_Utility.getMessage("device_destroyed_2", M_Utility.placeHolder("${devicetype}", device.getDeviceType()));
            else
                prefix = M_Utility.getMessage("device_destroyed_3", M_Utility.placeHolder("${devicetype}", device.getDeviceType()));
        return prefix;
    }

    private static String getLinks(R_Device device){
        if(device.isLinked())
            return M_Utility.getMessage("device_destroyed_with_links", M_Utility.placeHolder("${amount}", device.getLinkCount()));;
        return "";
    }

    private static void pushMessage(R_Device device, Player player){
        if(C_Value.allowMessages())
        {
            String str = getPrefix(device, player);
            if(str.endsWith("."))
                str = str.substring(0, str.length() - 1);
            str = str + getLinks(device);
            if(!str.endsWith("."))
                str += ".";
            M_Utility.sendMessage(player, str);
        }
    }
}
