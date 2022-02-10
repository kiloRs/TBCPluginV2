package fun.tbcraft.play.listener;

import fun.tbcraft.play.TBCPlugin;
import fun.tbcraft.play.player.TBCPlayer;
import fun.tbcraft.play.utils.DisplayUtil;
import fun.tbcraft.play.utils.TBCProperties;
import net.Indyuce.mmocore.api.ConfigFile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

import java.security.MessageDigest;
import java.security.MessageDigestSpi;
import java.util.List;


public class StartupListener implements BaseListener{
    private static String ovv = "default";
    private static List<String> acceptableWorlds = TBCPlugin.getConfiguration().getStringList("Worlds");

    @EventHandler
    public void on(PlayerJoinEvent event){
        final var player = event.getPlayer();
    }
    @EventHandler
    public void onResourcePack(PlayerResourcePackStatusEvent e){
        if ( e.getStatus()== PlayerResourcePackStatusEvent.Status.DECLINED ) {
            e.getPlayer().setResourcePack(TBCProperties.resourcePack, "",true);
        }
    }
    @Override
    public String getName ( ) {
        return "";
    }
}