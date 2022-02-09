package fun.tbcraft.play.hub;

import org.bukkit.Location;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class PortalWorld{
    private final HubWorld hubWorld;
    private String name = "";
    public Map<Location, Location> map = new HashMap<>();

    public PortalWorld(HubWorld hubWorld){
        this.hubWorld = hubWorld;
        this.name = hubWorld.getId();
    }
    public void add(Location origin, Location to){
        map.put(origin,to);
    }
    public void remove(Location origin){
        map.remove(origin);
    }
    @Nullable
    public Location getFinalLocation(Location origin){
        return map.getOrDefault(origin,null);
    }

    @Nullable
    public Location getOriginOrNull(Location v){
        if ( map.containsValue(v) ) {
            var context = new Object(){
                Location def = null;
            };
            map.forEach((location , location2) -> {
                final var oWorld = location2.getWorld();
                final var x = location2.getBlockX();
                final var blockY = location2.getBlockY();
                final var blockZ = location2.getBlockZ();

                if ( v.getBlockX()==x&&v.getBlockZ()==blockZ &&blockY==v.getBlockY()){
                    context.def = location;
                }
            });
            return context.def;
        }
        return null;
    }

    public HubWorld getHubWorld ( ) {
        return hubWorld;
    }
}
