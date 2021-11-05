package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.Register;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SpawnPlacements.class)
public class SpawnPlacementMixin {
    @Shadow
    private static <T extends Mob> void register(EntityType<T> type, SpawnPlacements.Type spawnType, Heightmap.Types heightTypes, SpawnPlacements.SpawnPredicate<T> predicate){

    }

    static {
        register(Register.INFECTEDDIVER, SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, Mob::checkMobSpawnRules);
        register(Register.INFECTEDHUMAN, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        register(Register.INFECTEDVILLAGER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
    }
}
