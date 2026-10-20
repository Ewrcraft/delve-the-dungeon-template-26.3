package com.delvethedungeon;

import com.delvethedungeon.block.ModBlockEntities;
import com.delvethedungeon.block.ModBlocks;
import com.delvethedungeon.item.ArrowGun;
import com.delvethedungeon.item.ModItems;
import com.delvethedungeon.userinterface.CreativeModTab;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import net.minecraft.world.InteractionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelveTheDungeon implements ModInitializer {
	public static final String MOD_ID = "delve-the-dungeon";

	public static boolean isAwesomeMode = false;
	public static int compliments = 0;
	public static int stuff_said = 0;


	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		CreativeModTab.initialize();
		ModItems.initialize();
		ModBlocks.initialize();
		ModBlockEntities.initialize();
		ModComponents.initialize();

		LOGGER.info("Hello Fabric world!");

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("awesomegirl").executes(context -> {
				isAwesomeMode = !isAwesomeMode;
				if (isAwesomeMode) {
					context.getSource().sendSuccess(() -> Component.literal("Made everything better"), false);
				}
				return 1;
			}));
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("saystuff")
					.then(Commands.argument("value", StringArgumentType.string())
							.executes(DelveTheDungeon::executeCommandWithArg)));
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("compliment")
					.then(Commands.argument("value", StringArgumentType.string())
							.executes(DelveTheDungeon::executeCommandWithArg2)));
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("testcommand").executes(context -> {
				context.getSource().sendSuccess(() -> Component.literal("Called test command"), false);
				return 1;
			}));
		});

		UseItemCallback.EVENT.register((player, world, hand) -> {
			System.out.println("Right clicked");
			System.out.println(player.getInventory().getItem(hand.asEquipmentSlot().getId()).getItem());
			if (player.getItemInHand(hand).getItem().equals(ModItems.ARROW_GUN)) {
				System.out.println("Ran SUCCESS branch");
				player.getItemInHand(hand).getItem().use(world, player, hand);
				return InteractionResult.SUCCESS;
			} else {
				System.out.println("Ran PASS branch");
				return InteractionResult.PASS;
			}});
	}

	private static int executeCommandWithArg(CommandContext<CommandSourceStack> context) {
		String value = StringArgumentType.getString(context, "value");
		DelveTheDungeon.stuff_said++;
		context.getSource().sendSuccess(() -> Component.literal("Said %s".formatted(value)), false);
		return 1;
	}

	private static int executeCommandWithArg2(CommandContext<CommandSourceStack> context) {
		String value = StringArgumentType.getString(context, "value");
		DelveTheDungeon.compliments++;
		context.getSource().sendSuccess(() -> Component.literal("Said to Certain someone %s".formatted(value)), false);
		return 1;
	}


	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
