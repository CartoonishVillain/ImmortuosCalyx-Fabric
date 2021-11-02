package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.Items.BaseItems;
import com.cartoonishvillain.immortuoscalyx.Items.ItemFunctionality;
import com.cartoonishvillain.immortuoscalyx.entities.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import static com.cartoonishvillain.immortuoscalyx.ImmortuosCalyx.MOD_ID;

public class Register {

    public static final Item SYRINGE = new BaseItems(new Item.Properties().tab(CreativeModeTab.TAB_BREWING), ChatFormatting.GRAY + "Allows you to harvest biomaterials necessary to make medicines", "", "", "", ItemFunctionality.NONE);
    public static final Item GENERALANTIPARASITIC = new BaseItems(new Item.Properties().tab(CreativeModeTab.TAB_BREWING), ChatFormatting.BLUE + "Strengthens Immune System to the Immortuos Calyx Parasite", ChatFormatting.BLUE + "Does not make you immune. May also kill early forms of infection", ChatFormatting.RED + "Will cause light organ damage", ChatFormatting.GRAY + "Obtained through syringe extraction from a slime", ItemFunctionality.ANTIBIOTIC);
    public static final Item IMMORTUOSCALYXEGGS = new BaseItems(new Item.Properties().tab(CreativeModeTab.TAB_BREWING), ChatFormatting.RED + "Infects humans with the Immortuos Calyx Parasite,", ChatFormatting.GRAY + "Obtained through syringe extraction from fully converted entities", "", "", ItemFunctionality.EGGS);
    public static final Item CALYXANIDE = new BaseItems(new Item.Properties().tab(CreativeModeTab.TAB_BREWING),ChatFormatting.BLUE + "Kills the Immortuos Calyx Parasite", ChatFormatting.BLUE + "May need multiple doses for later stage infections", ChatFormatting.RED + "May be lethal if the parasite is ingrained too heavily", "", ItemFunctionality.CALYXIDE);
    public static final Item SCANNER = new BaseItems(new Item.Properties().tab(CreativeModeTab.TAB_BREWING).stacksTo(1), ChatFormatting.BLUE + "Gives you information about infection", ChatFormatting.BLUE + "levels in players, and yourself.",ChatFormatting.GRAY + "Shift right click to view your stats,", ChatFormatting.GRAY + "left click entities to view theirs.", ItemFunctionality.SCANNER);


    public static final EntityType<InfectedPlayerEntity>  INFECTEDPLAYER = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(MOD_ID, "infectedplayer"), FabricEntityTypeBuilder.create(MobCategory.MONSTER, InfectedPlayerEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).build());
    public static final EntityType<InfectedHumanEntity>  INFECTEDHUMAN = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(MOD_ID, "infectedhuman"), FabricEntityTypeBuilder.create(MobCategory.MONSTER, InfectedHumanEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).build());
    public static final EntityType<InfectedDiverEntity>  INFECTEDDIVER = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(MOD_ID, "infecteddiver"), FabricEntityTypeBuilder.create(MobCategory.MONSTER, InfectedDiverEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).build());
    public static final EntityType<InfectedVillagerEntity>  INFECTEDVILLAGER = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(MOD_ID, "infectedvillager"), FabricEntityTypeBuilder.create(MobCategory.MONSTER, InfectedVillagerEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).build());
    public static final EntityType<InfectedIGEntity>  INFECTEDIG = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(MOD_ID, "infectedig"), FabricEntityTypeBuilder.create(MobCategory.MONSTER, InfectedIGEntity::new).dimensions(EntityDimensions.fixed(1.6f, 2.6f)).build());


    public static final ResourceLocation human_ambient_id = new ResourceLocation(MOD_ID, "infected_idle");
    public static final ResourceLocation human_hurt_id = new ResourceLocation(MOD_ID, "infected_hurt");
    public static final ResourceLocation human_death_id = new ResourceLocation(MOD_ID, "infected_death");
    public static final ResourceLocation inject_id = new ResourceLocation(MOD_ID, "inject");
    public static final ResourceLocation extract_id = new ResourceLocation(MOD_ID, "extract");
    public static final ResourceLocation ig_hurt_id = new ResourceLocation(MOD_ID, "iginfected_hurt");
    public static final ResourceLocation ig_death_id = new ResourceLocation(MOD_ID, "iginfected_death");
    public static final ResourceLocation vil_idle_id = new ResourceLocation(MOD_ID, "villagerinfected_idle");
    public static final ResourceLocation vil_hurt_id = new ResourceLocation(MOD_ID, "villagerinfected_hurt");
    public static final ResourceLocation vil_death_id = new ResourceLocation(MOD_ID, "villagerinfected_death");

    public static SoundEvent HUMANAMBIENT = new SoundEvent(human_ambient_id);
    public static SoundEvent HUMANHURT = new SoundEvent(human_hurt_id);
    public static SoundEvent HUMANDEATH = new SoundEvent(human_death_id);
    public static SoundEvent INJECT = new SoundEvent(inject_id);
    public static SoundEvent EXTRACT = new SoundEvent(extract_id);
    public static SoundEvent IGHURT = new SoundEvent(ig_hurt_id);
    public static SoundEvent IGDEATH = new SoundEvent(ig_death_id);
    public static SoundEvent VILIDLE = new SoundEvent(vil_idle_id);
    public static SoundEvent VILHURT = new SoundEvent(vil_hurt_id);
    public static SoundEvent VILDEATH = new SoundEvent(vil_death_id);

    public static void init(){
        Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "syringe"), SYRINGE);
        Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "antiparasitic"), GENERALANTIPARASITIC);
        Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "immortuoseggs"), IMMORTUOSCALYXEGGS);
        Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "calyxanide"), CALYXANIDE);
        Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "healthscanner"), SCANNER);

        Registry.register(Registry.SOUND_EVENT, human_ambient_id, HUMANAMBIENT);
        Registry.register(Registry.SOUND_EVENT, human_hurt_id, HUMANHURT);
        Registry.register(Registry.SOUND_EVENT, human_death_id, HUMANDEATH);
        Registry.register(Registry.SOUND_EVENT, inject_id, INJECT);
        Registry.register(Registry.SOUND_EVENT, extract_id, EXTRACT);
        Registry.register(Registry.SOUND_EVENT, ig_hurt_id, IGHURT);
        Registry.register(Registry.SOUND_EVENT, ig_death_id, IGDEATH);
        Registry.register(Registry.SOUND_EVENT, vil_idle_id, VILIDLE);
        Registry.register(Registry.SOUND_EVENT, vil_hurt_id, VILHURT);
        Registry.register(Registry.SOUND_EVENT, vil_death_id, VILDEATH);

        FabricDefaultAttributeRegistry.register(INFECTEDPLAYER, InfectedPlayerEntity.customAttributes());
        FabricDefaultAttributeRegistry.register(INFECTEDHUMAN, InfectedHumanEntity.customAttributes());
        FabricDefaultAttributeRegistry.register(INFECTEDDIVER, InfectedDiverEntity.customAttributes());
        FabricDefaultAttributeRegistry.register(INFECTEDVILLAGER, InfectedVillagerEntity.customAttributes());
        FabricDefaultAttributeRegistry.register(INFECTEDIG, InfectedIGEntity.customAttributes());
    }
}