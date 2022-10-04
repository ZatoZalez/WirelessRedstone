package me.zatozalez.wirelessredstone.Config;

import org.bukkit.Bukkit;

public class C_Value {
    public static int getMaxLinksInServer(){
        C_Data data = C_Utility.getData("MaxLinksInServer");
        if(data != null) return data.getInt();
        return 0;
    }

    public static int getMaxDevicesInServer(){
        C_Data data = C_Utility.getData("MaxDevicesInServer");
        if(data != null) return data.getInt();
        return 0;
    }

    public static int getMaxLinksPerDevice(){
        C_Data data = C_Utility.getData("MaxLinksPerDevice");
        if(data != null) return data.getInt();
        return 0;
    }

    public static int getMaxLinkDistance(){
        C_Data data = C_Utility.getData("MaxLinkDistance");
        if(data != null) return data.getInt();
        return 0;
    }

    public static int getMaxDevicesPerPlayer(){
        C_Data data = C_Utility.getData("MaxDevicesPerPlayer");
        if(data != null) return data.getInt();
        return 0;
    }

    public static int getMaxLinksPerPlayer(){
        C_Data data = C_Utility.getData("MaxLinksPerPlayer");
        if(data != null) return data.getInt();
        return 0;
    }

    public static boolean allowCrossWorldSignal(){
        C_Data data = C_Utility.getData("AllowCrossWorldSignal");
        if(data != null) return data.getBoolean();
        return false;
    }

    public static boolean permissionsEnabled(){
        C_Data data = C_Utility.getData("PermissionsEnabled");
        if(data != null) return data.getBoolean();
        return false;
    }

    public static String getSenderBlockType(){
        C_Data data = C_Utility.getData("SenderBlockType");
        if(data != null) return data.getString();
        return null;
    }

    public static String getReceiverBlockType(){
        C_Data data = C_Utility.getData("ReceiverBlockType");
        if(data != null) return data.getString();
        return null;
    }
}
