package me.zatozalez.wirelessredstone.Redstone;

import me.zatozalez.wirelessredstone.Utils.U_Environment;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Piston;
import org.bukkit.material.Directional;
import org.bukkit.material.Lever;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class R_Device {

    private final String id;
    private UUID playerid;
    private Location location;
    private final DeviceType deviceType;
    private final List<String> links = new ArrayList<>();

    private int signalPower;

    public enum DeviceType {
        RedstoneSender,
        RedstoneReceiver
    }

    public R_Device(DeviceType deviceType, Block block){
        this.id = UUID.randomUUID().toString();
        this.deviceType = deviceType;
        this.location = block.getLocation();
    }

    public R_Device(String id, DeviceType deviceType, Location location){
        this.id = id;
        this.deviceType = deviceType;
        this.location = location;
    }

    public String getId() {
        return id;
    }
    public UUID getPlayerId() {
        return playerid;
    }
    public DeviceType getDeviceType() {
        return deviceType;
    }
    public int getSignalPower() { return signalPower; }
    public DeviceType getOppositeDeviceType() {
        if(deviceType.equals(DeviceType.RedstoneSender))
            return DeviceType.RedstoneReceiver;
        else
            return DeviceType.RedstoneSender;
    }
    public List<R_Link> getLinks(){
        List<R_Link> list = new ArrayList<>();
        for(String linkid : links)
            list.add(R_Links.get(linkid));
        return list;
    }
    public Location getLocation() {
        return location;
    }
    public World getWorld(){
        if(getLocation() == null)
            return null;
        return location.getWorld();
    }
    public Block getBlock(){
        if(getWorld() == null || getLocation() == null)
            return null;
        return getWorld().getBlockAt(getLocation());
    }
    public int getLinkCount(){
        return links.size();
    }
    public String getInline() {
        LinkedHashMap<String, String> inlineMap = new LinkedHashMap<>();
        if(playerid != null)
            inlineMap.put("playerid", playerid.toString());
        inlineMap.put("devicetype", deviceType.toString());

        if(location != null) {
            inlineMap.put("world", Objects.requireNonNull(location.getWorld()).getUID().toString());
            inlineMap.put("location", location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ());
        }

        if (links.size() > 0)
            inlineMap.put("links", String.join(",", links));

        String inline = "";
        for (String key : inlineMap.keySet()) {
            if(!inline.equals(""))
                inline += "\n";
            inline += key + ":" + inlineMap.get(key);
        }
        return inline;
    }

    public boolean isLinked(){
        return links.size() > 0;
    }
    public boolean isLinkedWith(R_Device device){
        if(!isLinked() || !device.isLinked())
            return false;

        for(R_Link l : getLinks())
            if(l.getReceiver().equals(device) || l.getSender().equals(device))
                return true;
        return false;
    }

    public void sendSignal(){
        sendSignal(signalPower);
    }
    public void sendSignal(int signalPower) {
        if (!isLinked() || getDeviceType().equals(DeviceType.RedstoneReceiver))
            return;

        for (R_Link link : getLinks())
            if (link.isLinked()) {
                link.getReceiver().emitSignal(signalPower);
            }
    }
    public void emitSignal(int signalPower){
        if (!isLinked() || getDeviceType().equals(DeviceType.RedstoneSender))
            return;

        setSignalPower(signalPower);
        for(Block block : U_Environment.GetSurroundingBlocks(getBlock())){
            BlockData data = block.getBlockData();
            if (data instanceof AnaloguePowerable) {
                AnaloguePowerable analoguePowerable = (AnaloguePowerable) block.getBlockData();
                analoguePowerable.setPower(signalPower);
                block.setBlockData(analoguePowerable);
                continue;
            }

            if (data instanceof Powerable) {
                Powerable powerable = (Powerable) block.getBlockData();
                powerable.setPowered((signalPower > 0));
                block.setBlockData(powerable);
            }
        }
    }

    public void destroyLinks(){
        for(R_Link l : getLinks())
            l.destroy();
    }
    public void addLink(String link){
        links.add(link);
    }
    public void removeLink(String link){
        links.remove(link);
    }

    public void setPlayerId(UUID playerid){
        if(playerid != null)
            this.playerid = playerid;
    }
    public void setPlayerId(String playerid){
        if(playerid != null)
            this.playerid = UUID.fromString(playerid);
    }
    public void setLocation(Location location){
        this.location = location;
    }
    public void setSignalPower(int signalPower) { this.signalPower = signalPower; }
    public void updateSignalPower() {
        new BukkitRunnable() {
            @Override
            public void run() {
                int newCurrent = getBlock().getBlockPower();
                setSignalPower(newCurrent);
                sendSignal();
            }
        }.runTaskLater(WirelessRedstone.getPlugin(), 0);
    }

    public boolean exists(){
        if(location == null)
            return false;

        Block block = location.getBlock();
        if(deviceType.equals(DeviceType.RedstoneSender))
            if(!block.getType().equals(R_Manager.RedstoneSenderMaterial))
                return false;
        if(deviceType.equals(DeviceType.RedstoneReceiver))
            return block.getType().equals(R_Manager.RedstoneReceiverMaterial);
        return true;
    }
}
