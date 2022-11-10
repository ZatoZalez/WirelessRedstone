package me.zatozalez.wirelessredstone.Listeners.Natural;

import me.zatozalez.wirelessredstone.Events.E_DeviceMove;
import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import me.zatozalez.wirelessredstone.Utils.U_Environment;
import me.zatozalez.wirelessredstone.Versions.V_Manager;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Piston;
import org.bukkit.block.data.type.PistonHead;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

import java.util.ArrayList;
import java.util.List;

public class LN_BlockMove implements Listener {
    public LN_BlockMove(WirelessRedstone plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(BlockPistonExtendEvent e) {
        move(e.getBlocks(), e.getDirection());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent2(BlockPistonRetractEvent e) {
        roulette(e.getBlock(), e);
        move(e.getBlocks(), e.getDirection());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEvent3(BlockPhysicsEvent e){
        Block pistonBlock = e.getBlock();
        if(pistonBlock.getType().equals(Material.PISTON_HEAD)){
            if(parentTouchesDevice(pistonBlock)){
                e.setCancelled(true);
            }
            return;
        }

        for(R_Device device : U_Environment.getSurroundingDevices(pistonBlock))
        {
            if(device == null)
                continue;

            if(device.isReceiver()) {
                if(pistonBlock.getType().equals(Material.PISTON) || pistonBlock.getType().equals(Material.STICKY_PISTON)){
                    Piston piston = (Piston) pistonBlock.getBlockData();
                    V_Manager.cancelPistonEvent(e, pistonBlock, piston);
                }
            }
        }
    }

    private void move(List<Block> blocks, BlockFace face) {
        if (blocks.size() < 1)
            return;

        List<R_Device> devices = new ArrayList<>();

        for (Block block : blocks) {
            Location loc = block.getLocation();
            R_Device device = R_Devices.get(loc);

            if (device == null)
                continue;

            devices.add(device);
        }

        for (R_Device device : devices) {
            Location oldLocation = device.getLocation();
            Location newLocation = oldLocation;
            switch (face) {
                case NORTH:
                    newLocation.setZ(newLocation.getZ() - 1);
                    break;
                case EAST:
                    newLocation.setX(newLocation.getX() + 1);
                    break;
                case SOUTH:
                    newLocation.setZ(newLocation.getZ() + 1);
                    break;
                case WEST:
                    newLocation.setX(newLocation.getX() - 1);
                    break;
                case UP:
                    newLocation.setY(newLocation.getY() + 1);
                    break;
                case DOWN:
                    newLocation.setY(newLocation.getY() - 1);
                    break;
            }

            Bukkit.getServer().getPluginManager().callEvent(new E_DeviceMove(device, oldLocation, newLocation));
        }
    }

    private void roulette(Block pistonBlock, BlockPistonRetractEvent e){
        for(R_Device device : U_Environment.getSurroundingDevices(pistonBlock))
        {
            if(device == null)
                continue;

            if(device.isReceiver())
                handlePermanentWire(device, e);
        }
    }

    private void handlePermanentWire(R_Device device, BlockPistonRetractEvent e){
        if(device.getSignalPower() != 0){
            e.setCancelled(true);
        }
    }

    private boolean parentTouchesDevice(Block headBlock){
        Block pistonBlock = getParentPiston(headBlock);
        for(R_Device device : U_Environment.getSurroundingDevices(pistonBlock))
        {
            if(device == null)
                continue;

            if(device.isReceiver()) {
                return true;
            }
        }
        return false;
    }

    public static Block getParentPiston(Block headBlock){
        if(!headBlock.getType().equals(Material.PISTON_HEAD))
            return null;

        PistonHead pistonHead = (PistonHead) headBlock.getBlockData();
        return headBlock.getRelative(pistonHead.getFacing().getOppositeFace());
    }
}
