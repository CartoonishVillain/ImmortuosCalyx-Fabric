package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.Register;
import com.cartoonishvillain.immortuoscalyx.component.InfectionComponent;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedEntity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Slime;
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
public abstract class SyringeUsageMixin {


    @Inject(at = @At("TAIL"), method = "interactOn")
    private void interactOn(Entity entity, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir){
        Player player = (Player) (Object) this;
        if(!entity.level.isClientSide() && player.getMainHandItem().getItem().equals(SYRINGE) && interactionHand.equals(InteractionHand.MAIN_HAND)){
            boolean extract = false;
            if(entity instanceof Slime){
                extract = true;
                player.getMainHandItem().shrink(1);
                ItemStack itemStack = new ItemStack(Register.GENERALANTIPARASITIC);
                player.getInventory().add(itemStack);
            }
            if(entity instanceof InfectedEntity || INFECTION.get(entity).getInfectionProgress() > 50){
                extract = true;
                player.getMainHandItem().shrink(1);
                ItemStack itemStack = new ItemStack(Register.IMMORTUOSCALYXEGGS);
                player.getInventory().add(itemStack);
            }

            if (extract) player.level.playSound(null, player.getX(), player.getY(), player.getZ(), EXTRACT, SoundSource.PLAYERS, 1, 1);
        }
    }

}
