package fun.tbcraft.play.hooks.techtree;

import eu.asangarin.tt.data.TechTree;
import eu.asangarin.tt.player.PlayerData;
import io.lumine.mythic.utils.chat.ColorString;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class EntryHolder{
    private TechTree tt;
    private String entryToLookFor = "";

    public EntryHolder (@Nullable String entry){
        entryToLookFor = entry==null?"example":entry;

    }
    public void setEntry(String newEntry){
        entryToLookFor =  newEntry!=null?newEntry:"example";

        if ( entryToLookFor.equalsIgnoreCase("example") ){
            return;
        }
        Validate.notNull(entryToLookFor,"Null Tech");
        Validate.notBlank(entryToLookFor,"Entry Cannot Be Blank!");


    }

    boolean has(Player player){
        final var playerData = PlayerData.get(player);
        Validate.notNull(entryToLookFor,"Null Tech");
        Validate.notBlank(entryToLookFor,"Entry Cannot Be Blank!");
        return playerData.hasEntry(entryToLookFor)&& !entryToLookFor.equalsIgnoreCase("example");
    }
    void addTech (Player player) {
        PlayerData playerData = PlayerData.get(player);
        if (! playerData.hasEntry(entryToLookFor) ) {
            playerData.addEntry(entryToLookFor);
            playerData.save();

            if ( player.isOp() && player.isOnline() ){
                player.sendRawMessage(ColorString.get("&l&bAdded " + entryToLookFor + " to TechTree!"));
            }
        }
    }

    void removeTech(Player player){
        PlayerData playerData = PlayerData.get(player);

        if ( playerData.hasEntry(entryToLookFor) ){
            playerData.clear(entryToLookFor);
        }
    }
}
