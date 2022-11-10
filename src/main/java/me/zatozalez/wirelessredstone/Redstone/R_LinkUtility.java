package me.zatozalez.wirelessredstone.Redstone;

import me.zatozalez.wirelessredstone.Utils.U_Log;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class R_LinkUtility {
    private static final String filePath = WirelessRedstone.getPlugin().getDataFolder().getAbsolutePath() + "/links/";

    public static void initialize(){
        read();
    }
    private static void create() {
        try {
            Path path = Paths.get(filePath);
            if(!Files.isDirectory(path))
                Files.createDirectories(path);
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
            WirelessRedstone.Log(new U_Log(U_Log.LogType.INFORMATION, ChatColor.RED + "" + count + ChatColor.GRAY + " Link(s) have been loaded."));
    }
    public static void save(){
        if(R_Links.getList() != null && R_Links.getList().size() > 0)
            for (R_Link link : R_Links.getList().values())
                if(link != null)
                    if(link.isLinked())
                        saveLink(link);
        if(R_Links.getBrokenList() != null && R_Links.getBrokenList().size() > 0)
            for (R_Link link : R_Links.getBrokenList().values())
                if(link != null)
                    removeLink(link);
    }

    private static void saveLink(R_Link link){
        File file = new File(filePath + link.getId() + ".json");

        try {
            file.createNewFile();
            String data = link.getInline();
            Writer writer = new FileWriter(file, false);
            writer.write(data);
            writer.flush();
            writer.close();
        }catch(Exception e) {
            WirelessRedstone.Log(new U_Log(U_Log.LogType.ERROR, "Could not save to " + file.getAbsolutePath() +  "\n" + e.getMessage()));
        }
    }
    private static void removeLink(R_Link link){
        File file = new File(filePath + link.getId() + ".json");
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
            R_Device sender;
            R_Device receiver;

            if (id == null || !map.containsKey("sender") || !map.containsKey("receiver"))
                return false;

            sender = R_Devices.get(map.get("sender"));
            receiver = R_Devices.get(map.get("receiver"));

            R_Link link = new R_Link(file.getName().split("[.]")[0], sender, receiver);

            if (map.containsKey("playerid"))
                link.setPlayerId(map.get("playerid"));

            if(!link.exists())
            {
                link.destroy();
                removeLink(link);
                WirelessRedstone.Log(new U_Log(U_Log.LogType.WARNING, "A link could not be restored."));
                return false;
            }

            R_Links.add(link);
            return true;
        }catch(Exception e) {
            WirelessRedstone.Log(new U_Log(U_Log.LogType.ERROR, "Could not read " + file.getAbsolutePath() +  ". Skipping this link.\n" + e.getMessage()));
        }
        return false;
    }
}
