package yeah.im.liam.simplehedge.neoforge;

import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import yeah.im.liam.simplehedge.SimpleHedge;
import yeah.im.liam.simplehedge.registry.HedgeBlocks;
import yeah.im.liam.simplehedge.registry.Registrar;

@Mod(SimpleHedge.MOD_ID)
public final class SimpleHedgeNeoForge {
    public SimpleHedgeNeoForge(IEventBus modEventBus) {
        modEventBus.addListener(SimpleHedgeNeoForge::register);
        modEventBus.addListener(SimpleHedgeNeoForge::fillCreativeTabs);
    }

    private static void register(RegisterEvent event) {
        event.register(Registries.SOUND_EVENT, helper -> HedgeBlocks.registerSounds(registrar(Registries.SOUND_EVENT, helper)));
        event.register(Registries.BLOCK, helper -> HedgeBlocks.registerBlocks(registrar(Registries.BLOCK, helper)));
        event.register(Registries.ITEM, helper -> HedgeBlocks.registerItems(registrar(Registries.ITEM, helper)));
        event.register(Registries.CREATIVE_MODE_TAB, helper -> helper.register(
            SimpleHedge.key(Registries.CREATIVE_MODE_TAB, "main"),
            CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.simple_hedge_mod.main"))
                .icon(() -> new ItemStack(HedgeBlocks.hedgeItem()))
                .displayItems((parameters, output) -> output.accept(HedgeBlocks.hedgeItem()))
                .build()
        ));
    }

    private static void fillCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        ItemStack hedge = new ItemStack(HedgeBlocks.hedgeItem());
        if (event.getTabKey().equals(CreativeModeTabs.NATURAL_BLOCKS)) {
            event.insertAfter(new ItemStack(Items.FLOWERING_AZALEA), hedge, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        } else if (event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
            event.insertBefore(new ItemStack(Items.FLOWER_POT), hedge, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    private static <T> Registrar<T> registrar(ResourceKey<? extends Registry<T>> registry, RegisterEvent.RegisterHelper<T> helper) {
        return new Registrar<>() {
            @Override
            public <V extends T> V register(String path, Function<ResourceKey<T>, V> factory) {
                ResourceKey<T> key = SimpleHedge.key(registry, path);
                V value = factory.apply(key);
                helper.register(key, value);
                return value;
            }
        };
    }
}
