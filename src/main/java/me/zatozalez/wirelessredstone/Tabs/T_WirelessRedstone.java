package me.zatozalez.wirelessredstone.Tabs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class T_WirelessRedstone implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("disable");
            arguments.add("reload");
            return arguments;
        }

        List<String> arguments = new ArrayList<>();
        arguments.add("");
        return arguments;
    }
}
