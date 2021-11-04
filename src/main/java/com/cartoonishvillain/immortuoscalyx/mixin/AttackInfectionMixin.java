package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.component.InfectionHandler;
import com.cartoonishvillain.immortuoscalyx.entities.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.cartoonishvillain.immortuoscalyx.component.ComponentTicker.ValidPlayer;

@Mixin(LivingEntity.class)
public class AttackInfectionMixin {
    @Inject(at = @At("HEAD"), method = "doHurtTarget")
    private void attack(Entity entity, CallbackInfoReturnable<Boolean> cir){
        if(!entity.level.isClientSide && entity instanceof LivingEntity victim) {
            LivingEntity aggro = ((LivingEntity) (Object) this);

            if(aggro instanceof Player && ValidPlayer((Player) aggro)){
                if(victim instanceof Player) InfectionHandler.infectPlayerByPlayer((Player) aggro,(Player) victim, 1);
                else InfectionHandler.infectEntityByPlayer((Player) aggro, victim, 1);
            }
            else if (aggro instanceof InfectedHumanEntity || aggro instanceof InfectedDiverEntity || aggro instanceof InfectedPlayerEntity) InfectionHandler.infectEntity(victim, 95, 1);
            else if (aggro instanceof InfectedIGEntity) InfectionHandler.infectEntity(victim, 75, 1);
            else if (aggro instanceof InfectedVillagerEntity){
                if(victim instanceof Villager || victim instanceof AbstractGolem) InfectionHandler.infectEntity(victim, 100, 1);
                else InfectionHandler.infectEntity(victim, 55, 1);
            }
            else if(!(aggro instanceof Player)) InfectionHandler.infectEntity(aggro, victim, 1);
        }
    }
}
