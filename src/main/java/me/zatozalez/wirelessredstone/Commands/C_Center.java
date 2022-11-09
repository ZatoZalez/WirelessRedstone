package me.zatozalez.wirelessredstone.Commands;

import me.zatozalez.wirelessredstone.Commands.Device.CD_CommandExecutor;
import me.zatozalez.wirelessredstone.Commands.WirelessRedstone.CW_CommandExecutor;
import me.zatozalez.wirelessredstone.Tabs.T_Device;
import me.zatozalez.wirelessredstone.Tabs.T_WirelessRedstone;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.command.*;

import java.lang.reflect.Field;
import java.util.HashMap;

public class C_Center {

    private HashMap<PluginCommand, CommandExecutor> commandList = new HashMap<>();
    private HashMap<PluginCommand, TabCompleter> tabList = new HashMap<>();

    public C_Center(){

    }

    public void initialize(){
        //commandList.put(WirelessRedstone.getPlugin().getCommand("debug"), new C_Debug());
        commandList.put(WirelessRedstone.getPlugin().getCommand("wirelessredstone"), new CW_CommandExecutor());
        commandList.put(WirelessRedstone.getPlugin().getCommand("device"), new CD_CommandExecutor());
        tabList.put(WirelessRedstone.getPlugin().getCommand("wirelessredstone"), new T_WirelessRedstone());
        tabList.put(WirelessRedstone.getPlugin().getCommand("device"), new T_Device());

        for(PluginCommand pCommand : commandList.keySet())
            pCommand.setExecutor(commandList.get(pCommand));

        for(PluginCommand pCommand : tabList.keySet())
            pCommand.setTabCompleter(tabList.get(pCommand));
    }

    public void unload(){
        for(PluginCommand pCommand : commandList.keySet())
            unRegisterBukkitCommand(pCommand);
    }

    private Object getPrivateField(Object object, String field)throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field objectField = clazz.getDeclaredField(field);
        objectField.setAccessible(true);
        Object result = objectField.get(object);
        objectField.setAccessible(false);
        return result;
    }

    private void unRegisterBukkitCommand(PluginCommand cmd) {
        try {
            Object result = getPrivateField(WirelessRedstone.getPlugin().getServer().getPluginManager(), "commandMap");
            SimpleCommandMap commandMap = (SimpleCommandMap) result;
            Object map = getPrivateField(commandMap, "knownCommands");
            @SuppressWarnings("unchecked")
            HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
            knownCommands.remove(cmd.getName());
            for (String alias : cmd.getAliases()){
                if(knownCommands.containsKey(alias) && knownCommands.get(alias).toString().contains(WirelessRedstone.getPlugin().getName())){
                    knownCommands.remove(alias);
                }
            }
        } catch (Exception e) {
        }
    }
}
