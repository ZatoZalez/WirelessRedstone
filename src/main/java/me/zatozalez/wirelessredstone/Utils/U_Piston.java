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
    public static Material[] unmovableBlocks = { Material.OBSIDIAN, Material.BEDROCK, Material.NOTE_BLOCK, Material.CHEST, Material.FURNACE, Material.DISPENSER, Material.DROPPER, Material.SPAWNER};
    public static Material[] breakingBlocks = { Material.PUMPKIN, Material.COBWEB, Material.DRAGON_EGG, Material.JACK_O_LANTERN };
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

            blockList.put(block, block.getRelative(blockFace).getLocation());
            if(i == maxPush)
                return false;
        }

        List<Block> reverseBlockList = new ArrayList<Block>(blockList.keySet());
        Collections.reverse(reverseBlockList);

        int i = 0;
        for (Block block : reverseBlockList) {
            i++;
            blockList.get(block).getBlock().setType(block.getType());
            blockList.get(block).getBlock().setBlockData(block.getBlockData());
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
        {
            Block block = pistonHead.getRelative(blockFace);
            pistonHead.setType(block.getType());
            pistonHead.setBlockData(block.getBlockData());

            R_Device device = R_Devices.get(block.getLocation());
            if(device != null)
                Bukkit.getServer().getPluginManager().callEvent(new E_DeviceMove(device, block.getLocation(), pistonHead.getLocation()));

            block.setType(Material.AIR);
            return true;
        }
        else
            pistonHead.setType(Material.AIR);
        return false;
    }

    public static boolean canBePowered(R_Device device, Block pistonBlock){
        if(!(pistonBlock.getBlockData() instanceof Piston))
            return false;
        Piston piston = (Piston)pistonBlock.getBlockData();
        if(pistonBlock.getRelative(piston.getFacing()).equals(device.getBlock()))
            return false;
        return true;
    }
}
