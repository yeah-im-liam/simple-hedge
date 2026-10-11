package yeah.im.liam.simplehedge.mixin;

import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yeah.im.liam.simplehedge.block.HedgeContact;
import yeah.im.liam.simplehedge.compat.Entities;

// Players crouch through hedges: slowed down, kept crouched until clear of them, and withered by wither rose hedges.
@Mixin(Player.class)
public abstract class PlayerMixin {
    @Unique
    private int simpleHedge$graceTicks;

    @Inject(method = "tick", at = @At("TAIL"))
    private void simpleHedge$moveThroughHedges(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (player.isSpectator()) {
            this.simpleHedge$graceTicks = 0;
            return;
        }
        HedgeContact contact = HedgeContact.of(player);
        if (contact != null) {
            this.simpleHedge$graceTicks = 2;
            player.setShiftKeyDown(true);
            player.setSprinting(false);
            player.makeStuckInBlock(contact.state(), HedgeContact.SLOWDOWN);
            Level level = Entities.level(player);
            if (contact.witherRose()
                && !level.isClientSide()
                && level.getDifficulty() != Difficulty.PEACEFUL
                && !player.getAbilities().invulnerable) {
                player.addEffect(new MobEffectInstance(MobEffects.WITHER, 40));
            }
        } else if (this.simpleHedge$graceTicks > 0) {
            this.simpleHedge$graceTicks--;
            player.setShiftKeyDown(true);
            player.setSprinting(false);
        }
    }

    @Inject(method = "canPlayerFitWithinBlocksAndEntitiesWhen", at = @At("HEAD"), cancellable = true)
    private void simpleHedge$stayCrouchedInHedges(Pose pose, CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) (Object) this;
        if (pose == Pose.STANDING && !player.isSpectator()
            && (this.simpleHedge$graceTicks > 0 || HedgeContact.of(player) != null)) {
            cir.setReturnValue(false);
        }
    }
}
