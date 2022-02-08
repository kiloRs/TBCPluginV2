package fun.tbcraft.play.listener;

import com.bergerkiller.bukkit.common.utils.PlayerUtil;
import fun.tbcraft.play.utils.DisplayUtil;
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

    public void onJoin(PlayerJoinEvent e){
        final var p = e.getPlayer();

        DisplayUtil util = new DisplayUtil();

        util.send(p);
    }
    @Override
    public String getName ( ) {
        return "STARTUP";
    }
}
