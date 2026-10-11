package yeah.im.liam.simplehedge.neoforge.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import yeah.im.liam.simplehedge.SimpleHedge;

@Mod(value = SimpleHedge.MOD_ID, dist = Dist.CLIENT)
public final class SimpleHedgeNeoForgeClient {
    public SimpleHedgeNeoForgeClient(IEventBus modEventBus) {
        HedgeColors.register(modEventBus);
    }
}
