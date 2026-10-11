package yeah.im.liam.simplehedge.compat;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.attribute.EnvironmentAttributes;

public final class Eyeblossoms {
    private Eyeblossoms() {
    }

    public static boolean shouldBeOpen(ServerLevel level, BlockPos pos, boolean open) {
        return level.environmentAttributes().getValue(EnvironmentAttributes.EYEBLOSSOM_OPEN, pos).toBoolean(open);
    }

    public static SoundEvent sound(boolean open) {
        return open ? SoundEvents.EYEBLOSSOM_OPEN : SoundEvents.EYEBLOSSOM_CLOSE;
    }
}
