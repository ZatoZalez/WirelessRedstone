package me.zatozalez.wirelessredstone.Listeners.Natural;

import me.zatozalez.wirelessredstone.Redstone.R_Items;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRecipeDiscoverEvent;

public class LN_RecipeUnlock implements Listener {
    public LN_RecipeUnlock(WirelessRedstone plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEvent(PlayerRecipeDiscoverEvent e) {
        if(e.getRecipe().getKey().toLowerCase().contains("redstone"))
        {
            e.getPlayer().discoverRecipe(R_Items.RedstoneReceiver.recipe.getKey());
            e.getPlayer().discoverRecipe(R_Items.RedstoneSender.recipe.getKey());
        }
    }
}
