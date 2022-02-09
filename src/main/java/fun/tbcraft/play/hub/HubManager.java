package fun.tbcraft.play.hub;

import fun.tbcraft.play.TBCPlugin;
import jdk.tools.jlink.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HubManager{
    private final Plugin plugin;
    private List<HubWorld> hubWorlds;
    public HubManager(Plugin plugin){
        this.plugin = plugin;

        List<String> worldNames = TBCPlugin.getConfiguration().getStringList("Hub.Worlds");
        hubWorlds = new ArrayList<>();

        for(String worldName : worldNames) {
            hubWorlds.add(new HubWorld(worldName));
        }

        for(HubWorld hubWorld : hubWorlds) {
            TBCPlugin.log("Loaded Hub World: " + hubWorld.getId());
        }
    }
    public List<HubWorld> getHubs(){
        return hubWorlds;
    }
}
