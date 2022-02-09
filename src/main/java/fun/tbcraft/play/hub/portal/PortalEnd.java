package fun.tbcraft.play.hub.portal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bukkit.Location;

public class PortalEnd{
    protected Location location;

    public PortalEnd(Location location){
        this.location = location;
    }

    public Location getLocation ( ) {
        return this.location;
    }

    public int getX(){
        return location.getBlockX();
    }
    public int getY(){
        return location.getBlockY();
    }
    public int getZ(){
        return location.getBlockZ();
    }

    @Override
    public boolean equals (Object o) {
        if ( this == o ) return true;

        if ( !( o instanceof PortalEnd ) ) return false;

        PortalEnd portalEnd = (PortalEnd) o;

        final var equalsBuilder = new EqualsBuilder();
        if ( !portalEnd.location.getWorld().getName().equalsIgnoreCase(this.location.getWorld().getName()) ){
            return false;
        }
        return equalsBuilder.append(getX(), portalEnd.getLocation().getBlockX()).append(getY(),portalEnd.location.getBlockY()).append(getZ(),portalEnd.location.getBlockZ()).isEquals();
    }

    @Override
    public int hashCode ( ) {
        return new HashCodeBuilder(17 , 37).append(getLocation()).toHashCode();
    }
}
