package me.zatozalez.wirelessredstone.Messages;

import me.zatozalez.wirelessredstone.Config.C_Set;
import me.zatozalez.wirelessredstone.Config.C_Value;
import me.zatozalez.wirelessredstone.Redstone.DeviceType;
import me.zatozalez.wirelessredstone.Utils.U_Device;
import me.zatozalez.wirelessredstone.Utils.U_Log;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class M_Utility {
    private static LinkedHashMap<String, String> defaultMessages = new LinkedHashMap<>();
    private static LinkedHashMap<String, String> messages = new LinkedHashMap<>();
    private static final String messagesPath = WirelessRedstone.getPlugin().getDataFolder().getAbsolutePath();
    private static final String messagesFile = "messages.yml";

    public static void initialize(){
        File file = new File(messagesPath + "/" + messagesFile);
        defaultMessages = readAndLoad(WirelessRedstone.getPlugin().getClass().getResourceAsStream("/" + messagesFile));
        if (!file.exists()) {
            create();
            messages = defaultMessages;
        }
        try {
            messages = readAndLoad(new FileInputStream(file));
            update();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return;
    }

    private static void create() {
        try {
            byte[] buffer = WirelessRedstone.getPlugin().getClass().getResourceAsStream("/" + messagesFile).readAllBytes();

            File targetFile = new File(messagesPath + "/" + messagesFile);
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
            outStream.close();
        } catch (IOException e) {
            WirelessRedstone.Log(new U_Log(U_Log.LogType.ERROR, "Could not create " + messagesFile + ".\n" + e.getMessage()));
        }
    }

    private static LinkedHashMap<String, String> readAndLoad(InputStream inputStream){
        Yaml yaml = new Yaml();
        try {
            @SuppressWarnings("unchecked")
            LinkedHashMap<String, String> values = yaml.load(inputStream);
            inputStream.close();
            return values;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void update(){
        boolean update = false;
        if(messages != null) {
            for (String string : defaultMessages.keySet()) {
                if (!messages.containsKey(string)) {
                    update = true;
                    WirelessRedstone.Log(new U_Log(U_Log.LogType.WARNING, "adding: " + string));
                    messages.put(string, defaultMessages.get(string));
                }
            }
        }
        else{
            create();
            return;
        }

        if(update){
            try {
                StringBuilder data = new StringBuilder();
                File file = new File(messagesPath + "/" + messagesFile);
                file.getParentFile().mkdir();
                for (String s : messages.keySet()) {
                    if (data.toString().equalsIgnoreCase(""))
                        data.append(s).append(": ").append("\"").append(messages.get(s)).append("\"");
                    else
                        data.append("\n").append(s).append(": ").append("\"").append(messages.get(s)).append("\"");
                }
                file.createNewFile();
                Writer writer = new FileWriter(file, false);
                writer.write(data.toString());
                writer.flush();
                writer.close();
            } catch (Exception e) {
                WirelessRedstone.Log(new U_Log(U_Log.LogType.ERROR, "Could not update " + messagesFile + ".\n" + e.getMessage()));
            }
        }
    }

    public static String getMessage(String key){
        return getMessage(key, null);
    }

    public static String getMessage(String key, HashMap<String, Object> parameters){
        String str = colorString(messages.get(key));
        if(str == null)
            str = colorString(defaultMessages.get(key));
        if(str == null)
            return ChatColor.RED + "Could not find message key: " + ChatColor.DARK_RED + key + ChatColor.RED + ". This is a Bug.";
        if(parameters == null)
            return str;
        for(String k : parameters.keySet())
            if(str.contains(k))
                str = str.replace(k, parameters.get(k).toString());
        return str;
    }

    public static boolean sendMessage(Player player, String value){
        if(!C_Value.allowMessages())
            return false;
        if(player == null)
            return false;
        if(value.startsWith("!!"))
            return false;
        player.sendMessage(value);
        return true;
    }

    public static HashMap placeHolder(Object ... parameters){
        if (parameters == null || parameters.length % 2 != 0 || parameters.length == 0)
            return null;

        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < parameters.length; i++) {
            String value = parameters[(i+1)].toString();
            if(value.equals(DeviceType.RedstoneSender.toString()))
                value = U_Device.getDeviceName(DeviceType.RedstoneSender);
            else if(value.equals(DeviceType.RedstoneReceiver.toString()))
                value = U_Device.getDeviceName(DeviceType.RedstoneReceiver);
            map.put(parameters[i].toString(), value);
            i++;
        }
        return map;
    }

    private static String colorString(String str){
        if(str == null)
            return null;
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
