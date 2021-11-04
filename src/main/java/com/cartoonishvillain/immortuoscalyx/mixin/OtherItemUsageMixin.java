package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.Items.BaseItems;
import com.cartoonishvillain.immortuoscalyx.Items.ItemFunctionality;
import com.cartoonishvillain.immortuoscalyx.Register;
import com.cartoonishvillain.immortuoscalyx.component.ItemUsages;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.cartoonishvillain.immortuoscalyx.Register.EXTRACT;
import static com.cartoonishvillain.immortuoscalyx.Register.SYRINGE;
import static com.cartoonishvillain.immortuoscalyx.component.ComponentStarter.INFECTION;

@Mixin(Player.class)
public class OtherItemUsageMixin {


    @Inject(at = @At("HEAD"), method = "attack", cancellable = true)
    private void attack(Entity entity, CallbackInfo ci){
        Player player = (Player) (Object) this;
        if(!entity.level.isClientSide() && player.getMainHandItem().getItem() instanceof BaseItems && entity instanceof LivingEntity){
            if(((BaseItems) player.getMainHandItem().getItem()).getItemFunctionality().equals(ItemFunctionality.ANTIBIOTIC)){
                ItemUsages.useAntiParasitic(player.getMainHandItem(), (LivingEntity) entity);
                ci.cancel();
            } else if(((BaseItems) player.getMainHandItem().getItem()).getItemFunctionality().equals(ItemFunctionality.CALYXIDE)){
                ItemUsages.useCalyxide(player.getMainHandItem(), (LivingEntity) entity);
                ci.cancel();
            } else if(((BaseItems) player.getMainHandItem().getItem()).getItemFunctionality().equals(ItemFunctionality.EGGS)){
                ItemUsages.useImmortuosCalyxEggs(player.getMainHandItem(), (LivingEntity) entity);
                ci.cancel();
            } else if(((BaseItems) player.getMainHandItem().getItem()).getItemFunctionality().equals(ItemFunctionality.SCANNER)){
                ItemUsages.useScanner((LivingEntity) entity, player);
                ci.cancel();
            }
        }
    }

}
