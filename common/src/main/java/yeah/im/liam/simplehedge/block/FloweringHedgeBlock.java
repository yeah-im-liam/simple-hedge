package yeah.im.liam.simplehedge.block;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import yeah.im.liam.simplehedge.SimpleHedge;
import yeah.im.liam.simplehedge.compat.Eyeblossoms;
import yeah.im.liam.simplehedge.registry.HedgeBlocks;
import yeah.im.liam.simplehedge.registry.HedgeFlower;

public class FloweringHedgeBlock extends HedgeBlock {
    private static Block vanillaOpenEyeblossom;
    private static Block vanillaClosedEyeblossom;

    private final HedgeFlower flower;
    private final Item flowerItem;

    public FloweringHedgeBlock(Properties properties, HedgeFlower flower, Item flowerItem) {
        super(properties);
        this.flower = flower;
        this.flowerItem = flowerItem;
    }

    public HedgeFlower flower() {
        return this.flower;
    }

    public Item flowerItem() {
        return this.flowerItem;
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        return List.of(new ItemStack(HedgeBlocks.hedgeItem()), new ItemStack(this.flowerItem));
    }

    @Override
    protected boolean useItem(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand) {
        if (!(stack.getItem() instanceof ShearsItem)) {
            return false;
        }
        if (level instanceof ServerLevel serverLevel) {
            level.setBlock(pos, HedgeBlocks.hedge().defaultBlockState(), Block.UPDATE_ALL);
            Sounds.play(serverLevel, pos, HedgeBlocks.shearSound());
            Block.popResource(level, pos, new ItemStack(this.flowerItem));
            stack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
        }
        return true;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (this.flower != HedgeFlower.WITHER_ROSE) {
            return;
        }
        for (int i = 0; i < 3; i++) {
            if (random.nextBoolean()) {
                level.addParticle(ParticleTypes.SMOKE, pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        updateEyeblossom(state, level, pos, random);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        updateEyeblossom(state, level, pos, random);
    }

    private void updateEyeblossom(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!this.flower.isEyeblossom()) {
            return;
        }
        boolean open = this.flower == HedgeFlower.OPEN_EYEBLOSSOM;
        if (Eyeblossoms.shouldBeOpen(level, pos, open) == open) {
            return;
        }
        FloweringHedgeBlock next = HedgeBlocks.flowering(open ? HedgeFlower.CLOSED_EYEBLOSSOM : HedgeFlower.OPEN_EYEBLOSSOM);
        if (next == null) {
            return;
        }
        level.setBlock(pos, next.defaultBlockState(), Block.UPDATE_ALL);
        Sounds.play(level, pos, Eyeblossoms.sound(!open));
        wakeNearbyEyeblossoms(level, pos, open, random);
    }

    // Like vanilla eyeblossoms: nearby ones in the same old state follow shortly after, closer ones first.
    private static void wakeNearbyEyeblossoms(ServerLevel level, BlockPos origin, boolean wasOpen, RandomSource random) {
        Block vanilla = vanillaEyeblossom(wasOpen);
        Block hedge = HedgeBlocks.flowering(wasOpen ? HedgeFlower.OPEN_EYEBLOSSOM : HedgeFlower.CLOSED_EYEBLOSSOM);
        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-3, -2, -3), origin.offset(3, 2, 3))) {
            if (pos.equals(origin)) {
                continue;
            }
            Block block = level.getBlockState(pos).getBlock();
            if (block != vanilla && block != hedge) {
                continue;
            }
            double distance = Math.sqrt(origin.distSqr(pos));
            int min = (int) (distance * 5.0);
            int max = (int) (distance * 10.0);
            level.scheduleTick(pos.immutable(), block, min + random.nextInt(max - min + 1));
        }
    }

    private static Block vanillaEyeblossom(boolean open) {
        if (vanillaOpenEyeblossom == null) {
            vanillaOpenEyeblossom = SimpleHedge.lookup(BuiltInRegistries.BLOCK, "minecraft:open_eyeblossom").orElse(null);
            vanillaClosedEyeblossom = SimpleHedge.lookup(BuiltInRegistries.BLOCK, "minecraft:closed_eyeblossom").orElse(null);
        }
        return open ? vanillaOpenEyeblossom : vanillaClosedEyeblossom;
    }
}
