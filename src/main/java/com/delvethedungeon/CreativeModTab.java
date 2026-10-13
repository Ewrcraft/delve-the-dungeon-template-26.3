package com.delvethedungeon;


import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;


public class CreativeModTab {
    public static final ResourceKey<CreativeModeTab> DELVE_INTO_CREATIVE_KEY = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(), DelveTheDungeon.id("creative_tab")
    );

    public static final CreativeModeTab DELVE_INTO_CREATIVE = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.SAC_DAGGER))
            .title(Component.translatable("delveIntoCreative.delve_into_creative"))
            .displayItems((params, output) -> {
                output.accept(ModItems.SAC_DAGGER);
                output.accept(ModItems.ARROW_GUN);
                output.accept(ModBlocks.CHEESE);
                output.accept(ModBlocks.CERTAIN_SOMEONE);
                output.accept(ModBlocks.UNCERTAIN_BLOCK);
                output.accept(ModBlocks.CERTAIN_SOMEONE_PART2);
                output.accept(ModBlocks.DEFINITIVELY_SOMEONE);
                output.accept(ModBlocks.UNCERTAIN_SOMEONE);
            }).build();
    public static void initialize() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, DELVE_INTO_CREATIVE_KEY, DELVE_INTO_CREATIVE);
    }
}
