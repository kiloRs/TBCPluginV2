package fun.tbcraft.play.player;

import fun.tbcraft.play.TBCPlugin;
import io.lumine.mythic.lib.api.player.MMOPlayerData;
import io.lumine.mythic.lib.api.util.EnumUtils;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.utils.datakeeper.User;
import net.Indyuce.mmocore.api.ConfigFile;
import net.Indyuce.mmocore.api.player.profess.PlayerClass;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TBCPlayer{
    private Player storedPlayer;
    private MMOPlayerData mmoPlayerData;
    private net.Indyuce.mmocore.api.player.PlayerData corePlayerData;
    private net.Indyuce.mmoitems.api.player.PlayerData itemsPlayerData;
    private ConfigFile usedFile = null;
    private User databasePlayer;
    private boolean fullyLoaded = false;
    private PlayerClassType classType;
    private FileConfiguration fileConfiguration = null;
    public static TBCPlayer get(UUID uuid){
        return new TBCPlayer(uuid);
    }
    public static TBCPlayer get(Player p){
        return new TBCPlayer(p.getUniqueId());
    }
    public TBCPlayer(UUID id){
        if ( Bukkit.getPlayer(id)==null ){
            TBCPlugin.callEmergency("Bad UUID for TBCPlayer");
            return;
        }
        if ( fullyLoaded ){
            TBCPlugin.log("Player Already Loaded: " + Bukkit.getPlayer(id));
        }this.storedPlayer = Bukkit.getPlayer(id);
        this.mmoPlayerData = MMOPlayerData.get(id);
        this.corePlayerData = net.Indyuce.mmocore.api.player.PlayerData.get(id);
        this.itemsPlayerData = net.Indyuce.mmoitems.api.player.PlayerData.get(id);
        this.databasePlayer = TheAPI.getUser(id);
        this.fullyLoaded = true;
        this.classType = EnumUtils.getIfPresent(PlayerClassType.class , corePlayerData.getProfess().getId()).orElseThrow(( ) -> new RuntimeException("Bad Class Type Data"));


        if ( new ConfigFile(storedPlayer).exists() ){
            this.usedFile = new ConfigFile(storedPlayer);
            this.fileConfiguration = usedFile.getConfig();
        }
    }

    public void setFullyLoaded(boolean l){
        this.fullyLoaded=l;
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

    public MMOPlayerData getMmoPlayerData ( ) {
        return mmoPlayerData;
    }

    public PlayerClass getPlayerClass(){
        return classType.get();
    }
    public PlayerClassType getPlayerClassType(){
        return classType;
    }

    public boolean isFullyLoaded ( ) {
        return fullyLoaded;
    }

     public Player getStoredPlayer(){
        return storedPlayer;
     }
}
