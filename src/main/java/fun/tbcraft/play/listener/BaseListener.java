package fun.tbcraft.play.listener;

import org.bukkit.event.Listener;

public interface BaseListener extends Listener{
    public static int numberOf = 0;

    public String getID();
    public boolean cancelAll(boolean useStop);
    public boolean shouldUse();
    public void setUseState(Boolean be);
}
