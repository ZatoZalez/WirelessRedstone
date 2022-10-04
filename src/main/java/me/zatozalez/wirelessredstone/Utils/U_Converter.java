package me.zatozalez.wirelessredstone.Utils;

public class U_Converter {
    public static int getIntFromString(String input){
        try {
            return Integer.parseInt(input);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double getDoubleFromString(String input){
        try {
            return Double.parseDouble(input);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    public static boolean getBoolFromString(String input){
        return getBoolFromString(input, false);
    }

    public static boolean getBoolFromString(String input, boolean catchOutput){
        try {
            return Boolean.parseBoolean(input);
        }
        catch (Exception e) {
            return catchOutput;
        }
    }
}
