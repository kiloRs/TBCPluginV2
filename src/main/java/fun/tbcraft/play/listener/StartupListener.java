package fun.tbcraft.play.listener;

import fun.tbcraft.play.TBCPlugin;
import fun.tbcraft.play.player.TBCPlayer;
import fun.tbcraft.play.utils.DisplayUtil;
import net.Indyuce.mmocore.api.ConfigFile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;


public class StartupListener implements BaseListener{
    private static String ovv = "default";
    private static List<String> acceptableWorlds = TBCPlugin.getConfiguration().getStringList("Worlds");

    @EventHandler
    public void on(PlayerJoinEvent event){
        final var player = event.getPlayer();

    }
    @Override
    public String getName ( ) {
        return "";
    }
}