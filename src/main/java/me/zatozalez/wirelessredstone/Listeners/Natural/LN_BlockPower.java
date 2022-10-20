package me.zatozalez.wirelessredstone.Listeners.Natural;

import me.zatozalez.wirelessredstone.Events.E_DevicePower;
import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import me.zatozalez.wirelessredstone.Redstone.R_Manager;
import me.zatozalez.wirelessredstone.Utils.U_Environment;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.Powerable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockRedstoneEvent;

public class LN_BlockPower implements Listener {
    public LN_BlockPower(WirelessRedstone plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEvent(BlockPhysicsEvent e){
        if(!e.getBlock().getType().equals(R_Manager.RedstoneSenderMaterial)) return;
        if(!(e.getSourceBlock().getBlockData() instanceof AnaloguePowerable) && !(e.getSourceBlock().getBlockData() instanceof Powerable)) return;

        Location location = e.getBlock().getLocation();
        R_Device device = R_Devices.get(location);

        if(device == null)
            return;

        if(device.getSignalPower() != device.getBlock().getBlockPower())
            Bukkit.getServer().getPluginManager().callEvent(new E_DevicePower(device));
    }

    @EventHandler
    public void onEvent2(BlockPhysicsEvent e){
        Block block = e.getBlock();
        for(Block b : U_Environment.GetSurroundingBlocks(block))
        {
            Location location = b.getLocation();
            R_Device device = R_Devices.get(location);

            if(device == null)
                continue;

            if(device.isSender()) {
                device.updateSignalPower();
            }
        }
    }

    @EventHandler
    public void onEvent3(BlockRedstoneEvent e){
        roulette(e.getBlock(), e);
    }

    private void roulette(Block powerBlock, BlockRedstoneEvent e){
        for(Block block : U_Environment.GetSurroundingBlocks(powerBlock))
        {
            Location location = block.getLocation();
            R_Device device = R_Devices.get(location);

            if(device == null)
                continue;

            if(device.isReceiver())
                handlePermanentWire(device, e);
        }
    }

    private void handlePermanentWire(R_Device device, BlockRedstoneEvent e){
        if(device.getSignalPower() != 0 && device.getSignalPower() > e.getNewCurrent())
            e.setNewCurrent(device.getSignalPower());
    }
}
