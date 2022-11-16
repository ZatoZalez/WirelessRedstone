package me.zatozalez.wirelessredstone.Listeners.Natural;

import me.zatozalez.wirelessredstone.Config.C_Value;
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
        String recipe = e.getRecipe().getKey().toLowerCase();
        if(recipe.contains("redstone"))
        {
            if(recipe.contains("sender") || recipe.contains("receiver"))
            {
                return;
            }

            if(C_Value.allowRecipes()) {
                if (R_Items.RedstoneReceiver != null && R_Items.RedstoneReceiver.recipe != null)
                    e.getPlayer().discoverRecipe(R_Items.RedstoneReceiver.recipe.getKey());
                if (R_Items.RedstoneSender != null && R_Items.RedstoneSender.recipe != null)
                    e.getPlayer().discoverRecipe(R_Items.RedstoneSender.recipe.getKey());
            }
        }
    }
}
