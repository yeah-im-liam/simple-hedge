package yeah.im.liam.simplehedge.fabric.client;

import java.util.List;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.world.level.block.Block;

final class HedgeColors {
    private HedgeColors() {
    }

    static void register(List<Block> hedges) {
        BlockColorRegistry.register(List.of(BlockTintSources.foliage()), hedges.toArray(Block[]::new));
    }
}
