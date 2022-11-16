package me.zatozalez.wirelessredstone.Listeners.Natural;

import me.zatozalez.wirelessredstone.Events.E_DeviceClick;
import me.zatozalez.wirelessredstone.Redstone.R_Device;
import me.zatozalez.wirelessredstone.Redstone.R_Devices;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Objects;

public class LN_BlockClick implements Listener {

    public LN_BlockClick(WirelessRedstone plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEvent(PlayerInteractEvent e) {
        Action action = e.getAction();
        Player player = e.getPlayer();

        if(e.isCancelled()){
            return;
        }

        if (!action.equals(Action.RIGHT_CLICK_BLOCK) || player.isSneaking() || e.getHand() == EquipmentSlot.HAND)
            return;

        Location location = Objects.requireNonNull(e.getClickedBlock()).getLocation();
        R_Device device = R_Devices.get(location);

        if(device == null)
            return;

        Bukkit.getServer().getPluginManager().callEvent(new E_DeviceClick(device, e.getPlayer(), e));
    }
}
