package fun.tbcraft.play.hooks;

import fun.tbcraft.play.TBCPlugin;
import me.clip.placeholderapi.PlaceholderAPI;
import me.devtec.theapi.configapi.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;

public class CommandControl{
    private final Config settings;
    private List<String> args = List.of(new String[]{});

    public CommandControl(){
        this.settings = TBCPlugin.getSettings();
        this.args = settings.getStringList("Join.Commands");
    }

    public boolean show(Player player){
        int x = 0;
        int total = args.size();
        for(String command:args){
            if ( command.startsWith("/") || command.startsWith(".") ) {
                command = command.substring(1 , command.length());

                if ( PlaceholderAPI.containsPlaceholders(command) ) {
                    final var parsed = PlaceholderAPI.setPlaceholders(player , command);
                    final var common = Bukkit.dispatchCommand(player , parsed);

                    if ( common ) {
                        if ( Bukkit.dispatchCommand(player , command) ) {
                            TBCPlugin.log("Command Ran: " + x);
                        }
                    }
                    x++;
                }
            }
            else {
                Bukkit.dispatchCommand(player,command);
                x++;
            }

            if ( total <= x  ){
                return true;
            }
        }
        return false;
    }
}
