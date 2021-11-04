package com.cartoonishvillain.immortuoscalyx.networking;

import com.cartoonishvillain.immortuoscalyx.ImmortuosCalyx;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class SoundPacket {
    public static void encodeAndSend(FriendlyByteBuf buffer){
        ClientPlayNetworking.send(new ResourceLocation(ImmortuosCalyx.MOD_ID, "soundpacket"), buffer);
    }
}
