package yeah.im.liam.simplehedge.compat;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import yeah.im.liam.simplehedge.mixin.EntityAccessor;

public final class Entities {
    private Entities() {
    }

    // The bee class moved package in 1.21.11; its entity type did not.
    public static boolean isBee(Entity entity) {
        return entity != null && entity.getType() == EntityType.BEE;
    }

    public static Level level(Entity entity) {
        return ((EntityAccessor) entity).simpleHedge$level();
    }
}
