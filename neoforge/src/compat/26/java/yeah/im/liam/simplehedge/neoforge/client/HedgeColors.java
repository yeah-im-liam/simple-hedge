package yeah.im.liam.simplehedge.neoforge.client;

import java.util.List;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import yeah.im.liam.simplehedge.registry.HedgeBlocks;

final class HedgeColors {
    private HedgeColors() {
    }

    static void register(IEventBus modEventBus) {
        modEventBus.addListener((RegisterColorHandlersEvent.BlockTintSources event) -> event.register(
            List.of(BlockTintSources.foliage()),
            HedgeBlocks.all().toArray(Block[]::new)
        ));
    }
}
