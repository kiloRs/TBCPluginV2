package fun.tbcraft.play.player;

import org.bukkit.entity.Player;

public interface DungeonPlayer{

    public boolean isInDungeon();

    public void setInDungeon(String s   );

    public Player get();
}
