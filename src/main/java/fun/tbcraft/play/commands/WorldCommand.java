package fun.tbcraft.play.commands;

import fun.tbcraft.play.utils.ColorWords;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.xml.validation.Validator;
import java.util.ArrayList;
import java.util.List;

public class WorldCommand implements CommandExecutor, TabExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("whereami") || label.equalsIgnoreCase("where")) {
            if (!(sender instanceof Player send)) {
                sender.sendMessage("Oops! This command can only be run by a player");
                return true;
            }
            if ( args.length == 1 ){

                if ( args[0].startsWith("-") ){
                    final var wording = args[0].split("-")[1];
                    if ( wording.equalsIgnoreCase("w") ){
                        showLocation(send,null,true);
                    }
                }
                final var otherPlayer = Bukkit.getPlayerExact(args[0]);
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
            sender.sendRawMessage(ColorWords.get("&b" + world));
        }
        sender.sendMessage(ColorWords.get(
                "&3World&7: &7" + world));

        sender.sendMessage(ColorWords.get(
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
