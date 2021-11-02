package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.Items.BaseItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import static com.cartoonishvillain.immortuoscalyx.ImmortuosCalyx.MOD_ID;

public class Register {

    public static final Item SYRINGE = new BaseItems(new Item.Properties().tab(CreativeModeTab.TAB_BREWING), ChatFormatting.GRAY + "Allows you to harvest biomaterials necessary to make medicines", "", "", "");

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
    }
}
