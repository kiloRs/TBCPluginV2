package fun.tbcraft.play;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserReader{
    private static final Map<String, UUID> playerStored = new HashMap<>();

    public UserReader(){

    }
    public void addPlayer(Player player){
        final var add = playerStored.put(player.getName() , player.getUniqueId());

        if ( add == null ){
            throw new RuntimeException("Bad Player Loaded in UserReader");
        }

        TBCPlugin.log("Loading... " + player.getName() + " with " + player.getUniqueId());
    }
    public UUID getPlayer(Player player){
        return playerStored.get(player.getName());
    }

    public boolean isSavedAlready(Player player){
        return playerStored.containsKey(player.getName())||playerStored.containsValue(player.getUniqueId());
    }
    public boolean hasNameSaved(Player player){
        return playerStored.containsKey(player.getName());
    }
    public boolean hasUUIDSaved(Player player){
        return playerStored.containsValue(player.getUniqueId());
    }
    public void clear(){
        playerStored.clear();
    }

}
