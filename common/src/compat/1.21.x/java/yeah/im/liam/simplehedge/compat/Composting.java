package yeah.im.liam.simplehedge.compat;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.ComposterBlock;

public final class Composting {
    private static final float HEDGE_CHANCE = 0.85F;

    private Composting() {
    }

    public static Item.Properties properties(Item.Properties properties) {
        return properties;
    }

    public static void register(Item item) {
        ComposterBlock.COMPOSTABLES.put(item, HEDGE_CHANCE);
    }
}
