package me.zatozalez.wirelessredstone.Events;

import me.zatozalez.wirelessredstone.Redstone.R_Device;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockPlaceEvent;

public class E_DevicePlace extends Event {

    private static final HandlerList handlers = new HandlerList();

    BlockPlaceEvent blockPlaceEvent;
    R_Device.DeviceType deviceType;
    Player player;
    Location location;

    public E_DevicePlace(R_Device.DeviceType deviceType, Player player, Location location, BlockPlaceEvent blockPlaceEvent){
        this.deviceType = deviceType;
        this.player = player;
        this.location = location;
        this.blockPlaceEvent = blockPlaceEvent;
    }

    public R_Device.DeviceType getDeviceType() {
        return deviceType;
    }
    public Player getPlayer() {
        return player;
    }
    public Location getLocation() {
        return location;
    }
    public BlockPlaceEvent getBlockPlaceEvent() {
        return blockPlaceEvent;
    }

    public HandlerList getHandlers(){
        return handlers;
    }
    public static HandlerList getHandlerList(){
        return handlers;
    }
}
