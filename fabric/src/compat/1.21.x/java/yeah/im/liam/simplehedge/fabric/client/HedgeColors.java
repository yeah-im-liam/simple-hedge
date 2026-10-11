package yeah.im.liam.simplehedge.fabric.client;

import java.util.List;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.block.Block;

final class HedgeColors {
    private static final int DEFAULT_FOLIAGE = 0xFF48B518;

    private HedgeColors() {
    }

    static void register(List<Block> hedges) {
        ColorProviderRegistry.BLOCK.register(
            (state, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getAverageFoliageColor(level, pos) : DEFAULT_FOLIAGE,
            hedges.toArray(Block[]::new)
        );
    }
}
