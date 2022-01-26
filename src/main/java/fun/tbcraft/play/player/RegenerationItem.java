package fun.tbcraft.play.player;

import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class RegenerationItem extends MMOItemTemplate{
    public RegenerationItem (Type type , String id) {
        super(type , id);
    }

    public RegenerationItem (Type type , ConfigurationSection config) {
        super(type , config);
    }

    @Override
    public MMOItemBuilder newBuilder (@Nullable Player player) {
        return super.newBuilder(player);
    }

    @Override
    public String getCooldownPath ( ) {
        return super.getCooldownPath();
    }
}
