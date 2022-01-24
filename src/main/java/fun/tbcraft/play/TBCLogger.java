package fun.tbcraft.play;

import java.util.logging.Logger;

public class TBCLogger{
    public static final Logger logger = Logger.getLogger("Minecraft");
    private static final int debugLevel = TBCPlugin.getConfiguration().exists("Debug.Level")?TBCPlugin.getConfiguration().getInt("Debug.Level"):0;
    private static final TBCLogger tbc = new TBCLogger();
    private static String prefix = "[TBCPlugin] ";

    public TBCLogger(){
        this("[TBCPlugin] ");
    }
    public TBCLogger(String prefix){
        TBCLogger.prefix = prefix;
    }
    public static void logMessage(String message){
        tbc.log(message,0);
    }
    public static void logError(String error){
        tbc.log(error,3);
    }
    public static void debugOnly(String n, int num){
        try {
            tbc.log(n,num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void log(String text, int level){
        if ( level < 0 ){
            logger.severe("[Debug] " + text);
        }
        if ( level == 0  ){
            logger.info(prefix + text);
            return;
        }
        if ( debugLevel == level && level == 2){
            logger.warning(prefix + text);
        }
        else if ( debugLevel >= level && level == 1 ){
            logger.info(prefix + text);
        }
        else{
            logger.severe(prefix + text);
        }
    }
}
