package me.zatozalez.wirelessredstone.Commands.WirelessRedstone;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.zatozalez.wirelessredstone.Commands.WirelessRedstone.CW_Handle.*;
import static me.zatozalez.wirelessredstone.Commands.WirelessRedstone.CW_Push.pushInvalidArguments;
import static me.zatozalez.wirelessredstone.Commands.WirelessRedstone.CW_Validity.hasValidArguments;

public class CW_CommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args){
        if(!(sender instanceof Player))
            return false;

        Player player = ((Player) sender).getPlayer();

        if(!hasValidArguments(player, args, 1, 3, new String[] {
                "- /wirelessredstone info",
                "- /wirelessredstone reload",
                "- /wirelessredstone disable"
        }))
            return true;

        switch(args[0]){
            case "help":
            case "info":
                return handleInfo(player, args);
            case "reload":
                return handleReload(player, args);
            case "disable":
                return handleDisable(player, args);
            default:
                return pushInvalidArguments(player, new String[] {
                        "- /wirelessredstone info",
                        "- /wirelessredstone reload",
                        "- /wirelessredstone disable"
                });
        }
    }
}
