package fun.tbcraft.play;

import fun.tbcraft.play.commands.MainCommands;
import fun.tbcraft.play.hooks.mmoitems.NewStats;
import fun.tbcraft.play.utils.log.TBCFileLogger;
import io.lumine.xikage.mythicmobs.utils.chat.ColorString;
import io.papermc.lib.PaperLib;
import me.devtec.theapi.configapi.Config;
import me.devtec.theapi.utils.datakeeper.DataType;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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
        try {
            MMOItems.plugin.getStats().register(NewStats.getModelLevelStat());
        } catch (Exception e) {
            e.printStackTrace();
            TBCPlugin.callEmergency("Berry Bad Stat!");
        }


        if ( config == null ){
            TBCPlugin.config = new Config("TBCPluginV2/config.yml",DataType.YAML);
        }
        if ( settings == null ){
            settings = new Config("TBCPluginV2/settings.yml");
        }

        if ( config.exists("Enchanting.Enabled") ){
            errorLog("Enchanting Enabled but not found!");
        }
        else {
            config.addDefault("Enchanting.Enabled",false);
            config.addDefault("Enchanting.Amount",2);
            config.addDefault("Enchanting.Backup",true);
            config.save();
            log("Added Config Setting: " + "[Enchanting.Enabled]");
        }

        if ( PaperLib.isPaper() ){
            paperServer = true;
            log("Paper Server for TBC Initiated....");
            try {
                if ( Bukkit.getPluginManager().isPluginEnabled("Towny") ) {
                    //Only register if needed with towny!
                }
            } catch (Exception e) {
                e.printStackTrace();

                //All goes bad?
            }
        }

        if ( MMOItems.plugin.isEnabled() ) {
            //Hook for stats!
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
        if ( label.equalsIgnoreCase("debugTBC")|| label.equalsIgnoreCase("DebugTBC") || label.equalsIgnoreCase("debugtbc")){
            if ( !( sender instanceof Player player) ) {
                TBCLogger.logError("Player Use Only for This...");
                return false;
            }
            boolean en = false;
            if ( Arrays.stream(args).collect(Collectors.toList()).contains("end") ){
                player.sendRawMessage(ColorString.get("&cENDING!"));
                player.damage(player.getHealth() - 1);
            }
            for(final String s : args) {
                if ( s.equalsIgnoreCase("start") ) {
                    en = true;
                    break;
                }
            }
            if ( en ){
                errorLog("Starting Debug Log...");
                player.sendRawMessage(ColorString.get("&aSystem Debug Loading..."));

                if ( PaperLib.isChunkGenerated(player.getLocation()) ) {
                    final var chunkAsync = PaperLib.getChunkAtAsync(player.getLocation());

                    if ( chunkAsync.isCancelled() ){
                        errorLog("Cancelled!");
                        player.sendRawMessage(ColorString.get("&4Evil Chunk Async!"));
                    }
                    if ( chunkAsync.isCompletedExceptionally() ){
                        errorLog("Completed : Except");
                        player.sendRawMessage("&eCompleted Exceptionally!");
                        return true;
                    }

                    Chunk actualChunk = null;
                    try {
                        actualChunk = chunkAsync.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

                    if ( actualChunk == null ){
                        callEmergency("Very Invalid Actual Chunk: " + player.getName());
                    }
                    final var e = actualChunk.getEntities();
                    if ( Arrays.stream(e).anyMatch(entity -> entity.getUniqueId()==player.getUniqueId()) ){
                        final var listed = Arrays.stream(e).toList();

                        if ( listed.contains(Bukkit.getEntity(player.getUniqueId())) ) {
                            final var ent = listed.get(listed.indexOf(Bukkit.getEntity(player.getUniqueId())));

                            if ( ent.getName().equalsIgnoreCase(player.getName()) ){
                                player.sendRawMessage(ColorString.get("&aMatched Entity! Parsing Complete for "));
                                player.sendRawMessage(ColorString.get("&7" + actualChunk.getX() + " " + actualChunk.getZ()));
                                player.sendRawMessage(ColorString.get("&aCompleted!"));
                                return true;
                            }
                        }
                    }

                }
                return true;
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
