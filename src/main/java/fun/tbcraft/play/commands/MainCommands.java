package fun.tbcraft.play.commands;

import fun.tbcraft.play.TBCPlugin;
import io.lumine.mythic.utils.chat.ColorString;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommands implements CommandExecutor{
    @Override
    public boolean onCommand (CommandSender sender , Command command , String label , String[] args) {
        if ( !( sender instanceof Player player ) ){
            return false;
        }

        if ( label.equals("run") && args.length == 1 && args[0].equalsIgnoreCase("task") ){
            player.sendRawMessage(ColorString.get("&cAttempting to run...."));
        }
        if ( !command.isRegistered() ){
            ( (Player) sender ).sendRawMessage(ColorString.get("&cInvalid Command Registration!"));
            return false;
        }
        if ( command.getUsage().equals("tbc") || command.getName().equalsIgnoreCase("tbc") ){
            if ( args.length == 1 ) {
                final var arg = args[0];

                if ( arg.equalsIgnoreCase("reload") ){
                    TBCPlugin.getPlugin().reloadConfig();
                    TBCPlugin.getConfiguration().reload();
                    TBCPlugin.getSettings().reload();
                    TBCPlugin.log("Reloading....");

                    TBCPlugin.log("Reloading Completed!");
                    player.sendRawMessage(ColorString.get("&aReloading Plugin..."));

                    return true;
                }
            }
            player.sendRawMessage(ColorString.get("&cNo Arguments Found!"));
            return true;
        }
        return false;
    }
}
