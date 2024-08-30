package com.seadoggie.worldborderfight.mixin;

import com.seadoggie.worldborderfight.event.ServerPlayerDeathCallback;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Debug(export = true) is for testing, shows the class with the injected code. Exported to: /worldborder-fight/run/.mixin.out/class/...
@Debug(export = true)
@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(at = @At(value = "HEAD"), method = "onDeath(Lnet/minecraft/entity/damage/DamageSource;)V")
    private void onPlayerDeathMixin(final DamageSource damageSource, final CallbackInfo info){
        ServerPlayerDeathCallback.EVENT.invoker().onPlayerDeath((ServerPlayerEntity) (Object)this, damageSource);
    }
}
