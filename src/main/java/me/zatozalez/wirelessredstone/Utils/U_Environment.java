package me.zatozalez.wirelessredstone.Utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.List;

public class U_Environment {

    public static List<Block> GetSurroundingBlocks(Block powerBlock){
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

    public static boolean IsFacing(Block block, Block powerBlock){
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
}
