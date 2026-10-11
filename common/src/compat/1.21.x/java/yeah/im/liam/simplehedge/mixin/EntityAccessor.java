package yeah.im.liam.simplehedge.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

// Entity.level() got a new intermediary name in 1.21.9; the field kept its name.
@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor("level")
    Level simpleHedge$level();
}
