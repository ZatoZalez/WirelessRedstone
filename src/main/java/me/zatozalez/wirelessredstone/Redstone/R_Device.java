package me.zatozalez.wirelessredstone.Redstone;

import me.zatozalez.wirelessredstone.Config.C_Value;
import me.zatozalez.wirelessredstone.Utils.U_Signal;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class R_Device {

    private final String id;
    private UUID playerid;
    private Location location;
    private final DeviceType deviceType;
    private final List<String> links = new ArrayList<>();
    private boolean isOverloaded;
    private boolean updating;
    private int signalPower;

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

    public boolean isOverloaded() { return isOverloaded; }

    public boolean isUpdating(){
        return updating;
    }

    public boolean isSender() { return (DeviceType.RedstoneSender.equals(deviceType)); }

    public boolean isReceiver() { return (DeviceType.RedstoneReceiver.equals(deviceType)); }

    public void sendSignal(){
        sendSignal(signalPower);
    }

    public void sendSignal(int signalPower) {
        if (!isLinked() || isReceiver())
            return;

        U_Signal.send(this, signalPower);
    }

    public void emitSignal(int signalPower){
        if (!isLinked() || isSender())
            return;

        for(R_Link link : getLinks()){
            if(link.isValid())
                if(signalPower < link.getSender().getSignalPower())
                    return;
        }
        U_Signal.emit(this, signalPower);
    }

    public void overload(){
        if(isOverloaded)
            return;

        isOverloaded = true;
        if(isSender())
            for(R_Link link : getLinks())
                link.getReceiver().overload();

        for(Player p : Bukkit.getOnlinePlayers())
            p.playSound(getLocation(), Sound.BLOCK_REDSTONE_TORCH_BURNOUT, 0.5f, 1f);

        spawnOverloadParticle(Color.fromRGB(10, 10, 10));
        spawnOverloadParticle(Color.fromRGB(50 , 50, 50));
        spawnOverloadParticle(Color.fromRGB(100, 100, 100));
        new BukkitRunnable() {
            @Override
            public void run() {
                isOverloaded = false;
            }
        }.runTaskLater(WirelessRedstone.getPlugin(), C_Value.getOverloadCooldown() * 20);
    }

    private void spawnOverloadParticle(Color color){
        Particle particle = Particle.REDSTONE;
        Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1.0F);

        Random rd = new Random();
        for(int i = 0; i < 2; i++) {
            getWorld().spawnParticle(particle, new Location(getWorld(), getLocation().getBlockX() + rd.nextFloat(), getLocation().getBlockY() + 1.1, getLocation().getBlockZ() + rd.nextFloat()), 1, dustOptions);
            getWorld().spawnParticle(particle, new Location(getWorld(), getLocation().getBlockX() + rd.nextFloat(), getLocation().getBlockY() + 1.1, getLocation().getBlockZ() + rd.nextFloat()), 1, dustOptions);
            getWorld().spawnParticle(particle, new Location(getWorld(), getLocation().getBlockX() + rd.nextFloat(), getLocation().getBlockY() + 1.1, getLocation().getBlockZ() + rd.nextFloat()), 1, dustOptions);
            getWorld().spawnParticle(particle, new Location(getWorld(), getLocation().getBlockX() + rd.nextFloat(), getLocation().getBlockY() + rd.nextFloat(), getLocation().getBlockZ() + 0), 1, dustOptions);
            getWorld().spawnParticle(particle, new Location(getWorld(), getLocation().getBlockX() + 0, getLocation().getBlockY() + rd.nextFloat(), getLocation().getBlockZ() + 0), 1, dustOptions);
            getWorld().spawnParticle(particle, new Location(getWorld(), getLocation().getBlockX() + 0, getLocation().getBlockY() + rd.nextFloat(), getLocation().getBlockZ() + rd.nextFloat()), 1, dustOptions);
            getWorld().spawnParticle(particle, new Location(getWorld(), getLocation().getBlockX() + rd.nextFloat(), getLocation().getBlockY() + rd.nextFloat(), getLocation().getBlockZ() + rd.nextFloat()), 1, dustOptions);
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

    public void setUpdating(boolean updating){
        this.updating = updating;
    }

    public void updateSignalPower() {
        U_Signal.update(this);
    }

    public boolean exists(){
        if(location == null)
            return false;
        Block block = location.getBlock();
        if(isSender())
            if(!block.getType().equals(R_Items.RedstoneSender.material))
                return false;
        if(isReceiver())
            if(!block.getType().equals(R_Items.RedstoneReceiver.material))
                return false;
        return true;
    }
}
