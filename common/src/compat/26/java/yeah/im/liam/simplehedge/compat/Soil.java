package yeah.im.liam.simplehedge.compat;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;

public final class Soil {
    private Soil() {
    }

    // 26.1 split grass, moss and mud out of #dirt; azalea's own tag still covers them.
    public static boolean holdsHedges(BlockState state) {
        return state.is(BlockTags.SUPPORTS_AZALEA);
    }
}
