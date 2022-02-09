package fun.tbcraft.play.hub.portal;

import fun.tbcraft.play.hub.portal.PortalEnd;
import org.bukkit.Location;

public class PortalEnter extends PortalEnd{
    private boolean locked = true;

    public PortalEnter (Location location) {
        super(location);
        this.location = location;
    }

    public Location getLocation(){
        return this.location;
    }

    public boolean isLocked(){
        return locked;
    }
    public void lock(boolean state){
        this.locked = state;
    }
}
