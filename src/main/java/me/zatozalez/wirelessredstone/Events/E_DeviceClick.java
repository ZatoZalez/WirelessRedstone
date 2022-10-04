package me.zatozalez.wirelessredstone.Events;

import me.zatozalez.wirelessredstone.Redstone.R_Device;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerInteractEvent;

public class E_DeviceClick extends Event {
    private static final HandlerList handlers = new HandlerList();

    PlayerInteractEvent playerInteractEvent;
    R_Device device;
    Player player;

    public E_DeviceClick(R_Device device, Player player, PlayerInteractEvent playerInteractEvent){
        this.device = device;
        this.player = player;
        this.playerInteractEvent = playerInteractEvent;
    }

    public R_Device getDevice() {
        return device;
    }
    public Player getPlayer() {
        return player;
    }
    public PlayerInteractEvent getPlayerInteractEvent() {
        return playerInteractEvent;
    }

    public HandlerList getHandlers(){
        return handlers;
    }
    public static HandlerList getHandlerList(){
        return handlers;
    }
}
