package me.zatozalez.wirelessredstone.Commands.Device;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.zatozalez.wirelessredstone.Commands.Device.CD_Handle.*;
import static me.zatozalez.wirelessredstone.Commands.Device.CD_Push.pushInvalidArguments;
import static me.zatozalez.wirelessredstone.Commands.Device.CD_Validity.hasValidArguments;

//REWORKED
public class CD_CommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args){
        if(!(sender instanceof Player))
            return false;

        Player player = ((Player) sender).getPlayer();

        if(!hasValidArguments(player, args, 1, 4, new String[] {
                "- /device info",
                "- /device give",
                "- /device link",
                "- /device deleteall" }))
            return true;

        switch(args[0]){
            case "info":
                return handleInfo(player, args);
            case "give":
                return handleGive(player, args);
            case "link":
                return handleLink(player, args);
            //case "deleteall":
            //    return handleDelete(player, args);
            default:
                return pushInvalidArguments(player, new String[] {
                        "- /device info",
                        "- /device give",
                        "- /device link",
                        "- /device deleteall" });
        }
    }
}
