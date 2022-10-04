package me.zatozalez.wirelessredstone.Redstone;

import java.util.LinkedHashMap;
import java.util.UUID;

public class R_Link {

    private final String id;
    private UUID playerid;
    private R_Device sender;
    private R_Device receiver;

    public R_Link(R_Device device){
        this.id = UUID.randomUUID().toString();
        if(device != null)
            if(device.getDeviceType().equals(R_Device.DeviceType.RedstoneSender))
                this.sender = device;
            else
                this.receiver = device;
    }

    public R_Link(String id, R_Device sender, R_Device receiver){
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
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

        if(device1.getDeviceType().equals(R_Device.DeviceType.RedstoneSender)){
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
    }
    public void destroy(){
        if(!isLinked())
            return;

        receiver.emitSignal(0);
        sender.removeLink(getId());
        receiver.removeLink(getId());
        R_Links.remove(this);
    }

    public boolean isLinked(){
        return sender != null && receiver != null;
    }
    public boolean exists(){
        if(sender == null || receiver == null)
            return false;
        return sender.exists() && receiver.exists();
    }
}
