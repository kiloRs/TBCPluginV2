package fun.tbcraft.play.hub;

import org.bukkit.Location;

public class PortalEnter extends PortalEnd{


    public PortalEnter (Location location) {
        super(location);
        this.location = location;
    }

    public Location getLocation(){
        return this.location;
    }
}
