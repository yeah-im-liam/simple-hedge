package yeah.im.liam.simplehedge.mixin;

import java.util.Map;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemBlockRenderTypes.class)
public interface ItemBlockRenderTypesAccessor {
    // The value type changed from RenderType to ChunkSectionLayer in 1.21.6, so it is left as Object.
    @Accessor("TYPE_BY_BLOCK")
    static Map<Block, Object> simpleHedge$typeByBlock() {
        throw new AssertionError();
    }
}
