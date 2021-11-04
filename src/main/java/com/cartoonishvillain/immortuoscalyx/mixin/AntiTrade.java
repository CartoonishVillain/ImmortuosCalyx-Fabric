package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.ImmortuosCalyx;
import com.cartoonishvillain.immortuoscalyx.Register;
import com.cartoonishvillain.immortuoscalyx.component.InfectionComponent;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedEntity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.cartoonishvillain.immortuoscalyx.Register.EXTRACT;
import static com.cartoonishvillain.immortuoscalyx.Register.SYRINGE;
import static com.cartoonishvillain.immortuoscalyx.component.ComponentStarter.INFECTION;

@Mixin(Player.class)
public class AntiTrade {


    @Inject(at = @At("HEAD"), method = "interactOn", cancellable = true)
    private void interactOn(Entity entity, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir){
        if(!entity.level.isClientSide()){
            if(entity instanceof Villager villager){
                InfectionComponent h = INFECTION.get(villager);
                boolean noTrade = false;
                if(h.isFollower() && h.getInfectionProgress() >= ImmortuosCalyx.config.entityToggles.VILLAGERFOLLOWERIMMUNITY * ImmortuosCalyx.config.entityToggles.VILLAGERNOTRADE) noTrade = true;
                else if(!h.isFollower() && h.getInfectionProgress() >= ImmortuosCalyx.config.entityToggles.VILLAGERNOTRADE) noTrade = true;
                if(noTrade) {
                    villager.setUnhappyCounter(40);
                    villager.getCommandSenderWorld().playSound(null, villager.getX(), villager.getY(), villager.getZ(), Register.VILIDLE, SoundSource.NEUTRAL, 1f, 1f);
                    cir.cancel();
                }
            }
        }
    }
}

