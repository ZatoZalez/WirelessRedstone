package me.zatozalez.wirelessredstone.Utils;

import me.zatozalez.wirelessredstone.Redstone.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class U_Api {
    public U_Api(){ }

    public ItemStack getRedstoneSender(){ return R_Items.RedstoneSender.itemStack; }

    public ItemStack getRedstoneReceiver(){ return R_Items.RedstoneReceiver.itemStack; }

    public List<R_Device> getAllRedstoneDevices(){ return new ArrayList<>(R_Devices.getList().values()); }

    public List<R_Link> getAllLinks(){ return new ArrayList<>(R_Links.getList().values()); }

    public List<R_Device> getAllPlayerRedstoneDevices(UUID playerId) { return R_Devices.getList(playerId); }

    public List<R_Link> getAllPlayerLinks(UUID playerId) { return R_Links.getList(playerId); }

    public int getTotalRedstoneDeviceCount() { return R_Devices.getList().size(); }

    public int getPlayerRedstoneDeviceCount(UUID playerId) { return R_Devices.getList(playerId).size(); }

    public int getTotalLinkCount() { return R_Links.getList().size(); }
}
