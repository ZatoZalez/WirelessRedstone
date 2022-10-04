package me.zatozalez.wirelessredstone.Utils;

public class U_Log {

    public enum LogType{
        DEFAULT,
        INFORMATION,
        WARNING,
        ERROR
    }

    public LogType logType;
    public String message;


    public U_Log(LogType logType, String message){
        this.logType = logType;
        this.message = message;
    }
}
