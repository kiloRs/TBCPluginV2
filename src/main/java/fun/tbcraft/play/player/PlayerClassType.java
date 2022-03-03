package fun.tbcraft.play.player;

import fun.tbcraft.play.TBCPlugin;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.profess.PlayerClass;
import org.apache.commons.lang3.Validate;

public enum PlayerClassType{
    MAGE("Mage"),ROGUE("Rogue"),PALADIN("Paladin"),WARRIOR("Warrior"),HUNTER("Hunter"),HUMAN("None");

    private final String id;
    private final Double stelliumMultiplier;
    private final double manaMultiplier;

    PlayerClassType(String id){
        final var s = TBCPlugin.getSettings();
        if ( id.equalsIgnoreCase("None") ){
            this.id = Validate.notNull(id,"Bad Class Setup");
            this.stelliumMultiplier = 1.0;
            this.manaMultiplier = 1;
            return;
        }
        if ( !s.exists(id) ){
            s.addDefault(id + ".Stellium",1);
            s.addDefault(id + ".Mana",1);
            s.save();
        }
        this.id = Validate.notNull(id,"Bad Class Name");
        this.stelliumMultiplier = s.exists(id + ".Stellium")? s.getDouble(id + ".Stellium"):1;
        this.manaMultiplier = s.exists(id + ".Mana")? s.getDouble(id + ".Mana"):1;
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
