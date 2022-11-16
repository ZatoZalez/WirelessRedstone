package me.zatozalez.wirelessredstone.Config;

//REWORKED
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
        C_Data data = C_Utility.getData("CrossWorldSignal");
        if(data != null) return data.getBoolean();
        return false;
    }

    public static boolean allowPermissions(){
        C_Data data = C_Utility.getData("Permissions");
        if(data != null) return data.getBoolean();
        return false;
    }

    public static boolean allowMessages(){
        C_Data data = C_Utility.getData("Messages");
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

    public static boolean overloadEnabled(){
        C_Data data = C_Utility.getData("Overload");
        if(data != null) return data.getBoolean();
        return true;
    }

    public static int getOverloadTrigger(){
        C_Data data = C_Utility.getData("OverloadTrigger");
        if(data != null) return data.getInt();
        return 8;
    }

    public static int getOverloadCooldown(){
        C_Data data = C_Utility.getData("OverloadCooldown");
        if(data != null) return data.getInt();
        return 10;
    }

    public static int getSignalDelay(){
        C_Data data = C_Utility.getData("SignalDelay");
        if(data != null) return data.getInt();
        return 0;
    }

    public static boolean allowContactSignals(){
        C_Data data = C_Utility.getData("ContactSignals");
        if(data != null) return data.getBoolean();
        return true;
    }

    public static String getSenderItemName(){
        C_Data data = C_Utility.getData("SenderItemName");
        if(data != null) return data.getString();
        return null;
    }

    public static String getSenderItemLore(){
        C_Data data = C_Utility.getData("SenderItemLore");
        if(data != null) return data.getString();
        return null;
    }

    public static String getReceiverItemName(){
        C_Data data = C_Utility.getData("ReceiverItemName");
        if(data != null) return data.getString();
        return null;
    }

    public static String getReceiverItemLore(){
        C_Data data = C_Utility.getData("ReceiverItemLore");
        if(data != null) return data.getString();
        return null;
    }

    public static boolean allowRecipes(){
        C_Data data = C_Utility.getData("Recipes");
        if(data != null) return data.getBoolean();
        return true;
    }

    public static boolean allowBreakThirdDevices(){
        C_Data data = C_Utility.getData("BreakThirdDevices");
        if(data != null) return data.getBoolean();
        return true;
    }

    public static boolean allowDestroyThirdLinks(){
        C_Data data = C_Utility.getData("DestroyThirdLinks");
        if(data != null) return data.getBoolean();
        return true;
    }

    public static boolean allowCreateThirdLinks(){
        C_Data data = C_Utility.getData("CreateThirdLinks");
        if(data != null) return data.getBoolean();
        return true;
    }
}
