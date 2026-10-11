package yeah.im.liam.simplehedge.client;

import java.util.Map;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import yeah.im.liam.simplehedge.mixin.ItemBlockRenderTypesAccessor;

public final class RenderLayers {
    private RenderLayers() {
    }

    public static void useCutout(Iterable<Block> blocks) {
        Map<Block, Object> layers = ItemBlockRenderTypesAccessor.simpleHedge$typeByBlock();
        Object cutout = layers.get(Blocks.AZALEA);
        for (Block block : blocks) {
            layers.put(block, cutout);
        }
    }
}
