package me.zatozalez.wirelessredstone.Utils;

import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;

import java.util.ArrayList;
import java.util.List;

public class U_Environment {

    public static List<Block> getSurroundingBlocks(Block powerBlock){
        Location loc = powerBlock.getLocation();
        List<Block> blocks = new ArrayList<>();
        blocks.add(powerBlock.getRelative(BlockFace.UP));
        blocks.add(powerBlock.getRelative(BlockFace.DOWN));
        blocks.add(powerBlock.getRelative(BlockFace.NORTH));
        blocks.add(powerBlock.getRelative(BlockFace.SOUTH));
        blocks.add(powerBlock.getRelative(BlockFace.EAST));
        blocks.add(powerBlock.getRelative(BlockFace.WEST));
        return blocks;
    }

    public static boolean isFacing(Block block, Block powerBlock){
        if(powerBlock == null)
            return false;

        List<String> sides = new ArrayList<>();
        BlockFace blockFace = BlockFace.SELF;
        String[] parts = powerBlock.getBlockData().getAsString().split("[\\[,\\]]");
        for(String part : parts) {
            if(!part.contains("="))
                continue;

            String key = part.split("=")[0];
            String value = part.split("=")[1];

            switch(key){
                case "facing":
                    blockFace = BlockFace.valueOf(value.toUpperCase()).getOppositeFace();
                    break;

                case "north":
                case "east":
                case "south":
                case "west":
                    if(value.equalsIgnoreCase("side"))
                        sides.add(key);
                    break;
            }
        }

        if(!blockFace.equals(BlockFace.SELF))
            if(powerBlock.getRelative(blockFace, 1).equals(block))
                return true;

        if(!sides.isEmpty())
            for(String side : sides)
                if(powerBlock.getRelative(BlockFace.valueOf(side.toUpperCase()), 1).equals(block))
                    return true;

        return powerBlock.getRelative(BlockFace.DOWN, 1).equals(block);
    }

    public static Block getTorchBlock(Block block){
        if(block.getBlockData() instanceof Directional) {
            Directional dir = (Directional) block.getBlockData();
            return block.getRelative(dir.getFacing().getOppositeFace());
        }

        return block.getRelative(0, -1, 0);
    }

    public static List<R_Device> getSurroundingDevices(Block block){
        if(block == null)
            return null;

        List<R_Device> devices = new ArrayList<>();
        for(Block b : getSurroundingBlocks(block)){
            R_Device device = R_Devices.get(b.getLocation());
            if(device == null)
                continue;
            devices.add(device);
        }
        return devices;
    }

    public static List<R_Device> getSurroundingDevicesExcept(Block block, R_Device device){
        List<R_Device> devices = getSurroundingDevices(block);
        if(devices == null)
            return null;
        devices.remove(device);
        return devices;
    }
}
