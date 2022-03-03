package fun.tbcraft.play.listener;

import fun.tbcraft.play.TBCPlugin;
import fun.tbcraft.play.block.BlockCore;
import fun.tbcraft.play.utils.TBCTimeHandler;
import io.lumine.mythic.lib.MythicLib;
import io.lumine.mythic.lib.api.event.skill.PlayerCastSkillEvent;
import io.lumine.mythic.lib.skill.handler.SkillHandler;
import net.Indyuce.mmocore.skill.CastableSkill;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;


public class DebugListener implements BaseListener{
    private static String ovv = "default";
    private static List<String> acceptableWorlds = TBCPlugin.getConfiguration().getStringList("Worlds");
    private List<String> skillHandlerList = new ArrayList<>();
    private static boolean cancelEach = false;
    private static boolean debug = false;

    public DebugListener(){

    }
    @EventHandler
    public void onSkill(PlayerCastSkillEvent e){
        final var skill = e.getCast();

        if ( skill instanceof CastableSkill castableSkill){
            final var classSkill = castableSkill.getSkill();
            final var skillName = classSkill.getSkill().getName();
            final var cooldownPath = castableSkill.getCooldownPath();
            final var minLevel = classSkill.getUnlockLevel();
            final var hasMaxLevel = classSkill.hasMaxLevel();
            final var maxLevel = classSkill.getMaxLevel();

            TBCPlugin.writeToFile("Skill Cast Data: " + skillName);
            TBCPlugin.writeToFile("Cooldown Path: " + cooldownPath);
            TBCPlugin.writeToFile("Min Level: " + minLevel);
            TBCPlugin.writeToFile("Max Level: " + maxLevel);

            TBCPlugin.log("Skill: " + skillName);
            TBCPlugin.log("Level Data; " + minLevel + " - " + maxLevel);
        }
        if ( skill instanceof AbilityData abilityData ) {
            final var abilityDataHandler = abilityData.getHandler();
            final var itemHandler = abilityDataHandler.getId();
            final var registeredSkill = abilityData.getAbility();
            final var skillName = registeredSkill.getName();

            TBCPlugin.debug("Casting Ability: " + skillName);
            TBCPlugin.debug("Using Handler: " + itemHandler);
            TBCPlugin.debug("Amount of Modifiers: " + abilityData.getModifiers().size());
            for(String each : abilityData.getModifiers()) {
                TBCPlugin.debug(each + " : " + abilityData.getModifier(each));
            }
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        var world = e.getPlayer().getWorld();

        if ( acceptableWorlds.contains(world.getName()) || e.getPlayer().isOp() ){
            final var handlers = MythicLib.inst().getSkills().getHandlers();

            final var skillHandlers = handlers.stream().toList();

            for(SkillHandler skillHandler : skillHandlers) {
                final var skillHandlerID = skillHandler.getId();

                if ( skillHandlerID.isEmpty() ){
                    continue;
                }
                skillHandlerList.add(skillHandlerID);

                TBCPlugin.debug("Skill Handler Found: " + skillHandlerID);

                if ( !skillHandler.getModifiers().isEmpty() ){
                    TBCPlugin.log("Skill Handler: " + skillHandlerID + " " + "[Modifiers]");
                    int found = 0;
                    for(Object modifier : skillHandler.getModifiers()) {
                        found = found + 1;
                        TBCPlugin.log(found + " " + modifier.toString());

                    }
                }
                if ( skillHandler.isTriggerable() ){
                    TBCPlugin.debug("- Triggerable: " + Boolean.toString(skillHandler.isTriggerable()).replace("true","Yes").replace("false","No"));
                }

                final var time = TBCTimeHandler.getTime(System.currentTimeMillis());
                TBCPlugin.log("Logging Skill at " + time);
                TBCPlugin.writeToFile("Start: " + time);
                TBCPlugin.writeToFile("Skill Data for " + skillHandlerID);
                TBCPlugin.writeToFile("Modifiers:" + ( (String[]) skillHandler.getModifiers().toArray() ));
                TBCPlugin.writeToFile("End: " + time);
            }


        }
    }
    @EventHandler
    public void on(BlockPlaceEvent p) {
        final var block = p.getBlockPlaced();
        final var blockCore = BlockCore.createBlockCore(block , debug);

        if ( cancelEach ){
            p.setCancelled(true);
        }

        if ( blockCore.isCustom() ) {
            final var customBlock = blockCore.getCustomBlock();

            if ( customBlock == null ){
                return;

            }

            final var s = customBlock.getState();
            final var uniqueId = s.getUniqueId();

            TBCPlugin.log("Block Id: " + uniqueId);

            if ( p.getPlayer().isOp() ){
                TBCPlugin.log("Block Type: " + customBlock.getId());
                TBCPlugin.log("Block UUID: " + s.getUniqueId());
                TBCPlugin.log("Up: "+ s.getSide("up"));
                TBCPlugin.log("Down: "+s.getSide("down"));
                TBCPlugin.log("North: " +s.getSide("north") );
                TBCPlugin.log("South: " +s.getSide("south") );
                TBCPlugin.log("East: " +s.getSide("east") );
                TBCPlugin.log("West: " +s.getSide("west") );

            }
        }
    }

    @Override
    public String getID ( ) {
        return "Debug";
    }

    @Override
    public boolean cancelAll (boolean useStop) {
        return cancelEach;
    }

    @Override
    public boolean shouldUse ( ) {
        return debug;
    }

    @Override
    public void setUseState (Boolean be) {
        debug = be;
    }
}
