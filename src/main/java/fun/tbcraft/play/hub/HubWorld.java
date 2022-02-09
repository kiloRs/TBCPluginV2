package fun.tbcraft.play.hub;

import fun.tbcraft.play.TBCPlugin;
import fun.tbcraft.play.hub.portal.PortalWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class HubWorld{
    private World hub = null;
    private String id;
    private boolean inventoryLocked = true;
    private boolean pvp = false;
    private boolean pve = false;
    private PortalWorld portalWorld = new PortalWorld(this);

    public HubWorld(String id){
        this.hub = Bukkit.getWorld(id);
        this.id = id;
        this.inventoryLocked = TBCPlugin.getConfiguration().getBoolean("Hub." + id + ".Inventory.Locked");
        this.pve = TBCPlugin.getConfiguration().getBoolean("Hub." + id + ".PVE");
        this.pvp = TBCPlugin.getConfiguration().getBoolean("Hub." + id + ".PVP");
        if ( this.hub == null ){
            throw new RuntimeException("No World:  " + this.id);
        }
    }


    public boolean isInventoryLocked ( ) {
        return inventoryLocked;
    }

    public boolean hasPVP ( ) {
        return pvp;
    }
    public boolean hasPVE(){
        return pve;
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
