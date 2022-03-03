package fun.tbcraft.play.commands;

import fun.tbcraft.play.TBCPlugin;
import fun.tbcraft.play.utils.Coloring;
import me.clip.placeholderapi.util.TimeUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SystemCommands implements CommandExecutor{

    public SystemCommands(){

    }

    @Override
    public boolean onCommand (CommandSender sender , Command command , String label , String[] args) {
        if ( !( sender instanceof Player player) ){
            TBCPlugin.errorLog("Bad Location");
            return false;
        }
        if ( player.isInsideVehicle() ) {
            player.sendRawMessage(Coloring.get("&4You cannot do this while riding entities or objects!"));
            player.eject();;
            return false;
        }


        if ( is("tbc",label) ) {
            if ( !command.isRegistered() ) {
                TBCPlugin.log("This command " + label.toUpperCase(Locale.ROOT) + " is not registered!");
                return false;
            }
            if ( args.length == 0 ) {
                List<String> tabCompletes = new ArrayList<>();

                tabCompletes.add("&aReload");
                tabCompletes.add("&aLoad");
                tabCompletes.add("&eTest");

                for(String s : tabCompletes) {
                    player.sendRawMessage(Coloring.get(s));
                    TBCPlugin.log("" + s);
                }

                return true;
            }
            if ( args.length == 1 ){
                final var usedArgument = args[0];

                if ( is("reload",usedArgument) ){
                    TBCPlugin.getSettings().reload();
                    TBCPlugin.getWaypointConfig().reload();
                    TBCPlugin.getConfiguration().reload();
                    TBCPlugin.getCommandConfig().reload();
                    TBCPlugin.log("Reloading Plugin");
                    player.sendRawMessage("&aReloading the TBC Plugin at " + TimeUtil.getTime(System.currentTimeMillis()));
                }
            }
        }
        return false;
        }

    private boolean is(String requirement, String used){
    if ( requirement == null  ){
        return true;
    }
    if ( used == null ){
         return false;
    }

        return used.equalsIgnoreCase(requirement);
    }
}
