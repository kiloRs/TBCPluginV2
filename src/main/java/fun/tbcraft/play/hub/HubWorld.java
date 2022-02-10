package fun.tbcraft.play.hub;

import fun.tbcraft.play.TBCPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class HubWorld{
    private World hub = null;
    private final String id;
    private boolean inventoryLocked = true;
    private boolean pvp = false;
    private boolean pve = false;


    public HubWorld (String id) {
        this.hub = Bukkit.getWorld(id);
        this.id = id;
        final var c = TBCPlugin.getConfiguration();
        if ( !c.exists("Hub." + id) ){
            final var hubSection = c.getSection("Hub." + id);

            hubSection.set("Inventory.Locked",false);
            hubSection.set("PVE",true);
            hubSection.set("PVP",false);
            c.save();

        }
        this.inventoryLocked = c.getBoolean("Hub." + id + ".Inventory.Locked");
        this.pve = c.getBoolean("Hub." + id + ".PVE");
        this.pvp = c.getBoolean("Hub." + id + ".PVP");
        if ( this.hub == null ) {
            throw new RuntimeException("No World:  " + this.id);
        }
    }


    public boolean isInventoryLocked ( ) {
        return inventoryLocked;
    }

    public boolean hasPVP ( ) {
        return pvp;
    }

    public boolean hasPVE ( ) {
        return pve;
    }

    public String getId ( ) {
        return hub.getName();
    }

    public World getHub ( ) {
        return hub;
    }
}