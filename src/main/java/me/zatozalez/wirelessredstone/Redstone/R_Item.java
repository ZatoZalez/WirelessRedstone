package me.zatozalez.wirelessredstone.Redstone;

import me.zatozalez.wirelessredstone.Config.C_Utility;
import me.zatozalez.wirelessredstone.Config.C_Value;
import me.zatozalez.wirelessredstone.Utils.U_Converter;
import me.zatozalez.wirelessredstone.Utils.U_Device;
import me.zatozalez.wirelessredstone.Utils.U_Log;
import me.zatozalez.wirelessredstone.Versions.V_Manager;
import me.zatozalez.wirelessredstone.WirelessRedstone;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class R_Item {
    public ItemStack itemStack;
    public Material material;
    public String displayname;
    public List<String> lore = new ArrayList<>();;
    public DeviceType itemType;
    public ShapedRecipe recipe;
    public int customModelData;
    public String secretCode;
    public boolean valid = false;

    public R_Item(DeviceType itemType){
        this.itemType = itemType;
        initialize(itemType);
        Bukkit.addRecipe(recipe);
    }

    public void initialize(DeviceType itemType) {
        if(V_Manager.getItemStack() == null || V_Manager.getItemStack().length != 2){
            WirelessRedstone.Log(new U_Log(U_Log.LogType.ERROR, "Invalid materials for " + itemType.toString() + ". Disabling WirelessRedstone."));
            Bukkit.getPluginManager().disablePlugin(WirelessRedstone.getPlugin());
            return;
        }

        if(C_Value.getSenderItemName() == null)
            C_Utility.addData("SenderItemName", U_Device.getDeviceName(DeviceType.RedstoneSender));
        if(C_Value.getReceiverItemName() == null)
            C_Utility.addData("ReceiverItemName", U_Device.getDeviceName(DeviceType.RedstoneReceiver));

        set();
        createItemStack();
        createRecipe();
        valid = true;
    }

    private void set(){
        String value;
        if(itemType.equals(DeviceType.RedstoneSender))
            value = C_Value.getSenderBlockType();
        else
            value = C_Value.getReceiverBlockType();

        if(value != null)
        {
            if(value.contains(":"))
                value = value.split(":")[1];

            try {
                ItemStack i = new ItemStack(getMaterial(value), 1, (short) getData(value));
                if (i.getType().isBlock()){
                    itemStack = i;
                    return;
                }
            }catch (Exception ignored) { }
        }

        if(itemType.equals(DeviceType.RedstoneSender))
            itemStack = V_Manager.getItemStack()[0];
        else
            itemStack = V_Manager.getItemStack()[1];
        String key = itemStack.getType().toString().toLowerCase();
        int data = getData(itemStack.getData().toString());
        if (data != 0)
            key += " (" + data + ")";

        if(itemType.equals(DeviceType.RedstoneSender)) {
            if (C_Utility.hasData("SenderBlockType"))
                C_Utility.getData("SenderBlockType").setValue(key);
        }
        else {
            if (C_Utility.hasData("ReceiverBlockType"))
                C_Utility.getData("ReceiverBlockType").setValue(key);
        }
        C_Utility.save();
        if(!value.equals("null")){
            if(itemType.equals(DeviceType.RedstoneSender))
                WirelessRedstone.Log(new U_Log(U_Log.LogType.WARNING, "Invalid configuration for SenderBlockType. '" + value + "' is not a solid block. Block type has been set to '" + key + "'."));
            else
                WirelessRedstone.Log(new U_Log(U_Log.LogType.WARNING, "Invalid configuration for ReceiverBlockType. '" + value + "' is not a solid block. Block type has been set to '" + key + "'."));
        }
    }

    private void createItemStack(){
        ItemMeta meta = itemStack.getItemMeta();
        material = itemStack.getType();

        if(meta == null)
            return;

        if(C_Value.customItemsEnabled()){
            if(itemType.equals(DeviceType.RedstoneSender)){
                meta.setCustomModelData(C_Value.getCustomModelDataSender());
            }
            else{
                meta.setCustomModelData(C_Value.getCustomModelDataReceiver());
            }
        }
        if(itemType.equals(DeviceType.RedstoneSender)) {
            secretCode = " &0&k &8&o &7&l &r ";
            displayname = ChatColor.WHITE + "" + U_Device.getDeviceNameWithColor(itemType);
            lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', C_Value.getSenderItemLore()) + ChatColor.translateAlternateColorCodes('&', secretCode));
        }
        else
        {
            secretCode = " &7&l &8&o &0&k &r ";
            displayname = ChatColor.WHITE + "" + U_Device.getDeviceNameWithColor(itemType);
            lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', C_Value.getReceiverItemLore()) + ChatColor.translateAlternateColorCodes('&', secretCode));
        }

        meta.setDisplayName(displayname);
        meta.setLore(lore);

        itemStack.setItemMeta(meta);
    }

    public void createRecipe(){
        NamespacedKey key = new NamespacedKey(WirelessRedstone.getPlugin(), itemType.toString().toLowerCase().replace("redstone", "redstone_"));
        ShapedRecipe recipe = new ShapedRecipe(key, itemStack);
        if(C_Value.allowRecipes()) {
            recipe.shape("RRR", "RXR", "RBR");

            recipe.setIngredient('R', Material.REDSTONE);
            if (itemType.equals(DeviceType.RedstoneSender))
                recipe.setIngredient('X', Material.REDSTONE_TORCH);
            else
                recipe.setIngredient('X', Material.OBSERVER);
            recipe.setIngredient('B', Material.REDSTONE_BLOCK);
        }
        this.recipe = recipe;
    }

    public void removeRecipe(){
        Bukkit.removeRecipe(recipe.getKey());
    }

    private int getData(String string){
        String data = string;
        if(!data.contains("(") && !data.contains(")"))
            return 0;
        data = data.substring(data.indexOf("(") + 1);
        data = data.substring(0, data.indexOf(")"));
        return U_Converter.getIntFromString(data.trim());
    }

    private Material getMaterial(String string){
        String data = string;
        if(data.contains("("))
            data = data.split("[(]")[0];
        return Material.valueOf(data.trim().toUpperCase());
    }
}
