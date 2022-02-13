package fun.tbcraft.play.utils;

import fun.tbcraft.play.TBCPlugin;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.configapi.Config;
import org.bukkit.entity.Player;

public class DisplayUtil{
    private String t = "Default Top";
    private String r = "Default Lower";
    private Config c;

    public DisplayUtil(){
        this("Display.Upper","Display.Lower");
    }
    public DisplayUtil(String topPath, String bottomPath){
        c = TBCPlugin.getConfiguration();
        if ( isLoading(topPath , bottomPath) ) {
            TBCPlugin.log("Loaded Display Util Paths!");
        }
        this.t = c.exists(topPath)? c.getString(topPath):t;
        this.r = c.exists(bottomPath)? c.getString(bottomPath):r;
    }

    private boolean isLoading (String topPath , String bottomPath) {
        int saveCheck = 0;
        if ( !c.exists(topPath) ){
            c.addDefault(topPath ,t);
            ++saveCheck;
        }
        if ( !c.exists(bottomPath) ){
            c.addDefault(bottomPath ,r);
            ++saveCheck;
        }

        if ( saveCheck >0 ){
            c.save();
            return true;
        }
        return false;
    }

    public void send (Player p) {
        TheAPI.sendTitle(p ,t,r);
        TBCPlugin.debug("Testing Hologram");
    }
}
