package yeah.im.liam.simplehedge.neoforge.client;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import yeah.im.liam.simplehedge.registry.HedgeBlocks;

final class HedgeColors {
    private static final int DEFAULT_FOLIAGE = 0xFF48B518;

    private HedgeColors() {
    }

    static void register(IEventBus modEventBus) {
        modEventBus.addListener((RegisterColorHandlersEvent.Block event) -> event.register(
            (state, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getAverageFoliageColor(level, pos) : DEFAULT_FOLIAGE,
            HedgeBlocks.all().toArray(Block[]::new)
        ));
    }
}
