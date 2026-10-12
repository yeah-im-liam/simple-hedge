package yeah.im.liam.simplehedge.compat;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class Soil {
    private Soil() {
    }

    // The same ground azalea grows on.
    public static boolean holdsHedges(BlockState state) {
        return state.is(BlockTags.DIRT) || state.is(Blocks.CLAY) || state.is(Blocks.FARMLAND);
    }
}
