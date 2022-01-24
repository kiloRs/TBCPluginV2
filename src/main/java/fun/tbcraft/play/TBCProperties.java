package fun.tbcraft.play;

import org.bukkit.entity.Player;

import java.util.Properties;

public class TBCProperties{
    private static final Properties properties = System.getProperties();

    public static String resourcePack = properties.getProperty("resource-pack","");
    public static String port = properties.getProperty("server-port","25565");
    public static String resourceHash = properties.getProperty("resource-pack-hash","");
    public static String serverIP = properties.getProperty("server-ip","0.0.0.0");
    public static String difficulty = properties.getProperty("difficulty","");


    public static boolean hasPack(Player p){
        return !resourcePack.isEmpty();
    }
}
