package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.Register;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedPlayerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.cartoonishvillain.immortuoscalyx.component.ComponentTicker.infectedEntityConverter;

@Mixin(LivingEntity.class)
public class InfectionDeathMixin {
    @Inject(at = @At("HEAD"), method = "die")
    private void die(DamageSource damageSource, CallbackInfo ci){
        LivingEntity entity = ((LivingEntity) (Object) this);
        infectedEntityConverter(damageSource, entity);
    }

}
