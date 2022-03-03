package fun.tbcraft.play.utils;

import fun.tbcraft.play.TBCPlugin;
import fun.tbcraft.play.listener.BaseListener;
import fun.tbcraft.play.listener.DebugListener;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class TBCRegistrationFactory{
    private static final Map<String, BaseListener> listenerMap = new HashMap<>();

    static {
        final var debugListener = new DebugListener();
        listenerMap.put(debugListener.getID(),debugListener);
    }

    public static void registerCommand(String id,CommandExecutor executor){
        registerCommands((JavaPlugin) TBCPlugin.getPlugin() ,id,executor,null);
    }
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
            TBCPlugin.log("Registering " + listener + " on " + plugin.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, BaseListener> getListenerMap ( ) {
        return listenerMap;
    }
}
