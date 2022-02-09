package fun.tbcraft.play.hub;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HubWorld{
    private World hub = null;
    private boolean inventoryLocked = true;
    private boolean pvp = false;
    private boolean pve = false;
    private PortalWorld portalWorld = new PortalWorld(this);

    public HubWorld(String id){
        this(id,true,true,false);
    }
    public HubWorld(String id, boolean inv, boolean p, boolean e){
        this.hub = Bukkit.getWorld(id);
        this.inventoryLocked = inv;
        this.pve = e;
        this.pvp = p;

        if ( this.hub == null ){
            return;
        }
    }


    public String getId(){
        return hub.getName();
    }
    public World getHub ( ) {
        return hub;
    }

    public PortalWorld getPortalWorld ( ) {
        return portalWorld;
    }
}
