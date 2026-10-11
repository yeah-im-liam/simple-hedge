package yeah.im.liam.simplehedge.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import yeah.im.liam.simplehedge.compat.Entities;
import yeah.im.liam.simplehedge.registry.HedgeFlower;

public record HedgeContact(BlockState state, boolean witherRose) {
    public static final Vec3 SLOWDOWN = new Vec3(0.92, 0.98, 0.92);

    // Hedges are full cubes, so every hedge in the box's block range overlaps the player.
    public static HedgeContact of(Player player) {
        Level level = Entities.level(player);
        AABB box = player.getBoundingBox().deflate(1.0E-4);
        BlockPos min = BlockPos.containing(box.minX, box.minY, box.minZ);
        BlockPos max = BlockPos.containing(box.maxX, box.maxY, box.maxZ);
        BlockState found = null;
        boolean witherRose = false;
        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            BlockState state = level.getBlockState(pos);
            if (!(state.getBlock() instanceof HedgeBlock)) {
                continue;
            }
            if (found == null) {
                found = state;
            }
            if (state.getBlock() instanceof FloweringHedgeBlock flowering && flowering.flower() == HedgeFlower.WITHER_ROSE) {
                witherRose = true;
            }
        }
        return found == null ? null : new HedgeContact(found, witherRose);
    }
}
