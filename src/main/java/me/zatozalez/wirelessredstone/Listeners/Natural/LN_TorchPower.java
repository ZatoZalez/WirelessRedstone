package me.zatozalez.wirelessredstone.Listeners.Natural;

import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import me.zatozalez.wirelessredstone.Utils.U_Environment;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Lightable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import static me.zatozalez.wirelessredstone.Utils.U_Environment.getTorchBlock;

public class LN_TorchPower implements Listener {
    public LN_TorchPower(WirelessRedstone plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(BlockPlaceEvent e) {
        if(!e.getBlock().getType().equals(Material.REDSTONE_WALL_TORCH) && !e.getBlock().getType().equals(Material.REDSTONE_TORCH))
            return;
        handle(e.getBlock(), null);
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(BlockPhysicsEvent e) {
        if(!e.getBlock().getType().equals(Material.REDSTONE_WALL_TORCH) && !e.getBlock().getType().equals(Material.REDSTONE_TORCH))
            return;
        handle(e.getBlock(), e);
    }

    private static void handle(Block torch, BlockPhysicsEvent e){
        Block block = getTorchBlock(torch);
        Location location = block.getLocation();
        R_Device device = R_Devices.get(location);

        if(device == null)
            return;

        if(device.isReceiver()) {
            if(device.getSignalPower() > 0)
            {
                if(e == null){
                    Lightable lightable = (Lightable) torch.getBlockData();
                    lightable.setLit(false);
                    torch.setBlockData(lightable);
                }
                else
                e.setCancelled(true);
            }
        }
    }
}
