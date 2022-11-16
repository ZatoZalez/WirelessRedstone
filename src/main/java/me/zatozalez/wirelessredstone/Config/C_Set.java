package me.zatozalez.wirelessredstone.Config;

import me.zatozalez.wirelessredstone.WirelessRedstone;

import java.util.ArrayList;

import static me.zatozalez.wirelessredstone.Config.C_Utility.addData;

//REWORKED
public class C_Set {
    private static ArrayList<String> fileLines = new ArrayList<>();
    public static ArrayList<String> getDefaultConfig(){
        //default config
        addDescription("Configuration for " + WirelessRedstone.getPlugin().getDescription().getName() + " " + WirelessRedstone.getPlugin().getDescription().getVersion() + " by ZatoZalez.");
        addNewLine();
        addNewLine();

        addDescription("Modify the default item names and lore.");
        addDescription("TIP: Use the in-game reload command or restart the server to update these values in-game.");

        addKeyAndValue("SenderItemName", "Redstone Sender");
        addKeyAndValue("SenderItemLore", "Sends wireless Redstone signals");
        addKeyAndValue("ReceiverItemName", "Redstone Receiver");
        addKeyAndValue("ReceiverItemLore", "Receives wireless Redstone signals");
        addNewLine();

        addDescription("IMPORTANT: Changing block types will disable all placed redstone devices of other materials.");
        addDescription("Modify the block type of both devices. At invalid block type it will set the default type.");
        addDescription("TIP: Use https://minecraftitemids.com/ for the correct item ids.");

        addKeyAndValue("SenderBlockType", "null");
        addKeyAndValue("ReceiverBlockType", "null");
        addNewLine();

        addDescription("Enable or disable all the permissions. Permissions can allow players to override some of the settings in the config.");
        addKeyAndValue("Permissions", false);
        addNewLine();

        addDescription("Enable or disable receiving messages for placing, breaking or overloading your devices.");
        addKeyAndValue("Messages", true);
        addNewLine();

        addDescription("======== Device Restrictions ========");
        addDescription("Devices are the physical blocks that represent RedstoneSenders and RedstoneReceivers. You can place, break and link these devices in any world.");
        addDescription("You can limit the amount of devices in your world with the following settings.");
        addNewLine();

        addDescription("Set up a maximum amount of devices in the server (0 is infinite).");
        addKeyAndValue("MaxDevicesInServer", 0);
        addNewLine();

        addDescription("Set up a maximum amount of devices a player can have (0 is infinite).");
        addKeyAndValue("MaxDevicesPerPlayer", 0);
        addNewLine();

        addDescription("Allow players to break redstone devices placed by other players.");
        addKeyAndValue("BreakThirdDevices", true);
        addNewLine();

        addDescription("Enable or disable the default craft recipe for the redstone devices.");
        addKeyAndValue("Recipes", true);
        addNewLine();

        addDescription("======== Link Restrictions ========");
        addDescription("Links are the invisible connection between two devices. You can create multiple links between multiple devices and they can become as advanced as you allow them to be with these settings.");
        addNewLine();

        addDescription("Set up a maximum amount of links between devices in the server (0 is infinite).");
        addKeyAndValue("MaxLinksInServer", 0);
        addNewLine();

        addDescription("Set up a maximum amount of links a player can have (0 is infinite).");
        addKeyAndValue("MaxLinksPerPlayer", 0);
        addNewLine();

        addDescription("Set up a maximum amount of links a device can have (0 is infinite).");
        addKeyAndValue("MaxLinksPerDevice", 0);
        addNewLine();

        addDescription("Set up a distance limit for links when they are in the same world (0 is infinite).");
        addKeyAndValue("MaxLinkDistance", 0);
        addNewLine();

        addDescription("Allow links to be created between worlds. Interworld links ignore the maximum link distance.");
        addKeyAndValue("CrossWorldSignals", true);
        addNewLine();

        addDescription("Allow RedstoneReceiver to power RedstoneSender when in touching each other. (This does not apply for devices that are linked with each other.");
        addKeyAndValue("ContactSignals", true);
        addNewLine();

        addDescription("Set up a delay (in ticks) for the signal to travel from the RedstoneSender to the RedstoneReceiver (0 is instantly). Every 20 ticks is 1 second.");
        addKeyAndValue("SignalDelay", 0);
        addNewLine();

        addDescription("Allow players to create and destroy links on devices from other players.");
        addKeyAndValue("CreateThirdLinks", true);
        addKeyAndValue("DestroyThirdLinks", true);
        addNewLine();

        addDescription("======== Overload ========");
        addDescription("Overload is a feature that allows devices to go into a safe mode.");
        addDescription("In this mode they will not send, receive or emit signals but remain inactive until the overload has ended.");
        addDescription("Overload will prevent servers from crashing or lagging when certain redstone machines are made.");
        addDescription("It is recommended to have this option enabled unless you know what you are doing.");
        addKeyAndValue("Overload", true);
        addNewLine();

        addDescription("The trigger is the amount of signals it needs every second for it to overload.");
        addKeyAndValue("OverloadTrigger", 8);
        addNewLine();

        addDescription("The cooldown is the time (in seconds) it takes for a device to switch back on after being overloaded.");
        addKeyAndValue("OverloadCooldown", 10);
        addNewLine();
        //--

        return fileLines;
    }

    private static void addKeyAndValue(String key, Object value){
        fileLines.add(addData(key, value).getInline());
    }

    private static void addKeyAndValue(String key, Object value, String comment){
        fileLines.add(addData(key, value, comment).getInline());
    }

    private static void addDescription(String description){
        fileLines.add(addData(description).getInline());
    }

    private static void addNewLine(){
        fileLines.add(addData("").getInline());
    }
}
