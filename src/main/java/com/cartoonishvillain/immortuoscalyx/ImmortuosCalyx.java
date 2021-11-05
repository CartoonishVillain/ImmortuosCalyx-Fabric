package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.commands.SetInfectionRateCommand;
import com.cartoonishvillain.immortuoscalyx.config.ImmortuosConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

import static com.cartoonishvillain.immortuoscalyx.entities.Spawns.initSpawns;

public class ImmortuosCalyx implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "immortuoscalyx";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	public static ArrayList<ResourceLocation> DimensionExclusion;
	public static final ArrayList<Item> rawItem = new ArrayList<>(Arrays.asList(Items.BEEF, Items.RABBIT, Items.CHICKEN, Items.PORKCHOP, Items.MUTTON, Items.COD, Items.SALMON, Items.ROTTEN_FLESH));
	public static final CreativeModeTab TAB = FabricItemGroupBuilder.build(new ResourceLocation(ImmortuosCalyx.MOD_ID, "immortuostab"), () -> new ItemStack(Register.IMMORTUOSCALYXEGGS));
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

		CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> {
			SetInfectionRateCommand.register(dispatcher);
		}));

		registerPackets();

		initSpawns();

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


	public static void registerPackets(){
		ServerPlayNetworking.registerGlobalReceiver(new ResourceLocation(ImmortuosCalyx.MOD_ID, "soundpacket"),((server, player, handler, buf, responseSender) -> {
			player.level.playSound(null, player.blockPosition(), Register.HUMANAMBIENT, SoundSource.PLAYERS, 0.5f, 2f);
		}));

		ServerPlayNetworking.registerGlobalReceiver(new ResourceLocation(ImmortuosCalyx.MOD_ID, "chatpacket"), (((server, player, handler, buffer, responseSender) -> {
			int length = buffer.readInt();
			String name = (String) buffer.readCharSequence(length, Charset.defaultCharset());
			length = buffer.readInt();
			String message = (String) buffer.readCharSequence(length, Charset.defaultCharset());
			server.getPlayerList().broadcastMessage(new TextComponent(name + ChatFormatting.OBFUSCATED + message), ChatType.CHAT, player.getUUID());
		})));
	}

}
