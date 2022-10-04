package me.zatozalez.wirelessredstone.Config;

import me.zatozalez.wirelessredstone.Utils.U_Log;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class C_Utility {

    private static ArrayList<C_Data> configData = new ArrayList<>();
    private static final String configPath = WirelessRedstone.getPlugin().getDataFolder().getAbsolutePath();
    private static final String configFile = "config.yml";

    public static void initialize() {
        try {
            read();
        } catch (Exception e) {
            WirelessRedstone.Log(new U_Log(U_Log.LogType.ERROR, "Could not create " + configFile + ".\n" + e.getMessage()));
        }
    }
    private static void create() {
        try {
            String data = "";
            File file = new File(configPath + "/" + configFile);
            file.getParentFile().mkdir();
            ArrayList<String> fileLines = C_Set.getDefaultConfig();

            for (String s : fileLines) {
                if (data.equalsIgnoreCase(""))
                    data += s;
                else
                    data += "\n" + s;
            }
            file.createNewFile();
            Writer writer = new FileWriter(file, false);
            writer.write(data);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            WirelessRedstone.Log(new U_Log(U_Log.LogType.ERROR, "Could not create " + configFile + ".\n" + e.getMessage()));
        }
    }

    public static void read() {
        File file = new File(configPath + "/" + configFile);
        if (!file.exists()) {
            create();
            return;
        }

        ArrayList<C_Data> tempData = new ArrayList<>();
        try {
            List<String> fileLines = new ArrayList<>();
            fileLines = Files.readAllLines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8);
            if (fileLines.size() < 1) {
                create();
                return;
            }
            for (String s : fileLines) {
                if (s == null || s.length() < 3 || !s.contains(":") || s.startsWith("#"))
                    continue;

                String key = s.split(":")[0].trim();
                String value = s.split(":", 2)[1].trim();
                if (key.contains("#"))
                    key = key.split("#")[0].trim();
                if (value.contains("#"))
                    value = value.split("#")[0].trim();
                tempData.add(new C_Data(key, value));
            }
        } catch (Exception e) {
            WirelessRedstone.Log(new U_Log(U_Log.LogType.ERROR, "Could not create " + configFile + ".\n" + e.getMessage()));
            return;
        }

        if (tempData.size() < 1)
            WirelessRedstone.Log(new U_Log(U_Log.LogType.ERROR, "The " + configFile + " could not be read. Creating new config..."));
        configData = tempData;
        create();
    }
    public static void save() {
        File file = new File(configPath + "/" + configFile);
        if (!file.exists()) {
            create();
            return;
        }

        try {
            String data = "";
            ArrayList<String> fileLines = new ArrayList<>();
            for (C_Data cd : configData)
                if (data.equalsIgnoreCase(""))
                    data += cd.getInline();
                else
                    data += "\n" + cd.getInline();
            file.createNewFile();
            Writer writer = new FileWriter(file, false);
            writer.write(data);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            WirelessRedstone.Log(new U_Log(U_Log.LogType.ERROR, "Could not create " + configFile + ".\n" + e.getMessage()));
        }
    }

    public static C_Data addData(String key, Object value) {
        return addData(key, value, null);
    }
    public static C_Data addData(String key, Object value, String comment) {
        for (C_Data cd : configData)
            if (cd.isValid() && cd.getKey().equalsIgnoreCase(key))
                return cd;

        C_Data data = new C_Data(key, value.toString());
        if (comment != null)
            data.setComment(comment);

        configData.add(data);
        return data;
    }
    public static C_Data addData(String description) {
        for (C_Data cd : configData)
            if (cd.isDescription() && cd.getDescription().equalsIgnoreCase(description))
                return cd;
        C_Data data = new C_Data(description);
        configData.add(data);
        return data;
    }
    public static C_Data getData(String key) {
        for (C_Data data : configData)
            if (data.isValid() && data.getKey().equalsIgnoreCase(key))
                return data;
        return null;
    }

    @Deprecated
    public static void removeData(String key) {
        for (C_Data cd : configData)
            if (cd.isValid() && cd.getKey().equalsIgnoreCase(key)) {
                configData.remove(cd);
                return;
            }
    }
}
