package me.zatozalez.wirelessredstone.Redstone;

import me.zatozalez.wirelessredstone.Config.C_Value;
import me.zatozalez.wirelessredstone.Messages.M_Utility;
import me.zatozalez.wirelessredstone.Utils.U_Log;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedHashMap;
import java.util.UUID;

public class R_Link {

    private final String id;
    private UUID playerid;
    private R_Device sender;
    private R_Device receiver;
    private int overload = 0;
    private boolean isValid = false;

    public void onSignal(){
        if(!C_Value.overloadEnabled() || getSender().isOverloaded() || getReceiver().isOverloaded())
            return;

        overload++;
        if(overload >= C_Value.getOverloadTrigger()){
            getSender().overload();
            overload = 0;

            Player player = Bukkit.getPlayer(getSender().getPlayerId());
            if(player != null && C_Value.allowMessages()) {
                M_Utility.sendMessage(player, M_Utility.getMessage("device_overload"));
                if (!getSender().getPlayerId().equals(getReceiver().getPlayerId())) {
                    player = Bukkit.getPlayer(getReceiver().getPlayerId());
                    if (player != null)
                        M_Utility.sendMessage(player, M_Utility.getMessage("device_overload"));
                }
            }
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                overload = 0;
            }
        }.runTaskLater(WirelessRedstone.getPlugin(), 20);
    }

    public R_Link(R_Device device){
        this.id = UUID.randomUUID().toString();
        if(device != null)
            if(device.isSender())
                this.sender = device;
            else
                this.receiver = device;
    }

    public R_Link(String id, R_Device sender, R_Device receiver){
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        isValid = true;
    }

    public String getId() {
        return id;
    }

    public UUID getPlayerId() {
        return playerid;
    }

    public R_Device getSender(){
        return sender;
    }

    public R_Device getReceiver(){
        return receiver;
    }

    public R_Device getLoneDevice(){
        if(isLinked())
            return null;

        if(sender == null)
            return receiver;
        return sender;
    }

    public String getInline() {
        LinkedHashMap<String, String> inlineMap = new LinkedHashMap<>();
        if(playerid != null)
            inlineMap.put("playerid", playerid.toString());
        inlineMap.put("sender", sender.getId());
        inlineMap.put("receiver", receiver.getId());

        String inline = "";
        for (String key : inlineMap.keySet()) {
            if(!inline.equals(""))
                inline += "\n";
            inline += key + ":" + inlineMap.get(key);
        }
        return inline;
    }

    public void setPlayerId(UUID playerid){
        if(playerid != null)
            this.playerid = playerid;
    }

    public void setPlayerId(String playerid){
        if(playerid != null)
            this.playerid = UUID.fromString(playerid);
    }

    public void create(R_Device device1, R_Device device2){
        if(device1 == null || device2 == null || device1.getDeviceType().equals(device2.getDeviceType()))
            return;

        if(device1.getDeviceType().equals(DeviceType.RedstoneSender)){
            sender = device1;
            receiver = device2;
        }
        else {
            sender = device2;
            receiver = device1;
        }

        sender.addLink(getId());
        receiver.addLink(getId());

        if(sender.getSignalPower() > 0){
            receiver.emitSignal(sender.getSignalPower());
        }

        isValid = true;
    }

    public void destroy(){
        isValid = false;

        if(receiver != null){
            receiver.emitSignal(0);
            receiver.removeLink(getId());
        }
        if(sender != null){
            sender.removeLink(getId());
        }
        R_Links.remove(this);
    }

    public boolean isLinked(){
        return sender != null && receiver != null;
    }

    public boolean isValid() {
        return isValid;
    }

    public boolean exists(){
        if(sender == null || receiver == null)
            return false;
        return sender.exists() && receiver.exists();
    }
}
