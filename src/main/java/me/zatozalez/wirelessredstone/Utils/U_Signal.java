package me.zatozalez.wirelessredstone.Utils;

import me.zatozalez.wirelessredstone.Config.C_Value;
import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import me.zatozalez.wirelessredstone.Redstone.R_Link;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.block.Hopper;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Piston;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Objects;

import static me.zatozalez.wirelessredstone.Utils.U_Environment.*;

public class U_Signal {

    public static void emit(R_Device device, int signalPower){
        boolean isUpdating = device.isUpdating();
        device.setUpdating(false);
        if(device.isOverloaded())
            return;

        device.setSignalPower(signalPower);
        List<Block> blocks = U_Environment.getSurroundingBlocks(device.getBlock());

        //REDSTONE WIRE ONLY
        for(Block block : blocks){
            if(!block.getType().equals(Material.REDSTONE_WIRE))
                continue;

            BlockData data = block.getBlockData();
            if (data instanceof AnaloguePowerable) {
                powerAnaloguePowerable(block, signalPower);
            }
        }

        //REST
        for(Block block : blocks){
            if(block.getType().equals(Material.REDSTONE_WIRE))
                continue;

            BlockData data = block.getBlockData();
            if (data instanceof AnaloguePowerable) {
                powerAnaloguePowerable(block, signalPower);
                continue;
            }
            if(data instanceof Openable){
                powerOpenable(device, block);
                continue;
            }
            if (data instanceof Powerable) {
                powerPowerable(block, signalPower);
                continue;
            }
            if(data instanceof Piston){
                powerPiston(device, block);
                continue;
            }
            if(block.getState() instanceof Dispenser){
                if(!isUpdating)
                    powerDispenser(device, block);
                continue;
            }
            if(block.getState() instanceof Dropper){
                if(!isUpdating)
                    powerDropper(device, block);
                continue;
            }
            if(block.getState() instanceof Hopper){
                powerHopper(device, block);
                continue;
            }
            if(data instanceof Lightable){
                powerLightable(device, block, signalPower);
                continue;
            }
            if(C_Value.allowContactSignals()) {
                powerDevices(device, block);
            }
        }
    }

