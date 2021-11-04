package com.cartoonishvillain.immortuoscalyx.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.cartoonishvillain.immortuoscalyx.component.ComponentTicker.infectedEntityConverter;

@Mixin(ServerPlayer.class)
public class PlayerInfectionDeathMixin {
    @Inject(at = @At("HEAD"), method = "die")
    private void die(DamageSource damageSource, CallbackInfo ci){
        LivingEntity entity = ((LivingEntity) (Object) this);
        infectedEntityConverter(damageSource, entity);
    }

}
