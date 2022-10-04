package me.zatozalez.wirelessredstone.Tabs;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class T_GiveDevice implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            for(Player player : Bukkit.getOnlinePlayers())
                arguments.add(player.getName());
            return arguments;
        }else

        if (args.length == 2) {
            List<String> arguments = new ArrayList<>();
            arguments.add("RedstoneSender");
            arguments.add("RedstoneReceiver");
            return arguments;
        } else

        if (args.length == 3) {
            List<String> arguments = new ArrayList<>();
            arguments.add("1");
            return arguments;
        }
        List<String> arguments = new ArrayList<>();
        arguments.add("");
        return arguments;
    }
}
