package fun.tbcraft.play.player.attributes;

import io.lumine.mythic.utils.chat.ColorString;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitPlayer;
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.mobs.GenericCaster;
import io.lumine.xikage.mythicmobs.skills.*;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.attribute.PlayerAttributes;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public abstract class AttributeHandle{
    private static Map<String, Skill> skillMap = new HashMap<>();
    final PlayerData playerData;
    final int attr;
    final PlayerAttributes.AttributeInstance actual;
    final AttributeInstanced instanced;
    final Player player;
    private BukkitAPIHelper bukkitAPIHelper = new BukkitAPIHelper();

    public AttributeHandle (AttributeInstanced instanced , Player player) {
        this.instanced = instanced;
        this.player = player;
        this.playerData = PlayerData.get(player);
        this.attr = playerData.getAttributes().getAttribute(MMOCore.plugin.attributeManager.get(instanced.getId()));
        this.actual = playerData.getAttributes().getInstance(MMOCore.plugin.attributeManager.get(instanced.getId()));

    }

    /**
     * A simple test method for casting. This has not been setup yet.
     * @param skillName Skill to use.
     * @return if skill is casted.
     */
    public boolean cast(String skillName){
        SkillCaster caster = new GenericCaster(new BukkitPlayer(player));
        SkillMetadata metadata = new SkillMetadata(SkillTrigger.API,caster,new BukkitPlayer(player));

        //This is just a test, messing with the custom skill casting?
        SkillCondition condition = new SkillCondition("");

        final var isCasted = new BukkitAPIHelper().castSkill(caster.getEntity().getBukkitEntity() , skillName);

        if ( canCast(condition.evaluateCaster(metadata)) ){
            if ( isCasted ){
                player.sendRawMessage(ColorString.get("&cCondition Casted"));
            }
            else {
                player.sendRawMessage(ColorString.get("&aCondition Failed?"));
            }
        }
        else {
            player.sendRawMessage(ColorString.get("&cBad Condition Setup"));
        }
        return canCast(condition.evaluateCaster(metadata));

    }
    public boolean canCast(boolean state){
        return state;
    }
}
