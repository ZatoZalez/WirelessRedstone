package me.zatozalez.wirelessredstone.Commands;

import me.zatozalez.wirelessredstone.Redstone.R_Link;
import me.zatozalez.wirelessredstone.Redstone.R_Links;
import me.zatozalez.wirelessredstone.Utils.U_Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class C_CancelLink implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String [] args) {
        if (!(sender instanceof Player))
            return false;

        //Command variables
        R_Link link = null;
        Player player = ((Player) sender).getPlayer();

        if(U_Permissions.wirelessRedstonePermissionsEnabled()) {
            if(player != null)
                if(!U_Permissions.wirelessRedstoneCommandsCancelLink(player)){
                    player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                    return true;
                }
        }

        for(R_Link l : R_Links.getList().values())
            if(!l.isLinked()) {
                if(player != null)
                    if (l.getPlayerId().equals(player.getUniqueId()))
                        link = l;
            }

        if(link != null){
            R_Links.remove(link);
            player.sendMessage(ChatColor.GREEN + "You have cancelled this link.");
            return true;
        }

        if(player != null)
            player.sendMessage(ChatColor.RED + "There is no link to cancel.");
        return true;
    }
}
