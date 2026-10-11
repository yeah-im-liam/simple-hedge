package yeah.im.liam.simplehedge.compat;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

// 26.3 made composting an item component; 26.1 and 26.2 still use the composter's chance map.
public final class Composting {
    private static final float HEDGE_CHANCE = 0.85F;
    private static final Object2FloatMap<ItemLike> CHANCES = chances();

    private Composting() {
    }

    public static Item.Properties properties(Item.Properties properties) {
        return CHANCES == null ? properties.compostable(ContextIntProviders.COMPOSTABLE_MEDIUM_HIGH) : properties;
    }

    public static void register(Item item) {
        if (CHANCES != null) {
            CHANCES.put(item, HEDGE_CHANCE);
        }
    }

    @SuppressWarnings("unchecked")
    private static Object2FloatMap<ItemLike> chances() {
        try {
            return (Object2FloatMap<ItemLike>) ComposterBlock.class.getField("COMPOSTABLES").get(null);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }
}
