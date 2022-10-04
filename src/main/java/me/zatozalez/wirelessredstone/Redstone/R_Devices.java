package me.zatozalez.wirelessredstone.Redstone;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class R_Devices {
    private static final LinkedHashMap<String, R_Device> devices = new LinkedHashMap<>();
    private static final LinkedHashMap<String, R_Device> broken_devices = new LinkedHashMap<>();

    public static LinkedHashMap<String, R_Device> getList(){
        return devices;
    }

    public static List<R_Device> getList(UUID playerId){
        List<R_Device> list = new ArrayList<>();
        for(R_Device device : devices.values())
            if(device.getPlayerId().equals(playerId))
                list.add(device);
        return list;
    }

    public static LinkedHashMap<String, R_Device> getBrokenList(){
        return broken_devices;
    }

    public static R_Device add(R_Device device){
        add(false, device);
        return device;
    }

    public static R_Device add(boolean override, R_Device device){
        R_Device placeHolderDevice = null;
        for(R_Device d : devices.values())
            if (d.getId().equalsIgnoreCase(device.getId()))
                placeHolderDevice = d;

        if(placeHolderDevice != null)
            if (override)
            {
                devices.remove(placeHolderDevice);
                device = placeHolderDevice;
            }

        devices.put(device.getId(), device);
        return device;
    }

    public static R_Device get(String deviceid){
        return devices.get(deviceid);
    }

    public static R_Device get(Location location){
        for(R_Device device : devices.values())
            if(device.getLocation().equals(location))
                return device;
        return null;
    }

    public static void remove(String deviceid){
        remove(get(deviceid));
    }

    public static void remove(R_Device device){
        if(device == null)
            return;
        devices.remove(device.getId());
        broken_devices.put(device.getId(), device);
    }
}
