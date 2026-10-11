package yeah.im.liam.simplehedge.fabric;

import java.util.function.Function;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import yeah.im.liam.simplehedge.SimpleHedge;
import yeah.im.liam.simplehedge.registry.HedgeBlocks;
import yeah.im.liam.simplehedge.registry.Registrar;

public final class SimpleHedgeFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        HedgeBlocks.registerSounds(registrar(BuiltInRegistries.SOUND_EVENT));
        HedgeBlocks.registerBlocks(registrar(BuiltInRegistries.BLOCK));
        HedgeBlocks.registerItems(registrar(BuiltInRegistries.ITEM));
        CreativeTabs.register();
    }

    private static <T> Registrar<T> registrar(Registry<T> registry) {
        return new Registrar<>() {
            @Override
            public <V extends T> V register(String path, Function<ResourceKey<T>, V> factory) {
                ResourceKey<T> key = SimpleHedge.key(registry.key(), path);
                V value = factory.apply(key);
                Registry.register(registry, key, value);
                return value;
            }
        };
    }
}
