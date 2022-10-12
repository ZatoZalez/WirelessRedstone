package me.zatozalez.wirelessredstone.Redstone;

import me.zatozalez.wirelessredstone.Utils.U_Converter;
import me.zatozalez.wirelessredstone.Utils.U_Log;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class R_DeviceUtility {
    private static final String filePath = WirelessRedstone.getPlugin().getDataFolder().getAbsolutePath() + "/devices/";

    public static void initialize(){
        read();
    }
    private static void create() {
        try {
            if(!Files.isDirectory(Paths.get(filePath)))
                Files.createDirectories(Paths.get(filePath));
        } catch(Exception e) {
            WirelessRedstone.Log(new U_Log(U_Log.LogType.ERROR, "Could not create directory " + filePath +  "\n" + e.getMessage()));
        }
    }
    private static void read(){
        create();

        int count = 0;
        File directoryPath = new File(filePath);
        File[] filesList = directoryPath.listFiles();
        assert filesList != null;
        for(File file : filesList) {
            if(file != null && file.exists() && file.getName().contains("."))
            {
                if(readFile(file))
                    count++;
            }
        }

        if(count > 0)
            WirelessRedstone.Log(new U_Log(U_Log.LogType.INFORMATION, ChatColor.RED + "" + count + ChatColor.GRAY + " Device(s) have been loaded."));
    }
    public static void save(){
        if(R_Devices.getList() == null)
            return;

        try {
            if (R_Devices.getList() != null && R_Devices.getList().size() > 0)
                for (R_Device device : R_Devices.getList().values())
                    if (device != null)
                        saveDevice(device);
            if (R_Devices.getBrokenList() != null && R_Devices.getBrokenList().size() > 0)
                for (R_Device device : R_Devices.getBrokenList().values())
                    if (device != null)
                        removeDevice(device);
        } catch (Exception ignored) { }
    }

    private static void saveDevice(R_Device device){
        File file = new File(filePath + device.getId() + ".json");

        try {
            file.createNewFile();
            String data = device.getInline();
            Writer writer = new FileWriter(file, false);
            writer.write(data);
            writer.flush();
            writer.close();
        }catch(Exception e) {
            WirelessRedstone.Log(new U_Log(U_Log.LogType.ERROR, "Could not save to " + file.getAbsolutePath() +  "\n" + e.getMessage()));
        }
    }
    private static void removeDevice(R_Device device){
        File file = new File(filePath + device.getId() + ".json");
        try {
            file.delete();
        }catch(Exception e) {
            WirelessRedstone.Log(new U_Log(U_Log.LogType.ERROR, "Could not delete " + file.getAbsolutePath() +  "\n" + e.getMessage()));
        }
    }
    private static boolean readFile(File file){
        try {
            List<String> lines = new ArrayList<>();
            lines = Files.readAllLines(file.getAbsoluteFile().toPath(), StandardCharsets.UTF_8);
            if (lines.size() < 1)
                return false;

            Map<String, String> map = new HashMap<>();
            for (String s : lines) {
                if (s == null || s.length() < 3 || s.startsWith("#") || !s.contains(":"))
                    continue;

                String key = s.split(":")[0].trim().toLowerCase();
                String value = s.split(":", 2)[1].trim();
                map.put(key, value);
            }

            String id = file.getName().split("[.]")[0];
            Location location;
            R_Device.DeviceType deviceType;

            if (id == null || !map.containsKey("devicetype") || !map.containsKey("location"))
                return false;

            String[] coords = map.get("location").split(",");
            if(coords.length != 3) return false;
            location = new Location(Bukkit.getServer().getWorld(UUID.fromString(map.get("world"))), U_Converter.getDoubleFromString(coords[0]), U_Converter.getDoubleFromString(coords[1]), U_Converter.getDoubleFromString(coords[2]));
            deviceType = R_Device.DeviceType.valueOf(map.get("devicetype"));

            R_Device device = new R_Device(file.getName().split("[.]")[0], deviceType, location);

            if (map.containsKey("playerid"))
                device.setPlayerId(map.get("playerid"));

            if (map.containsKey("links")) {
                String[] links = map.get("links").split(",");
                if(links.length > 0)
                    for (String link : links)
                        if (link.trim().length() > 5)
                            device.addLink(link);
            }

            if(!device.exists())
            {
                removeDevice(device);
                WirelessRedstone.Log(new U_Log(U_Log.LogType.WARNING, "A device could not be restored."));
                return false;
            }

            R_Devices.add(device);
            return true;
        }catch(Exception e) {
            WirelessRedstone.Log(new U_Log(U_Log.LogType.ERROR, "Could not read " + file.getAbsolutePath() +  ". Skipping this device.\n" + e.getMessage()));
        }
        return false;
    }
}
