package com.cartoonishvillain.immortuoscalyx.mixin;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.cartoonishvillain.immortuoscalyx.Register.EXTRACT;
import static com.cartoonishvillain.immortuoscalyx.Register.SYRINGE;

@Mixin(Player.class)
public abstract class syringeUsageMixin {


    @Inject(at = @At("TAIL"), method = "interactOn")
    private void interactOn(Entity entity, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir){
        Player player = (Player) (Object) this;
        if(!entity.level.isClientSide() && player.getMainHandItem().getItem().equals(SYRINGE)){
            player.level.playSound(null, player.getX(), player.getY(), player.getZ(), EXTRACT, SoundSource.PLAYERS, 1, 1);
        }
    }

}
