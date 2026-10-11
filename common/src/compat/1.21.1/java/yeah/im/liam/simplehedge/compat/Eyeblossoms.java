package yeah.im.liam.simplehedge.compat;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;

// Eyeblossoms arrived in 1.21.4, so these are never reached here.
public final class Eyeblossoms {
    private Eyeblossoms() {
    }

    public static boolean shouldBeOpen(ServerLevel level, BlockPos pos, boolean open) {
        return open;
    }

    public static SoundEvent sound(boolean open) {
        throw new UnsupportedOperationException();
    }
}
