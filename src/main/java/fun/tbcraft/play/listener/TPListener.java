package fun.tbcraft.play.listener;

import com.github.unldenis.hologram.Hologram;
import com.github.unldenis.hologram.HologramPool;
import com.github.unldenis.hologram.animation.AnimationType;
import fun.tbcraft.play.TBCPlugin;
import fun.tbcraft.play.player.TBCPlayer;
import fun.tbcraft.play.utils.ColoredWords;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabFeature;
import me.neznamy.tab.api.chat.ChatClickable;
import me.neznamy.tab.api.placeholder.PlaceholderManager;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.event.PlayerResourceUpdateEvent;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TPListener implements BaseListener{

    private Player player;
    private TBCPlayer tbcPlayer;
    private double multiplier;
    private double max;
    private double stellium;

    @EventHandler(priority = EventPriority.HIGH)
    public void onTeleport(PlayerTeleportEvent e){
        this.player = e.getPlayer();
        this.tbcPlayer = TBCPlayer.get(player);
        final var from = e.getFrom();
        final var to = e.getTo();

        final var allWaypoints = MMOCore.plugin.waypointManager.getAll();
        final var wayPoints = MMOCore.plugin.waypointManager.getAll();

        final var h = Hologram.builder().build(new HologramPool(TBCPlugin.getPlugin() , 20));

        if ( !tbcPlayer.isFullyLoaded() ){
            TBCPlugin.errorLog("Player: " + player.getName() + " is not fully loaded!");
            e.setCancelled(true);
            return;
        }
        final var distance = from.distanceSquared(to);

        final var basePath = "Teleportation.Distance.Base";
        var usedStellium = distance * ( TBCPlugin.getSettings().exists(basePath)?TBCPlugin.getSettings().getDouble(basePath):1);

        final var data = PlayerData.get(player);

        var name = player.getName();

        if ( name==null||name.isEmpty() ){
            name = player.getDisplayName();
        }
        final var classTypeHolder = tbcPlayer.getPlayerClassType();
        usedStellium = usedStellium * classTypeHolder.getStelliumMultiplier();

        String errrorWording = "";
        if ( data.getStellium() < usedStellium ){
            e.setCancelled(true);
            TBCPlugin.errorLog("Player: " + name);
            errrorWording = "&cYou cannot teleport because you only have " + data.getStellium() + "/" + usedStellium;
            player.sendRawMessage(ColoredWords.get(errrorWording));
        }
        else {
            errrorWording = "&aSuccessfully Teleported!";
            data.giveStellium(-usedStellium, PlayerResourceUpdateEvent.UpdateReason.OTHER);
            TBCPlugin.log("Removed Stellium from " + name + " as amount: " + usedStellium);
            player.sendRawMessage(ColoredWords.get(errrorWording));
        }

        h.setAnimation(0, AnimationType.CIRCLE);
        h.setLine(1,"");
        h.setLine(2,"Teleportation System");
        final var location = h.getLocation();

        final var t = TabAPI.getInstance();

        t.getPlaceholderManager().
    }
}
