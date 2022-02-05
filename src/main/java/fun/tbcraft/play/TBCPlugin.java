package fun.tbcraft.play;

import com.palmergames.bukkit.towny.TownyAPI;
import fun.tbcraft.play.commands.MainCommands;
import fun.tbcraft.play.commands.WorldCommand;
import fun.tbcraft.play.utils.log.TBCFileLogger;
import fun.tbcraft.play.utils.log.TBCLogger;
import io.lumine.mythic.lib.api.player.EquipmentSlot;
import io.lumine.mythic.utils.chat.ColorString;
import io.papermc.lib.PaperLib;
import me.devtec.theapi.configapi.Config;
import me.devtec.theapi.utils.datakeeper.DataType;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.TypeSet;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class TBCPlugin extends JavaPlugin{
    private static Plugin plugin = null;
    private static final TBCFileLogger tbcFileLogger = new TBCFileLogger("log.txt");
    public static Plugin getPlugin ( ) {
        return plugin;
    }
    private static Config config = new Config("TBCPluginV2/config.yml", DataType.YAML);
    private static Config settings = new Config("TBCPluginV2/settings.yml");
    private static boolean paperServer = false;

    public static Config getConfiguration ( ) {
        return config;
    }

    public static Config getSettings ( ) {
        return settings;
    }

    public static boolean isPaperServer ( ) {
        return paperServer;
    }

    public static void log(String logMessage){
        TBCLogger.logMessage(logMessage);
    }
    public static void errorLog(String logMessage){
        TBCLogger.logError(logMessage);
    }

    @Override
    public void onLoad ( ) {
        log("Enabling System on TBC");

    }

    @Override
    public void onEnable ( ) {
        plugin = this;
        //Loading Main Stat Below.

        registerCommands(this, "whereami", new WorldCommand(), new WorldCommand());

        if ( config == null ){
            TBCPlugin.config = new Config("TBCPluginV2/config.yml",DataType.YAML);
        }
        if ( settings == null ){
            settings = new Config("TBCPluginV2/settings.yml");
        }
        if ( settings.getKeys().isEmpty() ) {
            settings.addDefault("Events.Enabled" , new ArrayList<>());
            settings.addDefault("Events.Disabled" , new ArrayList<>());
            settings.addDefault("Attribute.Tracking" , new Config.Node(true));
            settings.addDefault("Attribute.Enabled" , new Config.Node(true));
            settings.save();
            log("Loaded Settings");
        }
        if ( config.getKeys().size() == 0  ) {
            config.addDefault("Enchanting.Enabled" , false);
            config.addDefault("Enchanting.Amount" , 2);
            config.addDefault("Enchanting.Backup" , true);
            config.save();
            log("Added Config Setting: " + "[Enchanting.Enabled]");
        }
        if ( PaperLib.isPaper() ){
            paperServer = true;
            log("Paper Server for TBC Initiated....");
            try {
                if ( Bukkit.getPluginManager().isPluginEnabled("Towny") ) {
                    //Only register if needed with towny!
                    log("Towny Located");
                    final var world = Bukkit.getWorld(config.getStringList("Towny.Worlds").get(0));

                    if ( world != null ){
                        if ( TownyAPI.getInstance().isTownyWorld(world) ) {
                            log("Towny World: " + world.getName());
                    }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

                //All goes bad?
            }
        }

        if ( MMOItems.plugin.isEnabled() ) {
            //Hook for stats!
            final var type = new Type(TypeSet.OFFHAND,"Passive",false, EquipmentSlot.BOTH_HANDS,true);
            //Registers New Type
            MMOItems.plugin.getTypes().register(type);
            log("MMOItems Hooked!");
        }
            log("Completed TBCv2 Startup!");

        setConfigDefaults(0,true);

    }
    static void registerCommands(JavaPlugin plugin, String commandText, CommandExecutor executor, TabCompleter tab) {
        plugin.getCommand(commandText).setExecutor(executor);
        if (tab != null) {
            plugin.getCommand(commandText).setTabCompleter(tab);
        }

    }

    static void registerEvents(Listener listener, JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }
    public static void debug(String toDebug){
        TBCLogger.debugOnly(toDebug,-1);
    }
    @Override
    public boolean onCommand (CommandSender sender , Command command , String label , String[] args) {
        if ( label.equalsIgnoreCase("tbc") || label.equalsIgnoreCase("TBC") ){
            final var amountOfArgs = args.length;

            if ( amountOfArgs > 0 ){
                if ( amountOfArgs == 1 ){
                    if ( args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("load") ){
                        config.reload();
                        settings.reload();
                        reloadConfig();
                        log("Reloading TBC!");
                        if ( sender instanceof Player player ){
                            player.sendRawMessage(ColorString.get("&aReloading TBC Plugin..."));
                        }

                        return true;
                    }
                    else{
                        if ( sender instanceof Player p ){
                            final var thisArg = args[0];
                            final var player = Bukkit.getPlayer(thisArg);
                            if ( player != null ) {
                                player.sendPluginMessage(TBCPlugin.plugin,"",new byte[]{Byte.parseByte("You have been loaded!")});

                            }
                        }
                    }
                }
            }
        }
        return new MainCommands().onCommand(sender, command, label, args);
    }

    @Override
    public void onDisable ( ) {

        log("Closing...");
    }

    private void setConfigDefaults (int debugLevel, boolean hookEnabled ) {
        int changes = 0;

        if ( !config.exists("Debug.Level") ){
            config.addDefault("Debug.Level",debugLevel);
            config.save();
            config.addComment("Debug.Level", "#Debug System","#0 for no debug, 3 for emergency, 1 for log");
            ++changes;
        }
        if ( !config.exists("Hooks.Enabled") ){
            config.addDefault("Hooks.Enabled",hookEnabled);
            config.save();
            config.addComment("Hooks.Enabled","#Enables the hook system!");
            ++changes;
        }
        if ( !config.exists("NBT.Storage") ){
            config.addDefault("NBT.Storage",true);
            ++changes;
        }

        if ( changes>0 ){
            config.save();
        }
    }

    public static void writeToFile(String toLog){
        tbcFileLogger.logToFile(toLog,"log","txt");
    }

    public static void callEmergency ( ){
        throw new RuntimeException();
    }
    public static void callEmergency (String emergency){
        throw new RuntimeException(emergency + " !");
    }
}
