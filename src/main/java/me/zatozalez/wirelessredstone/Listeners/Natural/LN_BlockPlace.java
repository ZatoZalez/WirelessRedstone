package me.zatozalez.wirelessredstone.Listeners.Natural;

import me.zatozalez.wirelessredstone.Config.C_Value;
import me.zatozalez.wirelessredstone.Events.E_DevicePlace;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import me.zatozalez.wirelessredstone.Redstone.R_Manager;
import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Utils.U_Environment;
import me.zatozalez.wirelessredstone.Utils.U_Permissions;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.Powerable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Objects;

public class LN_BlockPlace implements Listener {
    public LN_BlockPlace(WirelessRedstone plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEvent(BlockPlaceEvent e) {
        if(e.getBlock().getType() != R_Manager.RedstoneSender.getType() && e.getBlock().getType() != R_Manager.RedstoneReceiver.getType()) return;
        if(!e.getItemInHand().hasItemMeta()) return;
        if(!Objects.equals(e.getItemInHand().getItemMeta(), R_Manager.RedstoneSender.getItemMeta()) && !Objects.equals(e.getItemInHand().getItemMeta(), R_Manager.RedstoneReceiver.getItemMeta())) return;

        R_Device.DeviceType deviceType = R_Device.DeviceType.RedstoneSender;
        if(Objects.requireNonNull(e.getItemInHand().getItemMeta()).getDisplayName().toLowerCase().contains(R_Device.DeviceType.RedstoneReceiver.toString().toLowerCase()))
            deviceType = R_Device.DeviceType.RedstoneReceiver;

        if(C_Value.getMaxDevicesInServer() > 0)
            if (R_Devices.getList().size() >= C_Value.getMaxDevicesInServer()) {
                e.getPlayer().sendMessage(ChatColor.RED + "The limit of devices on this server has been reached. You can't place anymore devices.");
                e.setCancelled(true);
                return;
            }

        if(U_Permissions.wirelessRedstonePermissionsEnabled())
            if(!U_Permissions.wirelessRedstoneDevicePlace(e.getPlayer())){
                e.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to place devices.");
                e.setCancelled(true);
                return;
            }

        if(C_Value.getMaxDevicesPerPlayer() > 0)
            if(R_Devices.getList(e.getPlayer().getUniqueId()).size() >= C_Value.getMaxDevicesPerPlayer()){
                if(U_Permissions.wirelessRedstonePermissionsEnabled()){
                    if(!U_Permissions.wirelessRedstoneDeviceNoPlaceLimit(e.getPlayer())){
                        e.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to place more than " + ChatColor.DARK_RED + C_Value.getMaxDevicesPerPlayer() + ChatColor.RED + " devices.");
                        e.setCancelled(true);
                        return;
                    }
                }
                else {
                    e.getPlayer().sendMessage(ChatColor.RED + "You can't place more than " + ChatColor.DARK_RED + C_Value.getMaxDevicesPerPlayer() + ChatColor.RED + " devices.");
                    e.setCancelled(true);
                    return;
                }
            }

        Bukkit.getServer().getPluginManager().callEvent(new E_DevicePlace(deviceType, e.getPlayer(), e.getBlock().getLocation(), e));
    }

    @EventHandler
    public void onEvent2(BlockPlaceEvent e){
        Block block = e.getBlock();
        if(!(block.getBlockData() instanceof AnaloguePowerable) && !(block.getBlockData() instanceof Powerable)) return;
        for(Block b : U_Environment.GetSurroundingBlocks(block))
        {
            Location location = b.getLocation();
            R_Device device = R_Devices.get(location);

            if(device == null)
                continue;

            if(device.getDeviceType().equals(R_Device.DeviceType.RedstoneReceiver)) {
                device.updateSignalPower();
                device.emitSignal(device.getSignalPower());
            }
        }
    }
}
