package com.delvethedungeon.userinterface;

import com.delvethedungeon.DelveTheDungeon;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {
    public static final MenuType<CharismaticBlockMenu> CHARISMATIC_BLOCK_MENU = register("charismatic_block", CharismaticBlockMenu::new);

    public static <T extends AbstractContainerMenu> MenuType<T> register(
            String name,
            MenuType.MenuSupplier<T> constructor
    ) {
        return Registry.register(BuiltInRegistries.MENU, DelveTheDungeon.id(name), new MenuType<>(constructor, FeatureFlagSet.of()));
    }

    public static void initialize() {
    }
}