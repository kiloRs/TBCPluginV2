package fun.tbcraft.play.utils;

import fun.tbcraft.play.TBCPlugin;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.profess.PlayerClass;
import org.apache.commons.lang3.Validate;

public enum PlayerClassType{
    MAGE("Mage"),ROGUE("Rogue"),PALADIN("Paladin"),WARRIOR("Warrior"),HUNTER("Hunter");

    private final String id;
    private final Double stelliumMultiplier;
    private final double manaMultiplier;

    PlayerClassType(String id){
        this.id = Validate.notNull(id,"Bad Class Name");
        this.stelliumMultiplier = TBCPlugin.getSettings().exists(id + ".Stellium")?TBCPlugin.getSettings().getDouble(id + ".Stellium"):1;
        this.manaMultiplier = TBCPlugin.getSettings().exists(id + ".Mana")?TBCPlugin.getSettings().getDouble(id + ".Mana"):1;
    }
    public boolean hasSettings(){
        return TBCPlugin.getSettings().isSection(id);
    }

    public boolean isRanged(){
        return this==HUNTER||this==MAGE;
    }
    public boolean isTank(){
        return this==WARRIOR||this==PALADIN;
    }
    public boolean usesMagic(){
        return this==MAGE||this==PALADIN;
    }

    public PlayerClass get(){
        return MMOCore.plugin.classManager.getOrThrow(id);
    }

    public double getStelliumMultiplier ( ) {
        return stelliumMultiplier;
    }

    public double getManaMultiplier ( ) {
        return manaMultiplier;
    }
}
