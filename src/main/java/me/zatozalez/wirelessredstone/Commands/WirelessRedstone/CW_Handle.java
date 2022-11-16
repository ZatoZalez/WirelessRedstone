package me.zatozalez.wirelessredstone.Commands.WirelessRedstone;

import me.zatozalez.wirelessredstone.Redstone.R_Items;
import me.zatozalez.wirelessredstone.Utils.U_Permissions;
import me.zatozalez.wirelessredstone.Versions.V_Manager;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static me.zatozalez.wirelessredstone.Commands.Device.CD_Push.pushInvalidPermission;
import static me.zatozalez.wirelessredstone.Commands.Device.CD_Validity.hasValidArguments;
import static me.zatozalez.wirelessredstone.Commands.WirelessRedstone.CW_Push.*;

public class CW_Handle {

    public static boolean handleInfo(Player player, String[] args){
        if(!hasValidArguments(player, args, 1, new String[] { "/wirelessredstone info" }))
            return true;

        String commands =
                "\n- /wirelessredstone info" +
                        "\n- /wirelessredstone reload" +
                        "\n- /wirelessredstone disable" +
                        "\n- /device info" +
                        "\n- /device give" +
                        "\n- /device link";

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Wireless Redstone &f" + V_Manager.pluginVersion + " &7by ZatoZalez." +
                "\n&7Send wireless redstone signals through worlds and air.&r" +
                commands
        ));
        return true;
    }

    public static boolean handleReload(Player player, String[] args){
        if(U_Permissions.isEnabled())
            if(!U_Permissions.check(player, U_Permissions.Permissions.WIRELESSREDSTONE_COMMANDS_WIRELESSREDSTONE_RELOAD))
                return pushInvalidPermission(player);

        if(!hasValidArguments(player, args, 1, new String[] { "/wirelessredstone reload" }))
            return true;

        pushReloading(player);
        WirelessRedstone.getPlugin().reloadPlugin();

        for(Player p : Bukkit.getOnlinePlayers()) {
            if (p == null)
                continue;

            for (int i = 0; i < p.getInventory().getSize() - 1; ++i) {
                ItemStack item = p.getInventory().getItem(i);
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
        return pushReloaded(player);
    }

    public static boolean handleDisable(Player player, String[] args){
        if(U_Permissions.isEnabled())
            if(!U_Permissions.check(player, U_Permissions.Permissions.WIRELESSREDSTONE_COMMANDS_WIRELESSREDSTONE_DISABLE))
                return pushInvalidPermission(player);

        if(!hasValidArguments(player, args, 1, new String[] { "/wirelessredstone disable" }))
            return true;

        pushDisabling(player);
        Bukkit.getPluginManager().disablePlugin(WirelessRedstone.getPlugin());
        return true;
    }
}
