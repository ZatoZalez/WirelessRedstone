package me.zatozalez.wirelessredstone.Utils;

import me.zatozalez.wirelessredstone.Config.C_Value;
import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import me.zatozalez.wirelessredstone.Redstone.R_Link;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Piston;
import org.bukkit.scheduler.BukkitRunnable;

public class U_Signal {
    public static void emit(R_Device device, int signalPower){
        if(device.isOverloaded())
            return;

        device.setSignalPower(signalPower);
        for(Block block : U_Environment.GetSurroundingBlocks(device.getBlock())){
            BlockData data = block.getBlockData();
            if (data instanceof AnaloguePowerable) {
                powerAnaloguePowerable(block, signalPower);
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
            if(data instanceof Lightable){
                powerLightable(block, signalPower);
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
                for(Block block : U_Environment.GetSurroundingBlocks(device.getBlock())){
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

    private static void powerAnaloguePowerable(Block block, int signalPower){
        AnaloguePowerable analoguePowerable = (AnaloguePowerable) block.getBlockData();
        analoguePowerable.setPower(signalPower);
        block.setBlockData(analoguePowerable);
    }

    private static void powerPowerable(Block block, int signalPower){
        Powerable powerable = (Powerable) block.getBlockData();
        powerable.setPowered((signalPower > 0));
        block.setBlockData(powerable);
    }

    private static void powerPiston(R_Device device, Block block){
        if(!U_Piston.canBePowered(device, block))
            return;

        if(device.getSignalPower() == 0)
            U_Piston.retract(block);
        else
            U_Piston.extend(block);
    }

    private static void powerLightable(Block block, int signalPower){
        Lightable lightable = (Lightable) block.getBlockData();
        lightable.setLit((signalPower > 0));
        block.setBlockData(lightable);
    }

    private static void powerDevices(R_Device device, Block block){
        R_Device sender = R_Devices.get(block.getLocation());
        if (sender != null && sender.isSender()) {
            if (!sender.isLinkedWith(device))
                sender.updateSignalPower();
        }
    }
}