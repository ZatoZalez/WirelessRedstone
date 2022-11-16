package me.zatozalez.wirelessredstone.Utils;

import me.zatozalez.wirelessredstone.Events.E_DeviceMove;
import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Piston;
import org.bukkit.block.data.type.PistonHead;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class U_Piston {
    public static Material[] staticBlocks =
            {
                    Material.OBSIDIAN,
                    Material.BEDROCK,
                    Material.NOTE_BLOCK,
                    Material.CHEST,
                    Material.FURNACE,
                    Material.DISPENSER,
                    Material.DROPPER,
                    Material.SPAWNER,
                    Material.BEACON
            };
    public static Material[] fragileBlocks =
            {
                    Material.PUMPKIN,
                    Material.COBWEB,
                    Material.DRAGON_EGG,
                    Material.JACK_O_LANTERN,
                    Material.GRASS,
                    Material.TALL_GRASS,
                    Material.LEVER,
                    Material.REPEATER,
                    Material.COMPARATOR,
                    Material.REDSTONE_WIRE,
                    Material.TORCH,
                    Material.REDSTONE_TORCH
            };
    public static boolean extend(Block pistonBlock){
        BlockData pistonData = pistonBlock.getBlockData();
        if (!(pistonData instanceof Directional) || !(pistonData instanceof Piston))
            return false;

        Piston piston = (Piston) pistonBlock.getBlockData();
        if(piston.isExtended())
            return false;

        if(!push(pistonBlock, piston.getFacing()))
            return false;


        Block headBlock = pistonBlock.getRelative(piston.getFacing());
        PistonHead pistonHead = (PistonHead) Material.PISTON_HEAD.createBlockData();
        if(pistonBlock.getType().equals(Material.STICKY_PISTON))
            pistonHead.setType(PistonHead.Type.STICKY);

        headBlock.setBlockData(pistonHead);
        piston.setExtended(true);

        BlockData headData = headBlock.getBlockData();
        if (headData instanceof Directional) {
            ((Directional) headData).setFacing(piston.getFacing());
            headBlock.setBlockData(headData);
        }

        pistonBlock.setBlockData(piston, true);
        for(Player p : Bukkit.getOnlinePlayers())
            p.playSound(pistonBlock.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1f, 1f);
        return true;
    }

    private static boolean push(Block pistonBlock, BlockFace blockFace){
        int maxPush = 12;
        LinkedHashMap<Block, Location> blockList = new LinkedHashMap<>();

        for(int i = 0; i < (maxPush + 1); i++){
            Block block = pistonBlock.getRelative(blockFace);
            if(blockList.size() > 0){
                for(Block b : blockList.keySet()){
                    block = b.getRelative(blockFace);
                }
            }

            if(block.getType().equals(Material.AIR))
                break;

            if(isStaticBlock(block))
                return false;

            if(isFragileBlock(block)){
                blockList.put(block, block.getRelative(blockFace).getLocation());
                break;
            }

            blockList.put(block, block.getRelative(blockFace).getLocation());
            if(i == maxPush)
                return false;
        }

        List<Block> reverseBlockList = new ArrayList<Block>(blockList.keySet());
        Collections.reverse(reverseBlockList);

        int i = 0;
        for (Block block : reverseBlockList) {
            i++;

            if(isFragileBlock(block)){
                block.breakNaturally();
            }
            else {
                blockList.get(block).getBlock().setType(block.getType());
                blockList.get(block).getBlock().setBlockData(block.getBlockData());
            }
            R_Device device = R_Devices.get(block.getLocation());
            if(device != null)
                Bukkit.getServer().getPluginManager().callEvent(new E_DeviceMove(device, block.getLocation(), blockList.get(block)));

            if(i == reverseBlockList.size())
                block.setType(Material.AIR);
        }
        return true;
    }

    public static boolean retract(Block pistonBlock){
        Piston piston = (Piston) pistonBlock.getBlockData();
        if(!piston.isExtended() || pistonBlock.isBlockIndirectlyPowered())
            return false;
        piston.setExtended(false);
        pistonBlock.setBlockData(piston);
        pull(pistonBlock, piston.getFacing());
        for(Player p : Bukkit.getOnlinePlayers())
            p.playSound(pistonBlock.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 1f, 1f);
        return true;
    }

    private static boolean pull(Block pistonBlock, BlockFace blockFace){
        Block pistonHead = pistonBlock.getRelative(blockFace);
        if(pistonBlock.getType().equals(Material.STICKY_PISTON))
            return stickyPull(pistonHead, blockFace);
        pistonHead.setType(Material.AIR);
        return false;
    }

    private static boolean stickyPull(Block pistonHead, BlockFace blockFace){
        Block block = pistonHead.getRelative(blockFace);
        if(isStaticBlock(block) || isFragileBlock(block)){
            pistonHead.setType(Material.AIR);
            return false;
        }
        pistonHead.setType(block.getType());
        pistonHead.setBlockData(block.getBlockData());

        R_Device device = R_Devices.get(block.getLocation());
        if(device != null)
            Bukkit.getServer().getPluginManager().callEvent(new E_DeviceMove(device, block.getLocation(), pistonHead.getLocation()));

        block.setType(Material.AIR);
        return true;
    }


    public static boolean canBePowered(R_Device device, Block pistonBlock){
        if(!(pistonBlock.getBlockData() instanceof Piston))
            return false;
        Piston piston = (Piston)pistonBlock.getBlockData();
        if(pistonBlock.getRelative(piston.getFacing()).equals(device.getBlock()))
            return false;
        return true;
    }

    private static boolean isStaticBlock(Block block){
        for(Material mat : staticBlocks)
            if(mat.equals(block.getType()))
                return true;
        return false;
    }

    private static boolean isFragileBlock(Block block){
        for(Material mat : fragileBlocks)
            if(mat.equals(block.getType()))
                return true;
        return block.isPassable();
    }
}
