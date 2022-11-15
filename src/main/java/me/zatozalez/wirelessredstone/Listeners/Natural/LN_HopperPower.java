package me.zatozalez.wirelessredstone.Listeners.Natural;

import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import me.zatozalez.wirelessredstone.Utils.U_Environment;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockRedstoneEvent;


public class LN_HopperPower  implements Listener {
    public LN_HopperPower(WirelessRedstone plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(BlockPhysicsEvent e) {
        if (!e.getBlock().getType().equals(Material.HOPPER) && !e.getSourceBlock().getType().equals(Material.HOPPER) && !e.getChangedType().equals(Material.HOPPER))
            return;

        handle(e.getBlock(), e);
    }

    private void handle(Block powerBlock, BlockPhysicsEvent e){
        for(R_Device device : U_Environment.getSurroundingDevices(powerBlock))
        {
            if(device == null)
                continue;

            if(device.isReceiver() && device.getSignalPower() > 0)
                e.setCancelled(true);
        }
    }
}
