package fun.tbcraft.play.player;

import net.Indyuce.mmocore.api.event.PlayerResourceUpdateEvent;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.profess.resource.PlayerResource;
import org.bukkit.Bukkit;
import org.bukkit.SoundCategory;

public class ResourceHandler{
    private PlayerResource resource;
    private PlayerData corePlayerData;

    public ResourceHandler(PlayerResource e){
        this.resource = e;

    }
    public void subtract(double amt, TBCPlayer tbcPlayer){
        corePlayerData = tbcPlayer.getCorePlayerData();

        switch(resource){

            case HEALTH -> {
                final var player = tbcPlayer.getStoredPlayer();
                if ( player.getHealth() - amt <= 0 ){
                    final var world = Bukkit.getWorld(player.getWorld().getName());
                    if ( world == null ){
                        throw new RuntimeException("Bad World In Health Set");
                    }
                    world.playSound(player, org.bukkit.Sound.ENTITY_WITCH_HURT, SoundCategory.HOSTILE,1,1);
                    player.damage(amt);
                    return;
                }
                player.setHealth(player.getHealth() - amt);
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
            default -> throw new IllegalStateException("Unexpected value: " + resource);
        }
    }
    public void add(double amt, TBCPlayer tbcPlayer){
        corePlayerData = tbcPlayer.getCorePlayerData();

        switch(resource){

            case HEALTH -> {
                final var player = tbcPlayer.getStoredPlayer();
                tbcPlayer.getCorePlayerData().heal(amt, PlayerResourceUpdateEvent.UpdateReason.OTHER);
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
            default -> throw new IllegalStateException("Bad Resource Name: " + resource);
        }
    }
}
