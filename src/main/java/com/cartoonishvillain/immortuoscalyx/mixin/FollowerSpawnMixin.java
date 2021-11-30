package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.ImmortuosCalyx;
import com.cartoonishvillain.immortuoscalyx.component.InfectionComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.cartoonishvillain.immortuoscalyx.component.ComponentStarter.INFECTION;

@Mixin(ServerLevel.class)
public class FollowerSpawnMixin {
    @Inject(at = @At("HEAD"), method = "addFreshEntity")
    private void ImmortuosaddFreshEntity(Entity entity, CallbackInfoReturnable<Boolean> cir){
        if(entity instanceof Villager && !((Villager) entity).isBaby()){
            InfectionComponent h = INFECTION.get(entity);
            if(entity.level.getRandom().nextInt(ImmortuosCalyx.config.entityToggles.VILLAGERFOLLOWERCHANCE) < 2){
                h.setInfectionProgressIfLower(1);
                h.setFollower(true);}
        }
    }
}
