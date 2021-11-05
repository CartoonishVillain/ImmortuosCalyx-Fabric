package com.cartoonishvillain.immortuoscalyx.client;

import com.cartoonishvillain.immortuoscalyx.ImmortuosCalyx;
import com.cartoonishvillain.immortuoscalyx.Register;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.resources.ResourceLocation;

public class ClientImmortuosInitalizer implements ClientModInitializer {
    public static boolean chatDisabledEnabled;
    public static boolean chatScrambledEnabled;
    public static boolean chatScreamingEnabled;
    public static int chatEffectMark;

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(Register.INFECTEDDIVER, RenderInfectedDiverEntity::new);
        EntityRendererRegistry.register(Register.INFECTEDPLAYER, RenderInfectedPlayerEntity::new);
        EntityRendererRegistry.register(Register.INFECTEDHUMAN, RenderInfectedHumanEntity::new);
        EntityRendererRegistry.register(Register.INFECTEDIG, RenderInfectedIGEntity::new);
        EntityRendererRegistry.register(Register.INFECTEDVILLAGER, RenderInfectedVillagerEntity::new);
        chatDisabledEnabled = ImmortuosCalyx.config.playerToggles.ANTICHAT;
        chatScrambledEnabled = ImmortuosCalyx.config.otherDetails.FORMATTEDINFECTCHAT;
        chatScreamingEnabled = ImmortuosCalyx.config.playerToggles.INFECTEDCHATNOISE;
        chatEffectMark = ImmortuosCalyx.config.playerSymptomProgression.EFFECTCHAT;

        registerPackets();
    }

    @Environment(EnvType.CLIENT)
    public static void registerPackets(){
        ClientPlayNetworking.registerGlobalReceiver(new ResourceLocation("immortuoscalyx:configupdate"), (client, handler, buf, responseSender) -> {
            chatDisabledEnabled = buf.readBoolean();
            chatScrambledEnabled = buf.readBoolean();
            chatScreamingEnabled = buf.readBoolean();
            chatEffectMark = buf.readInt();
        });
    }
}
