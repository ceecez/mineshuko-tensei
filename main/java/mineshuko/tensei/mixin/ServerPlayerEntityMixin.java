package mineshuko.tensei.mixin;

import mineshuko.tensei.ServerPlayerDataAccess;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements ServerPlayerDataAccess {
    @Unique
    private int seenCutscene = 0;

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("seenCutscene", seenCutscene);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("seenCutscene")) {
            seenCutscene = nbt.getInt("seenCutscene");
        }
    }

    @Override
    public int getSeenCutscene() {
        return seenCutscene;
    }

    @Override
    public void setSeenCutscene(int seen) {
        this.seenCutscene = seen;
    }
}