package fun.tbcraft.play.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class MathUtil{

    /**
     * Gets the region blocks.
     *
     * @param world the world
     * @param loc1  the loc 1
     * @param loc2  the loc 2
     * @return the region blocks
     */
    public List<Block> getRegionBlocks(World world, Location loc1, Location loc2) {
        List<Block> blocks = new ArrayList<Block>();

        for (double x = loc1.getX(); x <= loc2.getX(); x++) {
            for (double y = loc1.getY(); y <= loc2.getY(); y++) {
                for (double z = loc1.getZ(); z <= loc2.getZ(); z++) {
                    Location loc = new Location(world, x, y, z);
                    blocks.add(loc.getBlock());
                }
            }
        }

        return blocks;
    }


}
