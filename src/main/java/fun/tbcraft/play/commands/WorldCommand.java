package fun.tbcraft.play.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldCommand implements CommandExecutor,TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("whereami") || label.equalsIgnoreCase("where")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Oops! This command can only be run by a player");
                return true;
            }
            Player player = (Player) sender;

            String world = player.getWorld().getName();
            int x = (int) (Math.floor(player.getLocation().getX()));
            int y = (int) (Math.floor(player.getLocation().getX()));
            int z = (int) (Math.floor(player.getLocation().getX()));
            String coords = x + ", " + y + ", " + z;

            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&3World&7: &7" + world));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&3Coordinates&7: &7" + coords));
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete (@NotNull CommandSender commandSender , @NotNull Command command , @NotNull String s , @NotNull String[] strings) {
        if (command.getUsage().equalsIgnoreCase("whereami") || command.getUsage().equalsIgnoreCase("where")) {
            return null;
        }
        else {
            return new ArrayList<>();
        }
    }
}
