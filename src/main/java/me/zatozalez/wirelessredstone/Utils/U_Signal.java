package me.zatozalez.wirelessredstone.Utils;

import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Link;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Piston;
import org.bukkit.scheduler.BukkitRunnable;

public class U_Signal {
    public static void emit(R_Device device, int signalPower){
        device.setSignalPower(signalPower);
        for(Block block : U_Environment.GetSurroundingBlocks(device.getBlock())){
            BlockData data = block.getBlockData();
            if (data instanceof AnaloguePowerable) {
                AnaloguePowerable analoguePowerable = (AnaloguePowerable) block.getBlockData();
                analoguePowerable.setPower(signalPower);
                block.setBlockData(analoguePowerable);
                continue;
            }

            if (data instanceof Powerable) {
                Powerable powerable = (Powerable) block.getBlockData();
                powerable.setPowered((signalPower > 0));
                block.setBlockData(powerable);
                continue;
            }

            if(data instanceof Piston){
                if(device.getSignalPower() == 0){
                    if(U_Piston.canBePowered(device, block)){
                        U_Piston.retract(block);

                    }
                    else Bukkit.broadcastMessage("CANT BE POWERED");                }
                else
                {
                    if(U_Piston.canBePowered(device, block)){
                        Bukkit.broadcastMessage("CAN BE POWERED");
                        U_Piston.extend(block);

                    }
                    else
                        Bukkit.broadcastMessage("CANT BE POWERED");
                }
            }
        }
    }

    public static void send(R_Device device, int signalPower) {
        for (R_Link link : device.getLinks())
            if (link.isLinked()){
                link.getReceiver().emitSignal(signalPower);
            }
    }

    public static void update(R_Device device) {
        new BukkitRunnable() {
            @Override
            public void run() {
                int newCurrent = device.getBlock().getBlockPower();
                for(Block block : U_Environment.GetSurroundingBlocks(device.getBlock())){
                    if(block.getType().equals(Material.REDSTONE_BLOCK))
                        newCurrent = 15;
                }
                if(newCurrent == device.getSignalPower())
                    return;

                device.setSignalPower(newCurrent);
                device.sendSignal();
            }
        }.runTaskLater(WirelessRedstone.getPlugin(), 0);
    }
}