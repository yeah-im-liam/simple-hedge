package yeah.im.liam.simplehedge;

import com.google.gson.JsonPrimitive;
import com.mojang.serialization.JsonOps;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class SimpleHedge {
    public static final String MOD_ID = "simple_hedge_mod";

    private SimpleHedge() {
    }

    // Keys and lookups go through codecs so the jars never name the id class, which was renamed in 1.21.11.
    public static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registry, String path) {
        return ResourceKey.codec(registry).parse(JsonOps.INSTANCE, new JsonPrimitive(MOD_ID + ":" + path)).getOrThrow();
    }

    public static <T> Optional<T> lookup(Registry<T> registry, String id) {
        return registry.byNameCodec().parse(JsonOps.INSTANCE, new JsonPrimitive(id)).result();
    }
}
