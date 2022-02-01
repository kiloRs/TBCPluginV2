package fun.tbcraft.play.utils;

import fun.tbcraft.play.TBCPlugin;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TBCRegistrationFactory{
    static void registerCommands(JavaPlugin plugin, String commandText, CommandExecutor executor, TabCompleter tab) {
        final var calledCommand = plugin.getCommand(commandText);
        Validate.notNull(calledCommand ,"Command Cannot Be Empty");

        calledCommand.setExecutor(executor);
        if (tab != null) {
            calledCommand.setTabCompleter(tab);
        }

    }

    static void registerEvents(Listener listener, JavaPlugin plugin) {
        try {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
            TBCPlugin.log("Registering " + listener.toString() + " on " + plugin.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
