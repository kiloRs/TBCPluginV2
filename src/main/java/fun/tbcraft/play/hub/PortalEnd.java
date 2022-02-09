package fun.tbcraft.play.hub;

import org.bukkit.Location;

public class PortalEnd{
    protected Location location;

    public PortalEnd(Location location){
        this.location = location;
    }

    public Location getLocation ( ) {
        return this.location;
    }
}
