package me.zatozalez.wirelessredstone.Listeners.Natural;

import me.zatozalez.wirelessredstone.Redstone.R_Items;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LN_PlayerJoin implements Listener {
    public LN_PlayerJoin(WirelessRedstone plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEvent(PlayerJoinEvent e) {
        for (int i = 0; i < e.getPlayer().getInventory().getSize() - 1; ++i) {
            ItemStack item = e.getPlayer().getInventory().getItem(i);
            if (item == null)
                continue;

            if (R_Items.isRedstoneDevice(item)) {
                if (R_Items.isRedstoneSender(item)) {
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(R_Items.RedstoneSender.displayname);
                    meta.setLore(R_Items.RedstoneSender.lore);
                    item.setItemMeta(meta);
                } else {
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(R_Items.RedstoneReceiver.displayname);
                    meta.setLore(R_Items.RedstoneReceiver.lore);
                    item.setItemMeta(meta);
                }
            }
        }
    }
}