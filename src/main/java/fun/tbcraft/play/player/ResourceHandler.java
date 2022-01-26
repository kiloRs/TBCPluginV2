package fun.tbcraft.play.player;

import net.Indyuce.mmocore.api.event.PlayerResourceUpdateEvent;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.profess.resource.PlayerResource;

public class ResourceHandler{
    private PlayerResource resource;
    private PlayerData corePlayerData;

    public ResourceHandler(PlayerResource e){
        this.resource = e;

    }
    public void add(double amt, TBCPlayer tbcPlayer){
        corePlayerData = tbcPlayer.getCorePlayerData();

        switch(resource){

            case HEALTH -> {
                corePlayerData.heal(amt, PlayerResourceUpdateEvent.UpdateReason.OTHER);
            }
            case MANA -> {
                corePlayerData.giveMana(amt, PlayerResourceUpdateEvent.UpdateReason.OTHER);
            }
            case STAMINA -> {
                corePlayerData.giveStamina(amt, PlayerResourceUpdateEvent.UpdateReason.OTHER);
            }
            case STELLIUM -> {
                corePlayerData.giveStellium(amt, PlayerResourceUpdateEvent.UpdateReason.OTHER);

            }
        }
    }
}
