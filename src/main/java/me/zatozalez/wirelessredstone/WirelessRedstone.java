package me.zatozalez.wirelessredstone;

import me.zatozalez.wirelessredstone.Commands.C_CancelLink;
import me.zatozalez.wirelessredstone.Commands.C_Debug;
import me.zatozalez.wirelessredstone.Commands.C_GiveDevice;
import me.zatozalez.wirelessredstone.Config.C_Utility;
import me.zatozalez.wirelessredstone.Listeners.Modified.*;
import me.zatozalez.wirelessredstone.Listeners.Natural.*;
import me.zatozalez.wirelessredstone.Redstone.*;
import me.zatozalez.wirelessredstone.Tabs.T_GiveDevice;
import me.zatozalez.wirelessredstone.Utils.U_Log;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class WirelessRedstone extends JavaPlugin {

    private static WirelessRedstone plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        initializePlugin();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        disablePlugin();
    }

    private void initializePlugin() {
        plugin = this;
        Bukkit.getConsoleSender().sendMessage(getDescription().getFullName() + " by " + ChatColor.RED + getDescription().getAuthors().toString().replace("[", "").replace("]", ""));

        C_Utility.initialize();
        R_Manager.initialize();
        R_DeviceUtility.initialize();
        R_LinkUtility.initialize();

        new LN_BlockPlace(this);
        new LN_BlockBreak(this);
        new LN_BlockClick(this);
        new LN_BlockMove(this);
        new LN_BlockPower(this);
        new LN_WireBreak(this);

        new LM_DevicePlace(this);
        new LM_DeviceBreak(this);
        new LM_DeviceClick(this);
        new LM_DeviceMove(this);
        new LM_DevicePower(this);

        getCommand("debug").setExecutor(new C_Debug());
        getCommand("givedevice").setExecutor(new C_GiveDevice());
        getCommand("givedevice").setTabCompleter(new T_GiveDevice());
        getCommand("cancellink").setExecutor(new C_CancelLink());

        //test at start up
        for(R_Link link : R_Links.getList().values())
            if(link.isLinked()){
                link.getSender().updateSignalPower();
            }
    }

    private void disablePlugin(){
        //C_Utility.save();
        R_DeviceUtility.save();
        R_LinkUtility.save();
    }

    public static void Log(U_Log logMes){
        String type = "";
        if(logMes.logType.equals(U_Log.LogType.ERROR))
            type = ChatColor.RED +  "[" + logMes.logType + "] ";
        if(logMes.logType.equals(U_Log.LogType.WARNING))
            type = ChatColor.GOLD +  "[" + logMes.logType + "] ";
        Bukkit.getConsoleSender().sendMessage("[" + getPlugin().getDescription().getName() + "] "
                + type
                + logMes.message);
    }
    public static WirelessRedstone getPlugin(){
        return plugin;
    }
}
