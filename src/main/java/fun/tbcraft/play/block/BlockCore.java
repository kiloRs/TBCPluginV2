package fun.tbcraft.play.block;

import fun.tbcraft.play.TBCPlugin;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.block.CustomBlock;
import org.apache.commons.lang.Validate;
import org.bukkit.block.Block;
import org.bukkit.block.SculkSensor;
import org.bukkit.block.TileState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
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

    public BlockCore(Player p){
        this(p.getTargetBlock(100),p.isOp());
    }
    public BlockCore (BlockPlaceEvent event) {
        this(event.getBlockPlaced(),TBCPlugin.getConfiguration().exists("Debug.Blocks")&&TBCPlugin.getConfiguration().getBoolean("Debug.Blocks"));
    }

    BlockCore (Block block , boolean extraTests){
        Validate.notNull(block,"The block found is actually null!");
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
        if ( !needspecial && extraTests ) {
            //Testing SculkSensor Block as TileSate
            checkSculkSensorDebug(block);
        }

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
        return createBlockCore(block,true);
    }
    public static BlockCore createBlockCore(Block block,boolean debug){
        return new BlockCore(block,debug);
    }

    public Block getBlock ( ) {
        return block;
    }
    public static boolean isMushroomBlock(Block block){
        return MMOItems.plugin.getCustomBlocks().isMushroomBlock(block.getType());
    }
}
