package fun.tbcraft.play.listener;

import com.bergerkiller.bukkit.common.utils.PlayerUtil;
import fun.tbcraft.play.DisplayUtil;
import fun.tbcraft.play.TBCPlugin;
import fun.tbcraft.play.player.TBCPlayer;
import fun.tbcraft.play.utils.TBCProperties;
import fun.tbcraft.play.utils.TBCTimeHandler;
import fun.tbcraft.play.utils.ColorWords;
import io.lumine.mythic.utils.chat.ColorString;
import net.Indyuce.mmocore.api.ConfigFile;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.query.QueryOptions;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;
import org.mozilla.javascript.ast.SwitchCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class StartupListener implements BaseListener{
    private static String ovv = "default";
    private static List<String> acceptableWorlds = TBCPlugin.getConfiguration().getStringList("Worlds");

    @EventHandler
    public void on(PlayerJoinEvent event){
        final var player = event.getPlayer();

        final var world = player.getWorld();

        final var tbcPlayer = TBCPlayer.get(player);
        if ( acceptableWorlds.contains(world.getName()) || player.isOp()){
            new DisplayUtil().send(player);
        }

        final var configFile = new ConfigFile(tbcPlayer.getStoredPlayer());
        if ( configFile.exists() ){
            final var config = configFile.getConfig();

            if ( config.contains("hub") ) {

            }
        }
    }
    @Override
    public String getName ( ) {
        return "";
    }
}