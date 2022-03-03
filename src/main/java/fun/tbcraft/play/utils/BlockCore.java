package fun.tbcraft.play.utils;

import fun.tbcraft.play.TBCPlugin;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.block.CustomBlock;
import org.bukkit.block.Block;
import org.bukkit.block.SculkSensor;
import org.bukkit.block.TileState;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

import javax.annotation.Nullable;
import java.util.Optional;

public class BlockCore{
    private final Block block;
    private final BlockData blockData;
    @Nullable
    private final CustomBlock customBlock;
    private boolean needspecial = false;

    public BlockCore (BlockPlaceEvent event) {
        this(event.getBlockPlaced());
    }

    private BlockCore (Block block){
        this.block = block;
        this.blockData = block.getBlockData();
        this.customBlock = MMOItems.plugin.getCustomBlocks().getFromBlock(blockData).orElse(null);
        this.needspecial = customBlock!=null;



        if ( customBlock!=null ){
            TBCPlugin.debug("Custom Block Found: " + customBlock.getId());
            TBCPlugin.debug("Required:" + customBlock.getRequiredPower());
            TBCPlugin.debug("Experience: " + customBlock.getMinExpDrop() + " - " + customBlock.getMaxExpDrop());
            TBCPlugin.debug("Generation Template" + customBlock.getGenTemplate().getId());

            needspecial = true;
            if ( customBlock.requirePowerToBreak() ) {
                TBCPlugin.debug("Requires Power to Break " + customBlock.getItem().displayName() );
            }
            return;
        }
        //Testing SculkSensor Block as TileState
        checkSculkSensorDebug(block);


    }

    public boolean isCustom(){
        return needspecial;
    }
    public @org.jetbrains.annotations.Nullable CustomBlock getCustomBlock(){
        return customBlock;
    }
    private void checkSculkSensorDebug (Block block) {
        if ( block.getState() instanceof TileState && block.getState() instanceof SculkSensor ){
            Optional<SculkSensor> optionalSculkSensor = Optional.of((SculkSensor) block);


            final var sculkSensor = optionalSculkSensor.orElseThrow(( ) -> {
                return new RuntimeException("Bad Sculk Sensor Sensor!");
            });
            if ( sculkSensor.getListenerRange()<=8 ){
                final var range = TBCPlugin.getConfiguration().exists("Sensor.Range")?TBCPlugin.getConfiguration().getInt("Sensor.Range"):20;
                sculkSensor.setListenerRange(range);
                sculkSensor.setMetadata("Ranged",new FixedMetadataValue(TBCPlugin.getPlugin(),range));
            }

            TBCPlugin.debug("Located Sculk Sensor at " + block.getLocation().toBlockLocation().toString() + "!");
            TBCPlugin.log("Sensor Strength: " + sculkSensor.getListenerRange() + "!");
        }
        else if ( !( block.getState() instanceof SculkSensor ) ){
            TBCPlugin.debug("Block Type: "  + block.getType().toString());
        }
    }


    public static BlockCore createBlockUtil (Block block) {
        return new BlockCore(block);
    }

    public boolean isSpecial ( ) {
        return needspecial;
    }

    public Block getBlock ( ) {
        return block;
    }
}
