package yeah.im.liam.simplehedge.item;

import java.util.Map;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import yeah.im.liam.simplehedge.registry.HedgeBlocks;

public class HedgeItem extends BlockItem {
    public HedgeItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    // Flowering hedges count as hedges, so picking one gives a hedge.
    @Override
    public void registerBlocks(Map<Block, Item> blockToItem, Item item) {
        super.registerBlocks(blockToItem, item);
        for (Block block : HedgeBlocks.allFlowering()) {
            blockToItem.put(block, item);
        }
    }
}
