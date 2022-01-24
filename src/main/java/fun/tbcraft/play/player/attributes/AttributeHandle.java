package fun.tbcraft.play.player.attributes;

import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.skills.Skill;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.attribute.PlayerAttributes;
import org.bukkit.entity.Entity;
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

    public boolean isValid ( ) {
        return instanced.getPlayerLevel(playerData) == instanced.getMaxLevel();
    }

    public boolean cast (Entity e) {
        return false;
    }
}
