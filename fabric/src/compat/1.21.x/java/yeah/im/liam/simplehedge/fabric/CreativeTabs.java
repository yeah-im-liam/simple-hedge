package yeah.im.liam.simplehedge.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import yeah.im.liam.simplehedge.SimpleHedge;
import yeah.im.liam.simplehedge.registry.HedgeBlocks;

final class CreativeTabs {
    private CreativeTabs() {
    }

    static void register() {
        CreativeModeTab tab = FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.simple_hedge_mod.main"))
            .icon(() -> new ItemStack(HedgeBlocks.hedgeItem()))
            .displayItems((parameters, output) -> output.accept(HedgeBlocks.hedgeItem()))
            .build();
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, SimpleHedge.key(Registries.CREATIVE_MODE_TAB, "main"), tab);

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.addAfter(Items.FLOWERING_AZALEA, HedgeBlocks.hedgeItem()));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> entries.addBefore(Items.FLOWER_POT, HedgeBlocks.hedgeItem()));
    }
}
