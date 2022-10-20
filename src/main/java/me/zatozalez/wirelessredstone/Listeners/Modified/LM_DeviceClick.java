package me.zatozalez.wirelessredstone.Listeners.Modified;

import me.zatozalez.wirelessredstone.Config.C_Value;
import me.zatozalez.wirelessredstone.Events.E_DeviceClick;
import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Link;
import me.zatozalez.wirelessredstone.Redstone.R_Links;
import me.zatozalez.wirelessredstone.Utils.U_Permissions;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LM_DeviceClick implements Listener {
    public LM_DeviceClick(WirelessRedstone plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEvent(E_DeviceClick e) {
        R_Device device = e.getDevice();
        List<String> links = new ArrayList<>(R_Links.getList().keySet());

        if(C_Value.getMaxLinksInServer() > 0) {
            if (R_Links.getList().size() >= C_Value.getMaxLinksInServer()) {
                e.getPlayer().sendMessage(ChatColor.RED + "The limit of links on this server has been reached. You can't link anymore devices.");
                return;
            }
        }


        for(String linkid : links){
            R_Link link = R_Links.get(linkid);

            if(!link.isLinked() && link.getPlayerId().equals(e.getPlayer().getUniqueId())){
                R_Device loneDevice = link.getLoneDevice();

                if(U_Permissions.wirelessRedstonePermissionsEnabled())
                    if(!U_Permissions.wirelessRedstoneLinkCreate(e.getPlayer())){
                        e.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to create links.");
                        R_Links.remove(link);
                        return;
                    }

                if(C_Value.getMaxLinksPerPlayer() > 0)
                    if(R_Links.getList(e.getPlayer().getUniqueId()).size() >= C_Value.getMaxLinksPerPlayer()) {
                        if(U_Permissions.wirelessRedstonePermissionsEnabled()) {
                            if (!U_Permissions.wirelessRedstoneLinkNoLimit(e.getPlayer())) {
                                e.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to create more than " + ChatColor.DARK_RED + C_Value.getMaxLinksPerPlayer() + ChatColor.RED + " links.");
                                R_Links.remove(link);
                                return;
                            }
                        }
                        else {
                            e.getPlayer().sendMessage(ChatColor.RED + "You can't create more than " + ChatColor.DARK_RED + C_Value.getMaxLinksPerPlayer() + ChatColor.RED + " links.");
                            R_Links.remove(link);
                            return;
                        }
                    }

                if(device.equals(loneDevice)){
                    e.getPlayer().sendMessage(ChatColor.RED + "You can't link a device to itself." + ChatColor.RED + "\nUse " + ChatColor.DARK_RED + "/cancellink" + ChatColor.RED + " to cancel this link or try again.");
                    return;
                }

                if(device.getDeviceType().equals(loneDevice.getDeviceType())){
                    e.getPlayer().sendMessage(ChatColor.RED + "You can't link a " + ChatColor.DARK_RED + device.getDeviceType() + ChatColor.RED + " to a " + ChatColor.DARK_RED + loneDevice.getDeviceType() + ChatColor.RED + "."+ ChatColor.RED + "\nUse " + ChatColor.DARK_RED + "/cancellink" + ChatColor.RED + " to cancel this link or try again.");
                    return;
                }

                if(device.isLinkedWith(loneDevice)){
                    e.getPlayer().sendMessage(ChatColor.RED + "You already linked these two devices to each other." + ChatColor.RED + "\nUse " + ChatColor.DARK_RED + "/cancellink" + ChatColor.RED + " to cancel this link or try again.");
                    return;
                }

                if(!loneDevice.exists()){
                    e.getPlayer().sendMessage(ChatColor.RED + "You can't link these two devices because one of them was destroyed.");
                    R_Links.remove(link);
                    return;
                }

                if(C_Value.getMaxLinksPerDevice() > 0) {
                    R_Device d = null;
                    if (loneDevice.getLinkCount() >= C_Value.getMaxLinksPerDevice()) {
                        d = loneDevice;
                    } else if (device.getLinkCount() >= C_Value.getMaxLinksPerDevice()) {
                        d = device;
                    }
                    if(d != null){
                        e.getPlayer().sendMessage(ChatColor.RED + "You can't have more than " + ChatColor.DARK_RED + C_Value.getMaxLinksPerDevice() + ChatColor.RED + " links for this " + ChatColor.DARK_RED + d.getDeviceType() + ChatColor.RED + ".");
                        R_Links.remove(link);
                        return;
                    }
                }

                if(C_Value.getMaxLinksPerDevice() > 0){
                    R_Device d = null;
                    if (loneDevice.getLinkCount() >= C_Value.getMaxLinksPerDevice()) {
                        d = loneDevice;
                    } else if (device.getLinkCount() >= C_Value.getMaxLinksPerDevice()) {
                        d = device;
                    }
                    if(d != null) {
                        if (U_Permissions.wirelessRedstonePermissionsEnabled()) {
                            if (!U_Permissions.wirelessRedstoneDeviceNoLinkLimit(e.getPlayer())) {
                                e.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to have more than " + ChatColor.DARK_RED + C_Value.getMaxLinksPerDevice() + ChatColor.RED + " links for this " + ChatColor.DARK_RED + d.getDeviceType() + ChatColor.RED + ".");
                                R_Links.remove(link);
                                return;
                            }
                        } else {
                            e.getPlayer().sendMessage(ChatColor.RED + "You can't have more than " + ChatColor.DARK_RED + C_Value.getMaxLinksPerDevice() + ChatColor.RED + " links for this " + ChatColor.DARK_RED + d.getDeviceType() + ChatColor.RED + ".");
                            R_Links.remove(link);
                            return;
                        }
                    }
                }

                if(C_Value.getMaxLinkDistance() > 0){
                    if(Objects.equals(device.getLocation().getWorld(), loneDevice.getLocation().getWorld()) && device.getLocation().distanceSquared(loneDevice.getLocation()) > C_Value.getMaxLinkDistance()){
                        if(U_Permissions.wirelessRedstonePermissionsEnabled()) {
                            if (!U_Permissions.wirelessRedstoneLinkInfiniteDistance(e.getPlayer())) {
                                e.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to create a link over a distance of " + ChatColor.DARK_RED + C_Value.getMaxLinkDistance() + ChatColor.RED + " blocks.");
                                R_Links.remove(link);
                                return;
                            }
                        }
                        else {
                            e.getPlayer().sendMessage(ChatColor.RED + "You can't create a link over a distance of " + ChatColor.DARK_RED + C_Value.getMaxLinkDistance() + ChatColor.RED + " blocks.");
                            R_Links.remove(link);
                            return;
                        }
                    }
                }

                if(!C_Value.allowCrossWorldSignal()){
                    if(!Objects.equals(device.getLocation().getWorld(), loneDevice.getLocation().getWorld()))
                    {
                        if(U_Permissions.wirelessRedstonePermissionsEnabled()) {
                            if (!U_Permissions.wirelessRedstoneLinkCrossWorld(e.getPlayer())) {
                                e.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to create a link between worlds.");
                                R_Links.remove(link);
                                return;
                            }
                        }
                        else{
                            e.getPlayer().sendMessage(ChatColor.RED + "You can't create a link between worlds.");
                            R_Links.remove(link);
                            return;
                        }
                    }
                }

                link.create(device, loneDevice);
                e.getPlayer().sendMessage(ChatColor.GREEN + "You have successfully linked two devices!");
                return;

            }
        }

        R_Link link = new R_Link(device);
        if(e.getPlayer() != null){
            link.setPlayerId(e.getPlayer().getUniqueId());
            e.getPlayer().sendMessage(ChatColor.GOLD + "Click on a " + ChatColor.GREEN + device.getOppositeDeviceType().toString() + ChatColor.GOLD + " to create a link with this " + ChatColor.GOLD + device.getDeviceType().toString() + ChatColor.GOLD + " or use " + ChatColor.GREEN + "/cancellink" + ChatColor.GOLD + " to cancel this link.");
        }

        R_Links.add(link);
        return;
    }
}
