package me.zatozalez.wirelessredstone.Events;

import me.zatozalez.wirelessredstone.Redstone.R_Device;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class E_DeviceMove extends Event {
    private static final HandlerList handlers = new HandlerList();

    R_Device device;
    Location oldLocation;
    Location newLocation;

    public E_DeviceMove(R_Device device, Location oldLocation, Location newLocation){
        this.device = device;
        this.oldLocation = oldLocation;
        this.newLocation = newLocation;
    }

    public R_Device getDevice() {
        return device;
    }
    public Location getOldLocation() { return oldLocation; }
    public Location getNewLocation() { return newLocation; }

    public HandlerList getHandlers(){
        return handlers;
    }
    public static HandlerList getHandlerList(){
        return handlers;
    }
}
