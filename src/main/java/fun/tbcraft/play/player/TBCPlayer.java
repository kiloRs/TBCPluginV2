package fun.tbcraft.play.player;

import eu.asangarin.tt.player.PlayerData;
import fun.tbcraft.play.TBCPlugin;
import fun.tbcraft.play.utils.BodyPartsAPI;
import io.lumine.mythic.lib.api.player.MMOPlayerData;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.utils.datakeeper.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TBCPlayer{
    private Player storedPlayer;
    private MMOPlayerData mmoPlayerData;
    private PlayerData ttPlayerData;
    private net.Indyuce.mmocore.api.player.PlayerData corePlayerData;
    private net.Indyuce.mmoitems.api.player.PlayerData itemsPlayerData;
    private User databasePlayer;
    private boolean fullyLoaded = false;

    public TBCPlayer(UUID id){
        if ( Bukkit.getPlayer(id)==null ){
            TBCPlugin.callEmergency("Bad UUID for TBCPlayer");
            return;
        }
        this.storedPlayer = Bukkit.getPlayer(id);
        this.mmoPlayerData = MMOPlayerData.get(id);
        this.ttPlayerData = PlayerData.get(this.storedPlayer);
        this.corePlayerData = net.Indyuce.mmocore.api.player.PlayerData.get(id);
        this.itemsPlayerData = net.Indyuce.mmoitems.api.player.PlayerData.get(id);
        this.databasePlayer = TheAPI.getUser(id);
        this.fullyLoaded = true;
    }

    public User getDatabasePlayer ( ) {
        return databasePlayer;
    }

    public net.Indyuce.mmoitems.api.player.PlayerData getItemsPlayerData ( ) {
        return itemsPlayerData;
    }

    public net.Indyuce.mmocore.api.player.PlayerData getCorePlayerData ( ) {
        return corePlayerData;
    }

    public PlayerData getTtPlayerData ( ) {
        return ttPlayerData;
    }

    public MMOPlayerData getMmoPlayerData ( ) {
        return mmoPlayerData;
    }

    public boolean isOperatorStatus(){
        return storedPlayer.isOp()||storedPlayer.hasPermission("Operator");
    }
    public BodyPartsAPI.BodyPart getBodyPart(Location location){
        return BodyPartsAPI.locationToBodyPart(corePlayerData.getPlayer() , location);
    }

    public boolean isFullyLoaded ( ) {
        return fullyLoaded;
    }
}
