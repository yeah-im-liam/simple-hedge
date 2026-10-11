package yeah.im.liam.simplehedge.compat;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class Props {
    private Props() {
    }

    public static BlockBehaviour.Properties block(BlockBehaviour.Properties properties, ResourceKey<Block> key) {
        return properties.setId(key);
    }

    public static Item.Properties item(ResourceKey<Item> key) {
        return new Item.Properties().setId(key).useBlockDescriptionPrefix();
    }
}
