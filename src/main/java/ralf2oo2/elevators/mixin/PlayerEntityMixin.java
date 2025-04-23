package ralf2oo2.elevators.mixin;

import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ralf2oo2.elevators.client.ElevatorsClient;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    void elevators_tick(CallbackInfo ci){
        if(PlayerEntity.class.cast(this) instanceof ClientPlayerEntity player && player.world.isRemote && ElevatorsClient.multiplayerDelayTicks > 0){
            ElevatorsClient.multiplayerDelayTicks--;
        }
    }
}
