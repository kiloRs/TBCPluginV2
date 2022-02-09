package fun.tbcraft.play.hub.portal;

import fun.tbcraft.play.hub.HubWorld;
import org.bukkit.Location;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class PortalWorld{
    private final HubWorld hubWorld;
    private String name = "";
    public Map<PortalEnter,PortalEnd> map = new HashMap<>();

    public PortalWorld(HubWorld hubWorld){
        this.hubWorld = hubWorld;
        this.name = hubWorld.getId();
    }
    public void add(Location origin, Location to){
        map.put(new PortalEnter(origin),new PortalEnd(to));
    }
    public void remove(Location origin){
        map.remove(origin);
    }
    @Nullable
    public PortalEnd getFinalLocation(Location origin){
        return map.getOrDefault(new PortalEnter(origin),null);
    }

    public HubWorld getHubWorld ( ) {
        return hubWorld;
    }

}
