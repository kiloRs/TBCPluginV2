package fun.tbcraft.play.commands;

import fun.tbcraft.play.utils.Coloring;
import org.apache.commons.lang3.Validate;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class WorldCommand implements CommandExecutor, TabExecutor{
    private Location location;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final var firstWord = args[0];

        if ( !sender.isOp() ){
            sender.sendMessage(Coloring.get("&cYou cannot use this command due to permissions of KingKilo."));
            return false;
        }

        if ( label.equalsIgnoreCase("location") || label.equalsIgnoreCase("l") ){
            if ( args.length == 1 ){
                if ( firstWord.startsWith("-") ){
                    final var occurance = firstWord.split("-")[1];

                    if ( occurance.equalsIgnoreCase("s") || occurance.equalsIgnoreCase(sender.getName())){
                        if ( sender instanceof Player player ){
                            player.sendRawMessage(Coloring.get("&aCurrent Location"));
                            location = player.getLocation();
                            player.sendRawMessage("&e - X: " + location.getBlockX());
                            player.sendRawMessage("&b - Y: " + location.getBlockY());
                            player.sendRawMessage("&e - Z: " + location.getBlockZ());
                            player.sendRawMessage("&5 - World: " + location.getWorld().getName());
                            player.sendRawMessage("&7 - Dimension Type: " + location.getWorld().getWorldType().getName());
                        }
                    }
                }
                Player o = Bukkit.getPlayerExact(firstWord);

                if ( o == null ){
                    sender.sendMessage("&cPlayer Not Found");
                }
            }
        }
        if (label.equalsIgnoreCase("whereami") || label.equalsIgnoreCase("where")) {
            if (!(sender instanceof Player send)) {
                sender.sendMessage("Oops! This command can only be run by a player");
                return true;
            }
            if ( args.length == 1 ){

                if ( firstWord.startsWith("-") ){
                    final var wording = firstWord.split("-")[1];
                    if ( wording.equalsIgnoreCase("w") ){
                        showLocation(send,null,true);
                    }
                }
                final var otherPlayer = Bukkit.getPlayerExact(firstWord);
                final var nnPl = Validate.notNull(otherPlayer,"Bad Player Name");

                showLocation(nnPl,send,false);
                return true;
            }
            Player player = send;

            showLocation(player,false);
            return true;
        }
        return false;
    }

    public void showLocation(Player player, boolean worldOnly){
        showLocation(player,null,worldOnly);
    }
    private void showLocation (Player player,@Nullable Player sender, boolean worldOnly) {
        String world = player.getWorld().getName();
        int x = (int) (Math.floor(player.getLocation().getX()));
        int y = (int) (Math.floor(player.getLocation().getX()));
        int z = (int) (Math.floor(player.getLocation().getX()));
        String coords = x + ", " + y + ", " + z;

        if ( sender == null ){
            sender = player;
        }
        if ( worldOnly ){
            sender.sendRawMessage(Coloring.get("&b" + world));
        }
        sender.sendMessage(Coloring.get(
                "&3World&7: &7" + world));

        sender.sendMessage(Coloring.get(
                "&3Coordinates&7: &7" + coords));
    }

    @Override
    public @Nullable
    List<String> onTabComplete (@NotNull CommandSender commandSender , @NotNull Command command , @NotNull String s , @NotNull String[] strings) {
        if (command.getUsage().equalsIgnoreCase("whereami") || command.getUsage().equalsIgnoreCase("where")) {
            return null;
        }
        else {
            return new ArrayList<>();
        }
    }
}
