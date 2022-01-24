package fun.tbcraft.play.player.attributes;

import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.attribute.PlayerAttribute;
import org.apache.commons.lang3.Validate;

public class AttributeInstanced{
    private String validated = "";
    private PlayerAttribute e = null;
    private String id = "";
    private int level = 10;
    public AttributeInstanced (PlayerAttribute e) {
        this.e = e;
        this.id = e.getId();
        this.level = e.getMax();

        validated = Validate.notNull(this.id, "Invalid Attribute ID");
    }

    public String getId ( ) {
        return id;
    }

    public int getMaxLevel ( ) {
        return level;
    }

    public int getPlayerLevel(PlayerData playerData){
       return playerData.getAttributes().getAttribute(MMOCore.plugin.attributeManager.get(validated));
    }

}
