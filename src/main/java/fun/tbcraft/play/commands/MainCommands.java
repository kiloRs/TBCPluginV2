package fun.tbcraft.play.commands;

import fun.tbcraft.play.TBCPlugin;
import io.lumine.mythic.utils.chat.ColorString;
import io.lumine.xikage.mythicmobs.MythicMobs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainCommands implements CommandExecutor, TabExecutor{
    private static List<String> t = new ArrayList<>();
    @Override
    public boolean onCommand (CommandSender sender , Command command , String label , String[] args) {
        if ( !( sender instanceof Player player ) ){
            return false;
        }
        if ( !command.isRegistered() ){
            ( (Player) sender ).sendRawMessage(ColorString.get("&cInvalid Command Registration!"));
            return false;
        }
        if ( command.getUsage().equalsIgnoreCase("mobzend") ){
            if ( args.length>0 ) {
                final var m = args[0];
                final var activeMob = MythicMobs.inst().getMobManager().spawnMob(m , player.getLocation());

                BukkitRunnable runnable = new BukkitRunnable(){
                    @Override
                    public void run ( ) {
                        if ( activeMob == null ){
                            return;
                        }
                        activeMob.setFaction("Ritual");
                    }
                };

                runnable.runTask(TBCPlugin.getPlugin());
            }
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

    @Override
    public @Nullable List<String> onTabComplete (@NotNull CommandSender commandSender , @NotNull Command command , @NotNull String s , @NotNull String[] strings) {
        if ( command.getUsage().equalsIgnoreCase("tbc") ){
            t.clear();
            t.add("Reload");
            t.add("Load");
            t.add("Save");
            return t;
        }
        else {
            return new ArrayList<>();
        }
    }
}
