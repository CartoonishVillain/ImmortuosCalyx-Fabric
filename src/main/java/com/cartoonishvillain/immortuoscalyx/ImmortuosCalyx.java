package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.config.ImmortuosConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ImmortuosCalyx implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "immortuoscalyx";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	public static ArrayList<ResourceLocation> DimensionExclusion;
//	public static ServerConfig config;

	public static ImmortuosConfig config;
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		AutoConfig.register(ImmortuosConfig.class, JanksonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(ImmortuosConfig.class).getConfig();

		Register.init();

		DimensionExclusion = getDimensions();

	}

	public static ArrayList<ResourceLocation> getDimensions() {
		final String DimensionList = ImmortuosCalyx.config.dimensionsAndSpawnDetails.DIMENSIONALCLEANSE;
		String[] DimensionExclusion = DimensionList.split(",");
		int exclusionLength = DimensionExclusion.length;
		ArrayList<ResourceLocation> finalDimensionExclusion = new ArrayList<>();
		int counter = 0;
		for (String i : DimensionExclusion) {
			ResourceLocation newResource = new ResourceLocation(i);
			finalDimensionExclusion.add(newResource);
			counter++;
		}
		return finalDimensionExclusion;
	}

}
