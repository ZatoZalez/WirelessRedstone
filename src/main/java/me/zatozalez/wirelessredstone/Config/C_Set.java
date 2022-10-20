package me.zatozalez.wirelessredstone.Config;

import me.zatozalez.wirelessredstone.WirelessRedstone;

import java.util.ArrayList;

import static me.zatozalez.wirelessredstone.Config.C_Utility.addData;

public class C_Set {
    public static ArrayList<String> getDefaultConfig(){
        ArrayList<String> fileLines = new ArrayList<>();

        //default config
        fileLines.add(addData("Configuration for " + WirelessRedstone.getPlugin().getDescription().getName() + " " + WirelessRedstone.getPlugin().getDescription().getVersion() + " by ZatoZalez.").getInline());
        fileLines.add(addData("").getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("Modify the block type of both devices. At invalid block type it will set the default type.").getInline());
        fileLines.add(addData("(Tip: use https://minecraftitemids.com/ for the correct item ids.)").getInline());

        fileLines.add(addData("SenderBlockType", "null").getInline());
        fileLines.add(addData("ReceiverBlockType", "null").getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("Enable or disable all the permissions. Permissions can allow players to override some of the settings in the config.").getInline());
        fileLines.add(addData("Permissions", true).getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("Enable or disable receiving messages for placing, breaking or overloading your devices.").getInline());
        fileLines.add(addData("Messages", true).getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("======== Device Restrictions ========").getInline());
        fileLines.add(addData("Devices are the physical blocks that represent RedstoneSenders and RedstoneReceivers. You can place, break and link these devices in any world.").getInline());
        fileLines.add(addData("You can limit the amount of devices in your world with the following settings.").getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("Set up a maximum amount of devices in the server (0 is infinite).").getInline());
        fileLines.add(addData("MaxDevicesInServer", 0).getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("Set up a maximum amount of devices a player can have (0 is infinite).").getInline());
        fileLines.add(addData("MaxDevicesPerPlayer", 0).getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("======== Link Restrictions ========").getInline());
        fileLines.add(addData("Links are the invisible connection between two devices. You can create multiple links between multiple devices and they can become as advanced as you allow them to be with these settings.").getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("Set up a maximum amount of links between devices in the server (0 is infinite).").getInline());
        fileLines.add(addData("MaxLinksInServer", 0).getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("Set up a maximum amount of links a player can have (0 is infinite).").getInline());
        fileLines.add(addData("MaxLinksPerPlayer", 0).getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("Set up a maximum amount of links a device can have (0 is infinite).").getInline());
        fileLines.add(addData("MaxLinksPerDevice", 0).getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("Set up a distance limit for links when they are in the same world (0 is infinite).").getInline());
        fileLines.add(addData("MaxLinkDistance", 0).getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("Allow links to be created between worlds. Interworld links ignore the maximum link distance.").getInline());
        fileLines.add(addData("CrossWorldSignals", true).getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("Allow RedstoneReceiver to power RedstoneSender when in touching each other. (This does not apply for devices that are linked with each other.").getInline());
        fileLines.add(addData("ContactSignals", true).getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("Set up a delay (in ticks) for the signal to travel from the RedstoneSender to the RedstoneReceiver (0 is instantly). Every 20 ticks is 1 second.").getInline());
        fileLines.add(addData("SignalDelay", 0).getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("======== Overload ========").getInline());
        fileLines.add(addData("Overload is a feature that allows devices to go into a safe mode.").getInline());
        fileLines.add(addData("In this mode they will not send, receive or emit signals but remain inactive until the overload has ended.").getInline());
        fileLines.add(addData("Overload will prevent servers from crashing or lagging when certain redstone machines are made.").getInline());
        fileLines.add(addData("It is recommended to have this option enabled unless you know what you are doing.").getInline());
        fileLines.add(addData("Overload", true).getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("The trigger is the amount of signals it needs every second for it to overload.").getInline());
        fileLines.add(addData("OverloadTrigger", 8).getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("The cooldown is the time (in seconds) it takes for a device to switch back on after being overloaded.").getInline());
        fileLines.add(addData("OverloadCooldown", 10).getInline());
        fileLines.add(addData("").getInline());
        //--
        return fileLines;
    }
}
