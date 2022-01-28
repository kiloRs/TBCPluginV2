package fun.tbcraft.play.player;

import fun.tbcraft.play.TBCPlugin;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Locale;

public class LoadedDungeonPleyer implements DungeonPlayer{

    private final Player player;
    private TBCPlayer tbcPlayer;

    public LoadedDungeonPleyer(Player player){

        this.player = player;
    }

    @Override
    public boolean isInDungeon ( ) {
        tbcPlayer = TBCPlayer.get(player);
        return tbcPlayer.inDungeon();
    }

    @Override
    public void setInDungeon ( String name) {
        final var u = tbcPlayer.getDatabasePlayer();
        u.setAndSave("Dungeon" , name);
        tbcPlayer.getMmoPlayerData().getPlayer().setMetadata("Dungeon",new FixedMetadataValue(TBCPlugin.getPlugin(),name));

        TBCPlugin.log("Set Player " + tbcPlayer.getMmoPlayerData().getPlayer().getName() + " into " + name.toUpperCase(Locale.ROOT));
    }

    @Override
    public Player get ( ) {
        return null;
    }
}
