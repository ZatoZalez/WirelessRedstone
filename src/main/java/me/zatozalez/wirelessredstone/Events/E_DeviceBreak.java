package me.zatozalez.wirelessredstone.Events;

import me.zatozalez.wirelessredstone.Redstone.R_Device;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class E_DeviceBreak extends Event {

    private static final HandlerList handlers = new HandlerList();

    BlockBreakEvent blockBreakEvent;
    EntityExplodeEvent entityExplodeEvent;
    R_Device device;
    Player player;

    public E_DeviceBreak(R_Device device, Player player, BlockBreakEvent blockBreakEvent){
        this.device = device;
        this.player = player;
        this.blockBreakEvent = blockBreakEvent;
    }

    public E_DeviceBreak(R_Device device, EntityExplodeEvent entityExplodeEvent){
        this.device = device;
        this.player = Bukkit.getPlayer(device.getPlayerId());
        this.entityExplodeEvent = entityExplodeEvent;
    }

    public R_Device getDevice() {
        return device;
    }
    public Player getPlayer() {
        return player;
    }
    public BlockBreakEvent getBlockBreakEvent() {
        return blockBreakEvent;
    }
    public EntityExplodeEvent getEntityExplodeEvent() {
        return entityExplodeEvent;
    }

    public HandlerList getHandlers(){
        return handlers;
    }
    public static HandlerList getHandlerList(){
        return handlers;
    }
}
