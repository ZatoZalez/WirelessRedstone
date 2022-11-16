package me.zatozalez.wirelessredstone;

import me.zatozalez.wirelessredstone.Commands.C_Center;
import me.zatozalez.wirelessredstone.Config.C_Utility;
import me.zatozalez.wirelessredstone.Listeners.Modified.*;
import me.zatozalez.wirelessredstone.Listeners.Natural.*;
import me.zatozalez.wirelessredstone.Messages.M_Utility;
import me.zatozalez.wirelessredstone.Redstone.*;
import me.zatozalez.wirelessredstone.Utils.U_Log;
import me.zatozalez.wirelessredstone.Versions.V_Manager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class WirelessRedstone extends JavaPlugin {

    private static WirelessRedstone plugin;
    private static C_Center commandCenter = new C_Center();

    @Override
    public void onEnable() {
        // Plugin startup logic
        initializePlugin(false);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        disablePlugin();
    }

    private void initializePlugin(boolean reload) {
        plugin = this;
        String latest = V_Manager.getLatestVersion();
        if(latest != null && !latest.equals(getDescription().getVersion())){
            WirelessRedstone.Log(new U_Log(U_Log.LogType.WARNING, "New update available [" + latest + "]! Visit " + "https://www.spigotmc.org/resources/101871/" + " to download the latest version."));
        }
        V_Manager.setVersion();
        Bukkit.getConsoleSender().sendMessage(getDescription().getFullName() + " by " + ChatColor.RED + getDescription().getAuthors().toString().replace("[", "").replace("]", ""));
        if(!V_Manager.isCompatible()){
            WirelessRedstone.Log(new U_Log(U_Log.LogType.ERROR, "Unsupported version [" + V_Manager.version + "]. Disabling WirelessRedstone."));
            Bukkit.getPluginManager().disablePlugin(WirelessRedstone.getPlugin());
            return;
        }

        C_Utility.initialize(reload);
        R_Items.initialize();

        if(!this.isEnabled())
            return;

        R_DeviceUtility.initialize();
        R_LinkUtility.initialize();
        M_Utility.initialize();
        commandCenter.initialize();

        if(!reload)
            for(R_Link link : R_Links.getList().values())
                if(link.isLinked()){
                    link.getReceiver().setUpdating(true);
                    link.getSender().updateSignalPower();
                }

        if(!reload)
            registerEvents();
    }

    private void registerEvents(){
        new LN_BlockPlace(this);
        new LN_BlockBreak(this);
        new LN_BlockClick(this);
        new LN_BlockMove(this);
        new LN_BlockPower(this);
        new LN_WireBreak(this);
        new LN_RecipeUnlock(this);
        new LN_PlayerJoin(this);
        new LN_TorchPower(this);
        new LN_HopperPower(this);

        new LM_DevicePlace(this);
        new LM_DeviceBreak(this);
        new LM_DeviceClick(this);
        new LM_DeviceMove(this);
        new LM_DevicePower(this);
    }

    private void disablePlugin(){
        try {
            R_DeviceUtility.save();
            R_LinkUtility.save();
        }catch (Exception ignored) {}

        commandCenter.unload();
        R_Items.unload();
    }

    public void reloadPlugin(){
        disablePlugin();
        initializePlugin(true);
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
