package com.delvethedungeon.client;

import com.delvethedungeon.userinterface.CharismaticBlockMenu;
import com.delvethedungeon.userinterface.ModMenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class DelveTheDungeonScreens implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModMenuTypes.CHARISMATIC_BLOCK_MENU, CharismaticBlockScreen::new);
    }
}