package fun.tbcraft.play.hub;

import fun.tbcraft.play.TBCPlugin;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class HubManager{
    private final Plugin plugin;
    private List<HubWorld> hubWorlds = new ArrayList<>();
    private boolean loaded = false;

    public HubManager(Plugin plugin){
        this.plugin = plugin;
        final var keys = TBCPlugin.getConfiguration().getKeys("Hub",false);
        loadAll(keys.stream().toList());
    }

    private void loadAll (List<String> keys) {

        for(String worldName : keys) {
            final var world = new HubWorld(worldName);

            if ( world.getHub() == null ){
                continue;
            }
            hubWorlds.add(world);
            TBCPlugin.log("Added Hub: " + worldName);
        }

        loaded = true;

    }

    public List<HubWorld> getHubs(){
        return hubWorlds;
    }


}
