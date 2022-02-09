package fun.tbcraft.play.listener;

import com.bencodez.advancedcore.listeners.AuthMeLogin;
import fr.xephi.authme.api.v3.AuthMeApi;
import fr.xephi.authme.api.v3.AuthMePlayer;
import fr.xephi.authme.events.AuthMeAsyncPreRegisterEvent;
import fr.xephi.authme.mail.EmailService;
import fun.tbcraft.play.TBCPlugin;
import fun.tbcraft.play.utils.DisplayUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import sun.net.www.protocol.mailto.MailToURLConnection;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TitleListener implements BaseListener{

    private @NotNull Player player;
    private static Map<Player,String> emailTo = new HashMap<>();
    private AuthMeApi api;
    public TitleListener ( ){
        api = AuthMeApi.getInstance();

    }
    @Override
    public String getName ( ) {
        return "TITLE";
    }

    @EventHandler
    public void onTitle(PlayerJoinEvent e){
        player = e.getPlayer();
        boolean title = e.getPlayer().isOp()|| !player.hasPlayedBefore();


        if ( title ){
            final var displayUtil = new DisplayUtil();
            displayUtil.send(player);
        }
        if ( title ) {
            final var authMePlayer = api.getPlayerInfo(player.getName());

            final var elseThrow = authMePlayer.orElseThrow(( ) -> new RuntimeException("Bad AuthMe Player"));

            String email;
            if ( elseThrow.getEmail().isEmpty() ){
                email = elseThrow.getEmail().orElse(null);
            }
            else {
                email = elseThrow.getEmail().orElseThrow();
            }
            emailTo.put(player,email);

        }
    }

    @EventHandler
    public void onLog(AuthMeAsyncPreRegisterEvent e){
        final var player = e.getPlayer();

        if (! e.canRegister() ) {
            TBCPlugin.errorLog("Player: " + player.getName() + " cannot register!");
            return;
        }
        final var displayUtil = new DisplayUtil();
        displayUtil.send(player);
        TBCPlugin.log("Pre-registration complete for " + player.getName());

    }
}
