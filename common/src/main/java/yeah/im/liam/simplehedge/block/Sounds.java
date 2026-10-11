package yeah.im.liam.simplehedge.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

final class Sounds {
    private Sounds() {
    }

    // Sends what Level.playSound sends; that method changed its signature in 1.21.5.
    static void play(ServerLevel level, BlockPos pos, SoundEvent sound) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;
        ClientboundSoundPacket packet = new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(sound), SoundSource.BLOCKS, x, y, z, 1.0F, 1.0F, level.getRandom().nextLong());
        level.getServer().getPlayerList().broadcast(null, x, y, z, sound.getRange(1.0F), level.dimension(), packet);
    }
}
