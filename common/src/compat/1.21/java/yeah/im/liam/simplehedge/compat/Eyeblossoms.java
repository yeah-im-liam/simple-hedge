package yeah.im.liam.simplehedge.compat;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;

public final class Eyeblossoms {
    private Eyeblossoms() {
    }

    // Vanilla eyeblossoms follow night in the overworld up to 1.21.10 (skyDarken >= 4 is Level.isDarkOutside).
    public static boolean shouldBeOpen(ServerLevel level, BlockPos pos, boolean open) {
        if (level.dimension() != Level.OVERWORLD) {
            return open;
        }
        return level.getSkyDarken() >= 4;
    }

    public static SoundEvent sound(boolean open) {
        return open ? SoundEvents.EYEBLOSSOM_OPEN : SoundEvents.EYEBLOSSOM_CLOSE;
    }
}
