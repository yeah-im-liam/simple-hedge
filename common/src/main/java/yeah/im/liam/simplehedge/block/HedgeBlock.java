package yeah.im.liam.simplehedge.block;

import com.mojang.serialization.MapCodec;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import yeah.im.liam.simplehedge.compat.Entities;
import yeah.im.liam.simplehedge.compat.HedgeBlockBase;
import yeah.im.liam.simplehedge.registry.HedgeBlocks;

public class HedgeBlock extends HedgeBlockBase {
    private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

    public HedgeBlock(Properties properties) {
        super(properties);
    }

    // Abstract in Block until 26.3, which dropped block codecs.
    protected MapCodec<? extends Block> codec() {
        return MapCodec.unit(this);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return canPassThrough(context) ? Shapes.empty() : SHAPE;
    }

    @Override
    protected VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        return below.getBlock() instanceof HedgeBlock
            || below.is(BlockTags.DIRT)
            || below.is(Blocks.CLAY)
            || below.is(Blocks.FARMLAND);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return type == PathComputationType.AIR || super.isPathfindable(state, type);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        return List.of(new ItemStack(HedgeBlocks.hedgeItem()));
    }

    @Override
    protected boolean useItem(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand) {
        FloweringHedgeBlock flowering = HedgeBlocks.floweringFor(stack.getItem());
        if (flowering == null) {
            return false;
        }
        if (level instanceof ServerLevel serverLevel) {
            level.setBlock(pos, flowering.defaultBlockState(), Block.UPDATE_ALL);
            Sounds.play(serverLevel, pos, SoundEvents.MOSS_PLACE);
            stack.consume(1, player);
        }
        return true;
    }

    private static boolean canPassThrough(CollisionContext context) {
        if (!(context instanceof EntityCollisionContext entityContext)) {
            return false;
        }
        Entity entity = entityContext.getEntity();
        return Entities.isBee(entity)
            || entity instanceof Player player && (player.isShiftKeyDown() || player.isCrouching());
    }
}
