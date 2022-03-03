package fun.tbcraft.play.utils.log;

import fun.tbcraft.play.TBCPlugin;
import fun.tbcraft.play.utils.Coloring;

import java.util.logging.Logger;

public class TBCLogger{
    public static final Logger logger = Logger.getLogger("Minecraft");
    private static final int debugLevel = TBCPlugin.getConfiguration().exists("Debug.Level")?TBCPlugin.getConfiguration().getInt("Debug.Level"):0;
    private static final TBCLogger tbc = new TBCLogger();
    private static String prefix = "[TBCPlugin] &e";

    public TBCLogger(){
        this("[TBCPlugin] &e");
    }
    public TBCLogger(String prefix){
        TBCLogger.prefix = prefix;
    }
    public static void logMessage(String message){
        tbc.log(message,LogType.LOW);
    }
    public static void logError(String error){
        tbc.log(error,LogType.HIGH);
    }
    public static void debugOnly(String n, int num){
        try {
            tbc.log(n,LogType.DEBUG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void log(String text, LogType logType){
        if ( logType==LogType.DEBUG ){
            if ( !TBCPlugin.getConfiguration().exists("Debug.Level") ){
                TBCPlugin.getConfiguration().addDefault("Debug.Level",3);
                TBCPlugin.getConfiguration().save();
            }
            else {
                if ( TBCPlugin.getConfiguration().isInteger("Debug.Level") ){
                    if ( TBCPlugin.getConfiguration().getInt("Debug.Level")>0 ){
                        logger.warning(Coloring.get("&c[Debug] "  + text));
                    }
                }
            }
        }
        if ( logType==LogType.HIGH ){
            logger.severe(prefix + text);
        }
        else if ( logType==LogType.MEDIUM ){
            logger.warning(prefix + text);
        }
    }
    enum LogType{
        HIGH,MEDIUM,LOW,DEBUG;
    }
}
