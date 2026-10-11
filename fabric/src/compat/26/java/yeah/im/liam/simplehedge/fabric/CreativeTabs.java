package yeah.im.liam.simplehedge.fabric;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
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
        CreativeModeTab tab = FabricCreativeModeTab.builder()
            .title(Component.translatable("itemGroup.simple_hedge_mod.main"))
            .icon(() -> new ItemStack(HedgeBlocks.hedgeItem()))
            .displayItems((parameters, output) -> output.accept(HedgeBlocks.hedgeItem()))
            .build();
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, SimpleHedge.key(Registries.CREATIVE_MODE_TAB, "main"), tab);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(output -> output.insertAfter(Items.FLOWERING_AZALEA, new ItemStack(HedgeBlocks.hedgeItem())));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(output -> output.insertBefore(Items.FLOWER_POT, new ItemStack(HedgeBlocks.hedgeItem())));
    }
}
