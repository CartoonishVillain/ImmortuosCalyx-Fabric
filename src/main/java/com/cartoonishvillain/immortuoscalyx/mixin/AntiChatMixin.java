package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.ImmortuosCalyx;
import com.cartoonishvillain.immortuoscalyx.component.InfectionComponent;
import com.cartoonishvillain.immortuoscalyx.networking.ObfuscatedChatPacket;
import com.cartoonishvillain.immortuoscalyx.networking.SoundPacket;
import io.netty.buffer.Unpooled;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.cartoonishvillain.immortuoscalyx.component.ComponentStarter.INFECTION;
import static com.cartoonishvillain.immortuoscalyx.component.ComponentTicker.ValidPlayer;

@Mixin(LocalPlayer.class)
public class AntiChatMixin {

    @Inject(at = @At("HEAD"), method = "chat", cancellable = true)
    private void chat(String string, CallbackInfo ci){
        Player player = ((LocalPlayer) (Object) this);
        if(player != null && ValidPlayer(player)) {
            InfectionComponent h = INFECTION.get(player);
            String name = player.getName().getString();
            String format = "<" + name + "> ";
            if (!(string.charAt(0) == '/')) {

                if ((h.getInfectionProgress() >= ImmortuosCalyx.config.playerSymptomProgression.EFFECTCHAT && ImmortuosCalyx.config.playerToggles.INFECTEDCHATNOISE))
                    SoundPacket.encodeAndSend(new FriendlyByteBuf(Unpooled.buffer()));

                if (h.getInfectionProgress() >= ImmortuosCalyx.config.playerSymptomProgression.EFFECTCHAT && ImmortuosCalyx.config.playerToggles.ANTICHAT && ImmortuosCalyx.config.otherDetails.FORMATTEDINFECTCHAT) {
//                playerList.broadcastMessage((new TextComponent(format + ChatFormatting.OBFUSCATED + component)), ChatType.CHAT, uUID);
                    ObfuscatedChatPacket.encodeAndSend(format, string, new FriendlyByteBuf(Unpooled.buffer()));
                    ci.cancel();
                }
                if (h.getInfectionProgress() >= ImmortuosCalyx.config.playerSymptomProgression.EFFECTCHAT && ImmortuosCalyx.config.playerToggles.ANTICHAT && !ImmortuosCalyx.config.otherDetails.FORMATTEDINFECTCHAT) {
                    ci.cancel();
                }
                ;//if the player's infection is @ or above 40%, they can no longer speak in text chat.

            }
        }
    }

}
