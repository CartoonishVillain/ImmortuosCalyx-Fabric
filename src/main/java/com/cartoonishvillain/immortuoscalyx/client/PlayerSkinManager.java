package com.cartoonishvillain.immortuoscalyx.client;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.SkullBlockEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
    All functions in this class were sourced from HenkleMax's code for their corelib here: https://github.com/henkelmax/corelib
    This code is used with permission, and I thank them for said permission!
 */
public class PlayerSkinManager {
    private static HashMap<String, GameProfile> players = new HashMap<>();

    public static GameProfile getGameProfile(UUID uuid, String name) {
        if (players.containsKey(uuid.toString())) {
            return players.get(uuid.toString());
        } else {
            GameProfile profile = new GameProfile(uuid, name);
            SkullBlockEntity.updateGameprofile(profile, updatedGameProfile ->{
                players.put(uuid.toString(), updatedGameProfile);
            });
            return profile;
        }
    }

    public static ResourceLocation getSkin(GameProfile gameProfile) {
        Minecraft minecraft = Minecraft.getInstance();
        Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().getInsecureSkinInformation(gameProfile);

        if (map.containsKey(Type.SKIN)) {
            return minecraft.getSkinManager().registerTexture(map.get(Type.SKIN), Type.SKIN);
        } else {
            return DefaultPlayerSkin.getDefaultSkin(gameProfile.getId());
        }
    }

    public static ResourceLocation getSkin(UUID uuid, String name) { return getSkin(getGameProfile(uuid, name)); }
}
