package yeah.im.liam.simplehedge.registry;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import yeah.im.liam.simplehedge.SimpleHedge;
import yeah.im.liam.simplehedge.block.FloweringHedgeBlock;
import yeah.im.liam.simplehedge.block.HedgeBlock;
import yeah.im.liam.simplehedge.compat.Composting;
import yeah.im.liam.simplehedge.compat.Props;
import yeah.im.liam.simplehedge.item.HedgeItem;

public final class HedgeBlocks {
    private static final Map<HedgeFlower, FloweringHedgeBlock> FLOWERING = new EnumMap<>(HedgeFlower.class);
    private static final Map<Item, FloweringHedgeBlock> BY_FLOWER = new HashMap<>();
    private static HedgeBlock hedge;
    private static Item hedgeItem;
    private static SoundEvent shearSound;

    private HedgeBlocks() {
    }

    public static void registerBlocks(Registrar<Block> blocks) {
        hedge = blocks.register("hedge", key -> new HedgeBlock(properties(key)));
        for (HedgeFlower flower : HedgeFlower.values()) {
            Optional<Item> flowerItem = SimpleHedge.lookup(BuiltInRegistries.ITEM, flower.flowerId());
            if (flowerItem.isEmpty()) {
                continue;
            }
            FloweringHedgeBlock block = blocks.register(flower.blockPath(), key -> new FloweringHedgeBlock(floweringProperties(flower, key), flower, flowerItem.get()));
            FLOWERING.put(flower, block);
            BY_FLOWER.put(flowerItem.get(), block);
        }
    }

    public static void registerItems(Registrar<Item> items) {
        hedgeItem = items.register("hedge", key -> new HedgeItem(hedge, Composting.properties(Props.item(key))));
        Composting.register(hedgeItem);
    }

    public static void registerSounds(Registrar<SoundEvent> sounds) {
        shearSound = sounds.register("hedge_shear", key -> soundEvent("hedge_shear"));
    }

    public static HedgeBlock hedge() {
        return hedge;
    }

    public static Item hedgeItem() {
        return hedgeItem;
    }

    public static SoundEvent shearSound() {
        return shearSound;
    }

    public static FloweringHedgeBlock flowering(HedgeFlower flower) {
        return FLOWERING.get(flower);
    }

    public static FloweringHedgeBlock floweringFor(Item flower) {
        return BY_FLOWER.get(flower);
    }

    public static List<Block> all() {
        List<Block> blocks = new ArrayList<>();
        blocks.add(hedge);
        blocks.addAll(FLOWERING.values());
        return Collections.unmodifiableList(blocks);
    }

    public static List<FloweringHedgeBlock> allFlowering() {
        return List.copyOf(FLOWERING.values());
    }

    private static SoundEvent soundEvent(String path) {
        JsonObject json = new JsonObject();
        json.addProperty("sound_id", SimpleHedge.MOD_ID + ":" + path);
        return SoundEvent.DIRECT_CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();
    }

    private static BlockBehaviour.Properties properties(ResourceKey<Block> key) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA)
            .strength(0.4F)
            .noOcclusion()
            .dynamicShape()
            .isRedstoneConductor((state, level, pos) -> false);
        return Props.block(properties, key);
    }

    private static BlockBehaviour.Properties floweringProperties(HedgeFlower flower, ResourceKey<Block> key) {
        BlockBehaviour.Properties properties = properties(key);
        int light = flower.lightLevel();
        if (light > 0) {
            properties.lightLevel(state -> light);
        }
        if (flower.isEyeblossom()) {
            properties.randomTicks();
        }
        return properties;
    }
}
