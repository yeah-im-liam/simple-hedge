package yeah.im.liam.simplehedge.fabric.client;

import java.util.List;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.world.level.block.Block;
import yeah.im.liam.simplehedge.client.RenderLayers;
import yeah.im.liam.simplehedge.registry.HedgeBlocks;

public final class SimpleHedgeFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        List<Block> hedges = HedgeBlocks.all();
        RenderLayers.useCutout(hedges);
        HedgeColors.register(hedges);
    }
}
