package fun.tbcraft.play.hooks.mmoitems;

import net.Indyuce.mmoitems.stat.type.ItemStat;
import org.bukkit.Material;

public class NewStats{
    private static final ItemStat modelLevelStat = new ModelStat("MODEL_LEVEL", Material.KNOWLEDGE_BOOK,"Model Level",new String[]{"Determines the model of the item, based on the level given below."},new String[]{"all"});

    public static ItemStat getModelLevelStat ( ) {
        return modelLevelStat;
    }
}
