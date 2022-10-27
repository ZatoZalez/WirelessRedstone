package me.zatozalez.wirelessredstone.Tabs;

import me.zatozalez.wirelessredstone.Config.C_Value;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class T_Device implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("info");
            arguments.add("give");
            arguments.add("link");
            return arguments;
        }
        else if (args[0].toLowerCase().equals("give")){
            if (args.length == 2) {
                List<String> arguments = new ArrayList<>();
                for(Player player : Bukkit.getOnlinePlayers())
                    arguments.add(player.getName());
                arguments.add("all");
                arguments.add("*");
                return arguments;
            }else

            if (args.length == 3) {
                List<String> arguments = new ArrayList<>();
                arguments.add(ChatColor.stripColor(translateAlternateColorCodes('&', C_Value.getSenderItemName().replace(" ", ""))));
                arguments.add(ChatColor.stripColor(translateAlternateColorCodes('&', C_Value.getReceiverItemName().replace(" ", ""))));
                arguments.add("*");
                return arguments;
            } else

            if (args.length == 4) {
                List<String> arguments = new ArrayList<>();
                arguments.add("1");
                return arguments;
            }
        }
        else if (args[0].toLowerCase().equals("link")){
            if (args.length == 2) {
                List<String> arguments = new ArrayList<>();
                arguments.add("cancel");
                arguments.add("breakall");
                arguments.add("breakfirst");
                arguments.add("breaklast");
                return arguments;
            }
        }

        List<String> arguments = new ArrayList<>();
        arguments.add("");
        return arguments;
    }
}
