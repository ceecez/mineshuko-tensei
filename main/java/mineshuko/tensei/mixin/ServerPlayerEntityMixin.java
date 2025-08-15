package mineshuko.tensei.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Unique
    private boolean mineshuko$cutsceneSeen = false;

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void onWriteCustomData(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("mineshuko_cutscene_seen", mineshuko$cutsceneSeen);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void onReadCustomData(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("mineshuko_cutscene_seen")) {
            mineshuko$cutsceneSeen = nbt.getBoolean("mineshuko_cutscene_seen");
        }
    }

    @Unique
    public boolean mineshuko$hasSeenCutscene() {
        return mineshuko$cutsceneSeen;
    }

    @Unique
    public void mineshuko$setCutsceneSeen(boolean seen) {
        mineshuko$cutsceneSeen = seen;
    }
}