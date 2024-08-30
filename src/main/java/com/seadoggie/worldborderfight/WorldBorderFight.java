package com.seadoggie.worldborderfight;

import com.seadoggie.worldborderfight.event.ServerPlayerDeathCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import static net.minecraft.server.command.CommandManager.*;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class WorldBorderFight implements ModInitializer {
	public static final String MOD_ID = "worldborder-fight";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Loaded");

		// Hopefully registering /foo as a useless command (on the server)
		CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> dispatcher.register(literal("foo")
				.requires(source -> source.hasPermissionLevel(2)) // set permission level
				.executes(context -> {
					context.getSource().sendFeedback(() -> Text.literal("You're an operator, foo!"), false);
					return 1;
				}))));

		ServerPlayerDeathCallback.EVENT.register((player, damageSource) -> {
			player.sendMessage(Text.literal("[WorldBorder Fight] Player died: " + player.getName().getString()));
            LOGGER.info("[WorldBorder Fight] Player died: {}", player.getName());

			CommandManager manager = Objects.requireNonNull(player.getServer()).getCommandManager();
			ServerCommandSource source = player.getServer().getCommandSource();
			manager.executeWithPrefix(source, "/worldborder set 20");
		});


	}
}