package fun.tbcraft.play;

import me.devtec.theapi.TheAPI;
import org.bukkit.entity.Player;

public class DisplayUtil{
    private String t = "";
    private String r = "";

    public DisplayUtil(){
        this.t = TBCPlugin.getConfiguration().getString("Display.Top");
        this.r = TBCPlugin.getConfiguration().getString("Display.Lower");
    }

    public void send (Player p) {
        TheAPI.sendTitle(p ,t,r);
        TBCPlugin.debug("Testing Hologram");
    }
}
