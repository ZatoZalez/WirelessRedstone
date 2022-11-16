package me.zatozalez.wirelessredstone.Versions;

import me.zatozalez.wirelessredstone.Utils.U_Log;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Piston;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.inventory.ItemStack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class V_Manager {
    public static String minecraftVersion = "0.0.0";
    public static String pluginVersion = "0.0.0";
    public static String latestPluginVersion = "0.0.0";
    private static String api = "https://api.github.com/repos/ZatoZalez/WirelessRedstone/releases/latest";
    public static void setVersion(){
        String v = Bukkit.getVersion();
        v = v.substring(v.indexOf("(") + 1);
        v = v.substring(0, v.indexOf(")"));
        v = v.substring(4);
        minecraftVersion = (v.split("[.]")[0] + "." +  v.split("[.]")[1]);
        latestPluginVersion = minecraftVersion;
    }

    public static boolean isCompatible(){
        switch(minecraftVersion) {
            case "1.16":
            case "1.17":
            case "1.18":
            case "1.19":
                return true;
            default:
                return false;
        }
    }

    public static ItemStack[] getItemStack(){
        switch(minecraftVersion) {
            case "1.16":
                return V_1_16.getItemStack();
            case "1.17":
                return V_1_17.getItemStack();
            case "1.18":
                return V_1_18.getItemStack();
            case "1.19":
                return V_1_19.getItemStack();
        }
        return null;
    }

    public static boolean cancelPistonEvent(BlockPhysicsEvent e, Block pistonBlock, Piston piston){
        switch(minecraftVersion) {
            case "1.16":
                return V_1_16.cancelPistonEvent(e, pistonBlock, piston);
            case "1.17":
                return V_1_17.cancelPistonEvent(e, pistonBlock, piston);
            case "1.18":
                return V_1_18.cancelPistonEvent(e, pistonBlock, piston);
            case "1.19":
                return V_1_19.cancelPistonEvent(e);
            default:
                return false;
        }
    }

    public static String getLatestVersion() {
        pluginVersion = WirelessRedstone.getPlugin().getDescription().getVersion();
        try {
            String data = stream(new URL(api));
            if(data == null){
                return null;
            }

            if(!data.contains(":") || !data.contains(",")){
                return null;
            }

            String[] entries = data.split(",");
            for(String entry : entries){
                if(entry.contains("tag_name")){
                    if(entry.contains(":")){
                        latestPluginVersion = entry.split(":")[1].replace("\"", "").trim();
                        return latestPluginVersion;
                    }
                }
            }

        } catch (MalformedURLException ignored) { }
        return null;
    }

    public static String stream(URL url) {
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            return json.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
