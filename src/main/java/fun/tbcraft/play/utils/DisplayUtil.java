package fun.tbcraft.play.utils;

import fun.tbcraft.play.TBCPlugin;
import me.devtec.theapi.TheAPI;
import org.bukkit.entity.Player;

public class DisplayUtil{
    private String t = "Default Top";
    private String r = "Default Lower";

    public DisplayUtil(){
        this("Display.Upper","Display.Lower");
    }
    public DisplayUtil(String topPath, String bottomPath){
        this.t = TBCPlugin.getConfiguration().exists(topPath)?TBCPlugin.getConfiguration().getString(topPath):t;
        this.r = TBCPlugin.getConfiguration().exists(bottomPath)?TBCPlugin.getConfiguration().getString(bottomPath):r;
    }

    public void send (Player p) {
        TheAPI.sendTitle(p ,t,r);
        TBCPlugin.debug("Testing Hologram");
    }
}
