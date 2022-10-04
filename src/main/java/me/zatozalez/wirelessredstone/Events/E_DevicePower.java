package me.zatozalez.wirelessredstone.Events;

import me.zatozalez.wirelessredstone.Redstone.R_Device;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class E_DevicePower extends Event {
    private static final HandlerList handlers = new HandlerList();

    R_Device device;

    public E_DevicePower(R_Device device){
        this.device = device;
    }

    public R_Device getDevice() {
        return device;
    }

    public HandlerList getHandlers(){
        return handlers;
    }
    public static HandlerList getHandlerList(){
        return handlers;
    }
}
