package fun.tbcraft.play.utils;

import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.profess.PlayerClass;
import org.apache.commons.lang3.Validate;

public enum PlayerClassType{
    MAGE("Mage"),ROGUE("Rogue"),PALADIN("Paladin"),WARRIOR("Warrior"),HUNTER("Hunter");

    private final String id;

    PlayerClassType(String id){
        this.id = Validate.notNull(id,"Bad Class Name");
    }

    public PlayerClass get(){
        return MMOCore.plugin.classManager.getOrThrow(id);
    }
}
