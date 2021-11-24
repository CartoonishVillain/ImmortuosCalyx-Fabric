package com.cartoonishvillain.immortuoscalyx.entities;

import com.cartoonishvillain.immortuoscalyx.ImmortuosCalyx;
import com.cartoonishvillain.immortuoscalyx.Register;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Spawns {
    public static void initSpawns(){
        //Loop through every biome
        for(Biome biome : BuiltinRegistries.BIOME){
            // Make the new spawner entry like I do in forge
            MobSpawnSettings.SpawnerData vilspawner = new MobSpawnSettings.SpawnerData(Register.INFECTEDVILLAGER, ImmortuosCalyx.config.dimensionsAndSpawnDetails.VILLAGER, 1, 1);
            MobSpawnSettings.SpawnerData humanspawner = new MobSpawnSettings.SpawnerData(Register.INFECTEDHUMAN, ImmortuosCalyx.config.dimensionsAndSpawnDetails.HUMAN, 1, 1);
            MobSpawnSettings.SpawnerData diverspawner = new MobSpawnSettings.SpawnerData(Register.INFECTEDDIVER, ImmortuosCalyx.config.dimensionsAndSpawnDetails.DIVER, 1, 1);
            //Select biome types.
            if(biome.getBiomeCategory() != Biome.BiomeCategory.NETHER && biome.getBiomeCategory() != Biome.BiomeCategory.THEEND && biome.getBiomeCategory() != Biome.BiomeCategory.OCEAN && biome.getBiomeCategory() != Biome.BiomeCategory.MUSHROOM){
                //grab the list of spawn entries.
                List<MobSpawnSettings.SpawnerData> spawnersList = biome.getMobSettings().spawners.get(MobCategory.MONSTER).unwrap();
                //move it to a more mutable list
                ArrayList<MobSpawnSettings.SpawnerData> newSpawnerList = new ArrayList<>(spawnersList);
                //add my spawner to the mutable list
                newSpawnerList.add(vilspawner);
                newSpawnerList.add(humanspawner);
                //make a new mutable map
                HashMap<MobCategory, WeightedRandomList<MobSpawnSettings.SpawnerData>> newSpawnerMap = new HashMap<>(biome.getMobSettings().spawners);
                //add the new WeightedRandom list to the new mutable map;
                newSpawnerMap.put(MobCategory.MONSTER, WeightedRandomList.create(newSpawnerList));
                //Replace the old scary nonmutable map with the new map
                biome.getMobSettings().spawners = newSpawnerMap;
            } else if(biome.getBiomeCategory() == Biome.BiomeCategory.OCEAN){
                //grab the list of spawn entries.
                List<MobSpawnSettings.SpawnerData> spawnersList = biome.getMobSettings().spawners.get(MobCategory.MONSTER).unwrap();
                //move it to a more mutable list
                ArrayList<MobSpawnSettings.SpawnerData> newSpawnerList = new ArrayList<>(spawnersList);
                //add my spawner to the mutable list
                newSpawnerList.add(diverspawner);
                //make a new mutable map
                HashMap<MobCategory, WeightedRandomList<MobSpawnSettings.SpawnerData>> newSpawnerMap = new HashMap<>(biome.getMobSettings().spawners);
                //add the new WeightedRandom list to the new mutable map;
                newSpawnerMap.put(MobCategory.MONSTER, WeightedRandomList.create(newSpawnerList));
                //Replace the old scary nonmutable map with the new map
                biome.getMobSettings().spawners = newSpawnerMap;
            }
        }
    }
}
