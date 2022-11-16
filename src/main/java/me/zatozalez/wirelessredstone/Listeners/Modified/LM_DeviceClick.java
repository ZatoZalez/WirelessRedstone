package me.zatozalez.wirelessredstone.Listeners.Modified;

import me.zatozalez.wirelessredstone.Config.C_Value;
import me.zatozalez.wirelessredstone.Events.E_DeviceClick;
import me.zatozalez.wirelessredstone.Messages.M_Utility;
import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Link;
import me.zatozalez.wirelessredstone.Redstone.R_Links;
import me.zatozalez.wirelessredstone.Utils.U_Permissions;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
        //spaghetti code
        R_Device device = e.getDevice();
        Player player = e.getPlayer();
        List<String> links = new ArrayList<>(R_Links.getList().keySet());

        if(!C_Value.allowCreateThirdLinks()) {
            if (!device.getPlayerId().equals(player.getUniqueId())) {
                if (U_Permissions.isEnabled()) {
                    if (!U_Permissions.check(player, U_Permissions.Permissions.WIRELESSREDSTONE_LINK_CREATETHIRDLINKS)) {
                        M_Utility.sendMessage(player, M_Utility.getMessage("link_no_permission_create_third_links"));
                        return;
                    }
                } else {
                    M_Utility.sendMessage(player, M_Utility.getMessage("link_create_third_links"));
                    return;
                }
            }
        }

        if(C_Value.getMaxLinksInServer() > 0) {
            if (R_Links.getList().size() >= C_Value.getMaxLinksInServer()) {
                M_Utility.sendMessage(player, M_Utility.getMessage("link_limit"));
                return;
            }
        }

        if(U_Permissions.isEnabled())
            if(!U_Permissions.check(player, U_Permissions.Permissions.WIRELESSREDSTONE_LINK_CREATE)){
                M_Utility.sendMessage(player, M_Utility.getMessage("link_no_permission_create"));
                return;
            }

        if(!C_Value.allowCreateThirdLinks()) {
            if (!device.getPlayerId().equals(player.getUniqueId())) {
                if (U_Permissions.isEnabled()) {
                    if (!U_Permissions.check(player, U_Permissions.Permissions.WIRELESSREDSTONE_LINK_CREATETHIRDLINKS)) {
                        M_Utility.sendMessage(player, M_Utility.getMessage("link_no_permission_create_third_links"));
                        return;
                    }
                } else {
                    M_Utility.sendMessage(player, M_Utility.getMessage("link_create_third_links"));
                    return;
                }
            }
        }


        for(String linkid : links){
            R_Link link = R_Links.get(linkid);

            if(!link.isLinked() && link.getPlayerId().equals(e.getPlayer().getUniqueId())){
                R_Device loneDevice = link.getLoneDevice();

                if(U_Permissions.isEnabled())
                    if(!U_Permissions.check(player, U_Permissions.Permissions.WIRELESSREDSTONE_LINK_CREATE)){
                        M_Utility.sendMessage(player, M_Utility.getMessage("link_no_permission_create"));
                        R_Links.remove(link);
                        return;
                    }

                if(C_Value.getMaxLinksPerPlayer() > 0)
                    if(R_Links.getList(e.getPlayer().getUniqueId()).size() >= C_Value.getMaxLinksPerPlayer()) {
                        if(U_Permissions.isEnabled()) {
                            if (!U_Permissions.check(player, U_Permissions.Permissions.WIRELESSREDSTONE_LINK_NOLIMIT)) {
                                M_Utility.sendMessage(player, M_Utility.getMessage("link_no_permission_max_links", M_Utility.placeHolder("${maxlinksperplayer}", C_Value.getMaxLinksPerPlayer())));
                                R_Links.remove(link);
                                return;
                            }
                        }
                        else {
                            M_Utility.sendMessage(player, M_Utility.getMessage("link_max_links", M_Utility.placeHolder("${maxlinksperplayer}", C_Value.getMaxLinksPerPlayer())));
                            R_Links.remove(link);
                            return;
                        }
                    }

                if(device.equals(loneDevice)){
                    M_Utility.sendMessage(player, M_Utility.getMessage("link_self", M_Utility.placeHolder("${command}", "/device link cancel")));
                    return;
                }

                if(device.getDeviceType().equals(loneDevice.getDeviceType())){
                    M_Utility.sendMessage(player, M_Utility.getMessage("link_same_type", M_Utility.placeHolder("${devicetype1}", device.getDeviceType(), "${devicetype2}", loneDevice.getDeviceType(), "${command}", "/device link cancel")));
                    return;
                }

                if(device.isLinkedWith(loneDevice)){
                    M_Utility.sendMessage(player, M_Utility.getMessage("link_double_link", M_Utility.placeHolder("${command}", "/device link cancel")));
                    return;
                }

                if(!loneDevice.exists()){
                    M_Utility.sendMessage(player, M_Utility.getMessage("link_destroyed"));
                    R_Links.remove(link);
                    return;
                }

                if(C_Value.getMaxLinksPerDevice() > 0){
                    R_Device d = null;
                    if (loneDevice.getLinkCount() >= C_Value.getMaxLinksPerDevice()) {
                        d = loneDevice;
                    } else if (device.getLinkCount() >= C_Value.getMaxLinksPerDevice()) {
                        d = device;
                    }
                    if(d != null) {
                        if (U_Permissions.isEnabled()) {
                            if (!U_Permissions.check(player, U_Permissions.Permissions.WIRELESSREDSTONE_DEVICE_NOLINKLIMIT)) {
                                M_Utility.sendMessage(player, M_Utility.getMessage("device_no_permission_max_links", M_Utility.placeHolder("${maxlinksperdevice}", C_Value.getMaxLinksPerDevice(), "${devicetype}", d.getDeviceType())));
                                R_Links.remove(link);
                                return;
                            }
                        } else {
                            M_Utility.sendMessage(player, M_Utility.getMessage("device_max_links", M_Utility.placeHolder("${maxlinksperdevice}", C_Value.getMaxLinksPerDevice(), "${devicetype}", d.getDeviceType())));
                            R_Links.remove(link);
                            return;
                        }
                    }
                }

                if(C_Value.getMaxLinkDistance() > 0){
                    if(Objects.equals(device.getLocation().getWorld(), loneDevice.getLocation().getWorld()) && device.getLocation().distanceSquared(loneDevice.getLocation()) > C_Value.getMaxLinkDistance()){
                        if(U_Permissions.isEnabled()) {
                            if (!U_Permissions.check(player, U_Permissions.Permissions.WIRELESSREDSTONE_LINK_INFINITEDISTANCE)) {
                                M_Utility.sendMessage(player, M_Utility.getMessage("link_no_permission_max_distance", M_Utility.placeHolder("${maxlinkdistance}", C_Value.getMaxLinkDistance())));
                                R_Links.remove(link);
                                return;
                            }
                        }
                        else {
                            M_Utility.sendMessage(player, M_Utility.getMessage("link_max_distance", M_Utility.placeHolder("${maxlinkdistance}", C_Value.getMaxLinkDistance())));
                            R_Links.remove(link);
                            return;
                        }
                    }
                }

                if(!C_Value.allowCrossWorldSignal()){
                    if(!Objects.equals(device.getLocation().getWorld(), loneDevice.getLocation().getWorld()))
                    {
                        if(U_Permissions.isEnabled()) {
                            if (!U_Permissions.check(player, U_Permissions.Permissions.WIRELESSREDSTONE_LINK_CROSSWORLD)) {
                                M_Utility.sendMessage(player, M_Utility.getMessage("link_no_permission_cross_world"));
                                R_Links.remove(link);
                                return;
                            }
                        }
                        else{
                            M_Utility.sendMessage(player, M_Utility.getMessage("link_cross_world"));
                            R_Links.remove(link);
                            return;
                        }
                    }
                }

                link.create(device, loneDevice);
                M_Utility.sendMessage(player, M_Utility.getMessage("link_created"));
                return;

            }
        }

        R_Link link = new R_Link(device);
        if(e.getPlayer() != null){
            link.setPlayerId(e.getPlayer().getUniqueId());
            M_Utility.sendMessage(player, M_Utility.getMessage("link_create", M_Utility.placeHolder("{devicetype1}", device.getDeviceType(), "{devicetype2}", device.getOppositeDeviceType(), "${command}", "/device link cancel")));
        }

        R_Links.add(link);
        return;
    }
}
