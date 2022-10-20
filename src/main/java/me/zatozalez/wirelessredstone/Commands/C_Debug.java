package me.zatozalez.wirelessredstone.Commands;

import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import me.zatozalez.wirelessredstone.Redstone.R_Manager;
import me.zatozalez.wirelessredstone.Utils.U_Environment;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Piston;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.Method;

public class C_Debug implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String [] args) {
        if (!(sender instanceof Player))
            return false;


        //Command variables
        Player player = ((Player) sender).getPlayer();
        Block block = player.getTargetBlockExact(10);

        R_Device device = R_Devices.get(block.getLocation());
        if(device!=null)
            Bukkit.broadcastMessage("power: " + device.getSignalPower());


        if(true)
            return true;
        PlayerInventory inv = player.getInventory();
        ItemStack item = null;

        item = R_Manager.RedstoneSender;
        item.setAmount(1);

        inv.addItem(item);
        item = R_Manager.RedstoneReceiver;
        inv.addItem(item);
        return true;
    }

    public static boolean movePiston(Block piston) throws Exception {
        BlockData bd =  piston.getBlockData();
        Piston redstone = (Piston) bd;
        boolean extended = redstone.isExtended();

        BlockFace bf = ((Directional) bd).getFacing();

        Object nmsWorld = (getNmsClass("CraftWorld", true).cast(piston.getWorld())).getClass().getMethod("getHandle").invoke((getNmsClass("CraftWorld", true).cast(piston.getWorld())));
        Object pistonPosition = getNmsClass("Location", false).getConstructor(int.class, int.class, int.class).newInstance(piston.getX(), piston.getY(), piston.getZ());
        Object nmsBlockData = nmsWorld.getClass().getMethod("getType", pistonPosition.getClass()).invoke(nmsWorld, pistonPosition);
        Object nmsPiston = getNmsClass("BlockPiston", false).cast(nmsBlockData.getClass().getMethod("getBlock").invoke(nmsBlockData));

        try {
            Method method = nmsPiston.getClass().getDeclaredMethod("a", getNmsClass("World", false) , getNmsClass("Location", false) , getNmsClass("EnumDirection", false) , boolean.class);
            method.setAccessible(true);
            if(extended == true) {
                redstone.setExtended(false);
                piston.setBlockData(redstone);
            }
            redstone.setExtended(true);
            piston.setBlockData(redstone);
            boolean bold = (boolean) method.invoke(nmsPiston, nmsWorld, pistonPosition, interchangeDirections(bf),  !extended);
            if(!bold) {
                redstone.setExtended(false);
                piston.setBlockData(redstone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return extended;
    }

    public static Object interchangeDirections(BlockFace bf) throws ClassNotFoundException, NoSuchFieldException, SecurityException, NoSuchMethodException, InstantiationException, IllegalAccessException{
        Class enumDirection = (Class) getNmsClass("EnumDirection", false);
        if(bf == BlockFace.DOWN) {
            return Enum.valueOf(enumDirection, "DOWN");
        }
        else if(bf == BlockFace.UP) {
            return Enum.valueOf(enumDirection, "UP");
        }
        else if(bf == BlockFace.EAST) {
            return Enum.valueOf(enumDirection, "EAST");
        }
        else if(bf == BlockFace.NORTH) {
            return Enum.valueOf(enumDirection, "NORTH");
        }
        else if(bf == BlockFace.SOUTH) {
            return Enum.valueOf(enumDirection, "SOUTH");
        }
        else if(bf == BlockFace.WEST) {
            return Enum.valueOf(enumDirection, "WEST");
        }
        return null;
    }

    public static Class<?> getNmsClass(String nmsClassName, boolean craftbukkit) throws ClassNotFoundException {
        String start = "net.minecraft.server.";
        if(craftbukkit) {
            start = "org.bukkit.craftbukkit.";
        }

        return Class.forName(start
                + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "."
                + nmsClassName);
    }
}

