package com.cartoonishvillain.immortuoscalyx.networking;

import com.cartoonishvillain.immortuoscalyx.ImmortuosCalyx;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ConfigPacket {

    public static void send(ServerPlayer player, FriendlyByteBuf byteBuf){
        byteBuf.writeBoolean(ImmortuosCalyx.config.playerToggles.ANTICHAT);
        byteBuf.writeBoolean(ImmortuosCalyx.config.otherDetails.FORMATTEDINFECTCHAT);
        byteBuf.writeBoolean(ImmortuosCalyx.config.playerToggles.INFECTEDCHATNOISE);
        byteBuf.writeInt(ImmortuosCalyx.config.playerSymptomProgression.EFFECTCHAT);
        ServerPlayNetworking.send(player, new ResourceLocation("immortuoscalyx:configupdate"), byteBuf);
    }
}
