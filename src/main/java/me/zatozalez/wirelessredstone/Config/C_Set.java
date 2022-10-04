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

        fileLines.add(addData("SenderBlockType", "green_concrete").getInline());
        fileLines.add(addData("ReceiverBlockType", "red_concrete").getInline());
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

        fileLines.add(addData("Set up a maximum amount of devices in the server (0 is infinite).").getInline());
        fileLines.add(addData("MaxDevicesInServer", 0).getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("Set up a maximum amount of devices a player can have (0 is infinite).").getInline());
        fileLines.add(addData("MaxDevicesPerPlayer", 0).getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("Set up a distance limit for links when they are in the same world (0 is infinite).").getInline());
        fileLines.add(addData("MaxLinkDistance", 0).getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("Allow links to be created between worlds. Interworld links ignore the maximum link distance.").getInline());
        fileLines.add(addData("AllowCrossWorldSignal", true).getInline());
        fileLines.add(addData("").getInline());

        fileLines.add(addData("Enable or disable all the permissions. Permissions can allow players to override some of the settings in the config.").getInline());
        fileLines.add(addData("PermissionsEnabled", true).getInline());
        //--
        return fileLines;
    }
}
