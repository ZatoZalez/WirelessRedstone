package me.zatozalez.wirelessredstone.Listeners.Natural;

import me.zatozalez.wirelessredstone.Events.E_DevicePower;
import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import me.zatozalez.wirelessredstone.Utils.U_Environment;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

public class LN_WireBreak implements Listener {
    public LN_WireBreak(WirelessRedstone plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEvent(BlockBreakEvent e) {
        if(e.getBlock().getType().equals(Material.REDSTONE_WIRE))
            Roulette(e.getBlock());
    }

    private void Roulette(Block powerBlock){
        for(Block block : U_Environment.GetSurroundingBlocks(powerBlock))
        {
            Location location = block.getLocation();
            R_Device device = R_Devices.get(location);

            if(device == null)
                continue;

            if(device.isReceiver())
                continue;

            if (U_Environment.IsFacing(block, powerBlock))
                Bukkit.getServer().getPluginManager().callEvent(new E_DevicePower(device));
        }
    }
}
