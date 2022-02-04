package fun.tbcraft.play.listener;

import com.bergerkiller.bukkit.common.utils.PlayerUtil;
import fun.tbcraft.play.DisplayUtil;
import fun.tbcraft.play.TBCPlugin;
import fun.tbcraft.play.utils.TBCProperties;
import fun.tbcraft.play.utils.TBCTimeHandler;
import fun.tbcraft.play.utils.ColorWords;
import io.lumine.mythic.utils.chat.ColorString;
import net.Indyuce.mmocore.api.ConfigFile;
import org.apache.commons.lang3.Validate;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;


public class StartupListener implements BaseListener{
    private static final List<Player> onlinePlayers = new ArrayList<>();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        final var p = e.getPlayer();
        ConfigFile configFile = new ConfigFile(p);

        if ( configFile.exists() ){
            Validate.notNull(configFile.getConfig(),"Bad User Config for " + p.getName());
            configFile.getConfig().addDefault("Login.Time", TBCTimeHandler.getTimeString(System.currentTimeMillis()));
            if (! configFile.getConfig().isConfigurationSection("TBC") ) {
                final var c = configFile.getConfig();
                c.addDefault("TBC.UUID", p.getUniqueId().toString());
                final var value = 1;
                if ( !configFile.getConfig().isInt("TBC.Times.Joined") ){
                    c.addDefault("TBC.Times.Joined", value);
                }
                else {
                    final var actual = configFile.getConfig().getInt("TBC.Times.Joined");
                    c.set("TBC.Times.Joined",actual + 1);
                }
                configFile.save();
            }
            TBCPlugin.debug("Loaded Config For " + p.getName());
        }
        if ( !onlinePlayers.contains(p) ){
            onlinePlayers.add(p);
        }
        else {
            TBCPlugin.debug("Player Already Found in Database!  [" + p.getName() + "]");
        }

        final var displayUtil = new DisplayUtil();
        displayUtil.send(p);

    }
    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        final var p = e.getPlayer();
        onlinePlayers.remove(p);
        TBCPlugin.debug("Removed " + p.getName() + " from Online");

    }
    @EventHandler
    public void on(PlayerGameModeChangeEvent e){
        final var newGameMode = e.getNewGameMode();

        if ( newGameMode==GameMode.CREATIVE ){
            final var player = e.getPlayer();
            if ( onlinePlayers.contains(player) ){
                if ( !player.hasPermission("creative.mode") ){
                    e.setCancelled(true);
                }
                return;
            }
            throw new RuntimeException("Unloaded Playerdata...");
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLimeResourcePack(PlayerInteractEvent e) {
        final var player = e.getPlayer();
        if ( player.hasResourcePack() ){
            player.sendRawMessage(ColorString.get("&cYou already have the resource pack!"));
            return;
        }

        if ( !player.isOnline() || PlayerUtil.isDisconnected(player) ) {
            TBCPlugin.errorLog("Player Offline! [" + player.getName() + "]");
            e.setCancelled(true);
        }
        if ( player.isOp() ) {
            if ( player.getGameMode() == GameMode.SPECTATOR ) {
                player.sendRawMessage(ColorWords.get("&4Bad Gamemode"));
                return;
            }
            if ( player.getGameMode() == GameMode.CREATIVE ) {
                player.sendRawMessage(ColorWords.get("&4Bad Gamemode"));
                return;
            }
            if ( e.hasBlock() ) {
                if ( e.getBlockFace() != BlockFace.UP ) {
                    return;
                }
                final var clickedBlock = e.getClickedBlock();
                if ( clickedBlock == null ) {
                    return;
                }
                if ( clickedBlock.getType() == Material.LIME_CONCRETE_POWDER ) {
                    player.setResourcePack(TBCProperties.resourcePack);
                    player.sendRawMessage(ColorString.get("&aAdding Resource Pack"));
                    return;
                }
                if ( clickedBlock.getType()== Material.ENCHANTING_TABLE ) {
                    e.setCancelled(true);
                    player.sendRawMessage(ColorWords.get("&cYou cannot enchant yet."));
                }
            }
            ;
        }
    }


    @Override
    public String getName ( ) {
        return "STARTUP";
    }
}
