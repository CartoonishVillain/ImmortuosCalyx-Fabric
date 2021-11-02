package com.cartoonishvillain.immortuoscalyx.client;

import com.cartoonishvillain.immortuoscalyx.Register;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ClientImmortuosInitalizer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(Register.INFECTEDDIVER, RenderInfectedDiverEntity::new);
        EntityRendererRegistry.register(Register.INFECTEDPLAYER, RenderInfectedPlayerEntity::new);
        EntityRendererRegistry.register(Register.INFECTEDHUMAN, RenderInfectedHumanEntity::new);
        EntityRendererRegistry.register(Register.INFECTEDIG, RenderInfectedIGEntity::new);
        EntityRendererRegistry.register(Register.INFECTEDVILLAGER, RenderInfectedVillagerEntity::new);
    }
}
