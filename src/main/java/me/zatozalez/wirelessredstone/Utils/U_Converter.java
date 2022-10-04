package me.zatozalez.wirelessredstone.Utils;

public class U_Converter {
    public static int getIntFromString(String input){
        try {
            int i = Integer.parseInt(input);
            return i;
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double getDoubleFromString(String input){
        try {
            double d = Double.parseDouble(input);
            return d;
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
            boolean b = Boolean.parseBoolean(input);
            return b;
        }
        catch (Exception e) {
            return catchOutput;
        }
    }
}
