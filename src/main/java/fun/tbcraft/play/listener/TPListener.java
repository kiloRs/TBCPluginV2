package fun.tbcraft.play.listener;

import fun.tbcraft.play.TBCPlugin;
import fun.tbcraft.play.player.TBCPlayer;
import fun.tbcraft.play.utils.PlayerClassType;
import net.Indyuce.mmocore.api.event.PlayerResourceUpdateEvent;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TPListener implements BaseListener{

    private Player player;
    private TBCPlayer tbcPlayer;
    private double multiplier;
    private double max;
    private double stellium;

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e){
        this.player = e.getPlayer();
        this.tbcPlayer = TBCPlayer.get(player);
        final var from = e.getFrom();
        final var to = e.getTo();

        final var distance = from.distanceSquared(to);

        final var usedStellium = ( distance * multiplier ) / max;

        final var data = PlayerData.get(player);

        var name = player.getName();

        if ( name==null||name.isEmpty() ){
            name = player.getDisplayName();
        }
        if ( tbcPlayer.getPlayerClassType()== PlayerClassType.MAGE ){

        }

        if ( data.getStellium() < usedStellium ){
            e.setCancelled(true);
            TBCPlugin.errorLog("Player: " + name);
        }
        else {
            data.giveStellium(-usedStellium, PlayerResourceUpdateEvent.UpdateReason.OTHER);
            TBCPlugin.log("Removed Stellium from " + name + " as amount: " + usedStellium);
        }

    }
}
