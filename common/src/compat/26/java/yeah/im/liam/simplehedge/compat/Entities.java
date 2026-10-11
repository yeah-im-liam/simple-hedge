package yeah.im.liam.simplehedge.compat;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.level.Level;

public final class Entities {
    private Entities() {
    }

    public static boolean isBee(Entity entity) {
        return entity instanceof Bee;
    }

    public static Level level(Entity entity) {
        return entity.level();
    }
}