    public static void send(R_Device device, int signalPower) {
        if(device.isOverloaded())
            return;

        if(C_Value.getSignalDelay() == 0){
            for (R_Link link : device.getLinks())
                if (link.isLinked()){
                    link.getReceiver().emitSignal(signalPower);
                    link.onSignal();
                }
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                for (R_Link link : device.getLinks())
                    if (link.isLinked()){
                        link.getReceiver().emitSignal(signalPower);
                        link.onSignal();
                    }
            }
        }.runTaskLater(WirelessRedstone.getPlugin(), C_Value.getSignalDelay());
    }

    public static void update(R_Device device) {
        new BukkitRunnable() {
            @Override
            public void run() {
                int newCurrent = device.getBlock().getBlockPower();
                for(Block block : U_Environment.getSurroundingBlocks(device.getBlock())){
                    if(block.getType().equals(Material.REDSTONE_BLOCK)){
                        newCurrent = 15;
                        break;
                    }

                    if(C_Value.allowContactSignals()){
                        R_Device receiver = R_Devices.get(block.getLocation());
                        if(receiver != null && receiver.isReceiver()){
                            if(receiver.getSignalPower() > newCurrent)
                                if(!receiver.isLinkedWith(device))
                                    newCurrent = receiver.getSignalPower();
                        }
                    }
                }
                if(newCurrent == device.getSignalPower())
                    return;

                device.setSignalPower(newCurrent);
                device.sendSignal();
            }
        }.runTaskLater(WirelessRedstone.getPlugin(), 0);
    }

    //Redstone Wire
    private static void powerAnaloguePowerable(Block block, int signalPower){
        AnaloguePowerable analoguePowerable = (AnaloguePowerable) block.getBlockData();
        analoguePowerable.setPower(signalPower);
        block.setBlockData(analoguePowerable);
    }

    //Redstone Repeater
    //Redstone Comparator
    private static void powerPowerable(Block block, int signalPower){
        Powerable powerable = (Powerable) block.getBlockData();
        powerable.setPowered((signalPower > 0));
        block.setBlockData(powerable);
    }

    //Piston
    //Sticky Piston
    private static void powerPiston(R_Device device, Block block){
        if(!U_Piston.canBePowered(device, block))
            return;

        if(device.getSignalPower() == 0){
            U_Piston.retract(block);
        }
        else{
            U_Piston.extend(block);
        }
    }

    //Dispenser
    private static void powerDispenser(R_Device device, Block block){
        if(block.getBlockPower() != 0)
            return;

        if(device.getSignalPower() > 0) {
            for(R_Device d : Objects.requireNonNull(getSurroundingDevicesExcept(block, device))){
                if(d.isReceiver() && d.getSignalPower() > 0)
                    return;
            }
            Dispenser dispenser = (Dispenser) block.getState();
            dispenser.dispense();
        }
    }

    //Dropper
    private static void powerDropper(R_Device device, Block block){
        if(block.getBlockPower() != 0)
            return;

        if(device.getSignalPower() > 0) {
            for(R_Device d : Objects.requireNonNull(getSurroundingDevicesExcept(block, device))){
                if(d.isReceiver() && d.getSignalPower() > 0)
                    return;
            }

            Dropper dropper = (Dropper) block.getState();
            if(dropper.getBlock().getBlockPower() == 0)
                dropper.drop();
        }
    }

    //Hopper
    private static void powerHopper(R_Device device, Block block){
        if(block.getBlockPower() != 0)
            return;

        for(R_Device d : Objects.requireNonNull(getSurroundingDevicesExcept(block, device))){
            if(d.isReceiver() && d.getSignalPower() > 0)
                return;
        }

        org.bukkit.block.data.type.Hopper hopper = (org.bukkit.block.data.type.Hopper) block.getBlockData();
        hopper.setEnabled((device.getSignalPower() == 0));
        block.setBlockData(hopper);
    }

    //DOOR
    //IRON DOOR
    //FENCE GATE
    //TRAPDOOR
    //IRON TRAPDOOR
    private static void powerOpenable(R_Device device, Block block){
        if(block.getBlockData() instanceof Door) {
            Door door = (Door) block.getBlockData();
            if (door.getHalf().name().equals("TOP")) {
                if (block.getRelative(BlockFace.DOWN).getBlockPower() != 0)
                    return;
            } else if (door.getHalf().name().equals("BOTTOM")) {
                if (block.getRelative(BlockFace.UP).getBlockPower() != 0)
                    return;
            }
        }

        for(R_Device d : Objects.requireNonNull(getSurroundingDevicesExcept(block, device))){
            if(d.isReceiver() && d.getSignalPower() > 0)
                return;
        }

        Openable openable = (Openable) block.getBlockData();
        openable.setOpen((device.getSignalPower() > 0));
        block.setBlockData(openable);
    }

    //Redstone Torch
    //Redstone Lamp
    private static void powerLightable(R_Device device, Block block, int signalPower){
        boolean bool = (signalPower != 0);
        if(block.getType().equals(Material.REDSTONE_WALL_TORCH)) {
            bool = !bool;
            Block base = getTorchBlock(block);
            if (!base.equals(device.getBlock()))
                return;
        }
        Lightable lightable = (Lightable) block.getBlockData();
        lightable.setLit(bool);
        block.setBlockData(lightable);
    }

    //Redstone Sender
    //Redstone Receiver
    private static void powerDevices(R_Device device, Block block){
        R_Device sender = R_Devices.get(block.getLocation());
        if (sender != null && sender.isSender()) {
            if (!sender.isLinkedWith(device))
                sender.updateSignalPower();
        }
    }

}