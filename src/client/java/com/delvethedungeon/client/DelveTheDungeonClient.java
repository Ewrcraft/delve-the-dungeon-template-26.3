package com.delvethedungeon.client;

import com.delvethedungeon.DelveTheDungeon;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.Style;
import net.minecraft.util.ARGB;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;

import java.util.List;

public class DelveTheDungeonClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// Attach our rendering code to before the chat hud layer. Our layer will render right before the chat. The API will take care of z spacing.
		HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, DelveTheDungeon.id("before_chat"), DelveTheDungeonClient::extract);
	}

	private static void extract(GuiGraphicsExtractor graphics, DeltaTracker tickCounter) {
		int color = 0xFFFF0000; // Red
		int targetColor = 0xFF00FF00; // Green

		// You can use the Util.getMillis() function to get the current time in milliseconds.
		// Divide by 1000 to get seconds.
		double currentTime = Util.getMillis() / 1000.0;

		// "lerp" simply means "linear interpolation", which is a fancy way of saying "blend".
		float lerpedAmount = Mth.abs(Mth.sin((float) currentTime));
		int lerpedColor = ARGB.linearLerp(lerpedAmount, color, targetColor);

		// Draw a square with the lerped color.
		// x1, x2, y1, y2, color

		Screen screen = new Screen(Component.literal("Complimented a certain someone %s time(-s)".formatted(DelveTheDungeon.compliments))) {
			@Override
			protected void init() {
				super.init();
			}
		};

		if (!DelveTheDungeon.isAwesomeMode) {
			graphics.text(screen.getFont(), Component.literal("Said something into the void %s time(-s)".formatted(DelveTheDungeon.stuff_said)), 10, 200, 0xFFFFFFFF, false);
		}
		else {
			graphics.text(screen.getFont(), Component.literal("Complimented a certain someone %s time(-s)".formatted(DelveTheDungeon.compliments)), 10, 200, ARGB.opaque(0x58009c), false);
		}

		//graphics.fill(0, 0, 10, 10, lerpedColor);
	}
}