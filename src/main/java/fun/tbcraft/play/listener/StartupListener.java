package fun.tbcraft.play.listener;

import dev.lone.itemsadder.main.S;
import fun.tbcraft.play.TBCPlugin;
import fun.tbcraft.play.utils.TBCTimeHandler;
import fun.tbcraft.play.utils.log.TBCFileLogger;
import fun.tbcraft.play.utils.log.TBCLogger;
import io.lumine.mythic.lib.MythicLib;
import io.lumine.mythic.lib.api.event.skill.PlayerCastSkillEvent;
import io.lumine.mythic.lib.manager.SkillManager;
import io.lumine.mythic.lib.skill.Skill;
import io.lumine.mythic.lib.skill.SkillMetadata;
import io.lumine.mythic.lib.skill.handler.MythicMobsSkillHandler;
import io.lumine.mythic.lib.skill.handler.SkillHandler;
import io.lumine.mythic.lib.skill.result.MythicMobsSkillResult;
import io.lumine.mythic.lib.skill.result.SkillResult;
import io.papermc.paper.event.world.border.WorldBorderBoundsChangeEvent;
import net.Indyuce.mmocore.api.ConfigFile;
import net.Indyuce.mmocore.skill.CastableSkill;
import net.Indyuce.mmocore.skill.ClassSkill;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.block.CustomBlock;
import net.Indyuce.mmoitems.api.util.MushroomState;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.util.Buildable;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public class StartupListener implements Listener{
    private static String ovv = "default";
    private static List<String> acceptableWorlds = TBCPlugin.getConfiguration().getStringList("Worlds");
    private List<String> skillHandlerList = new ArrayList<>();

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
    }
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

}
