package com.delvethedungeon;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

public class ModItems {

    public static final TagKey<Item> REPAIRS_GUIDITE_ARMOR = TagKey.create(BuiltInRegistries.ITEM.key(), DelveTheDungeon.id("repairs_guidite_armor"));

    public static final TagKey<Block> INCORRECT_FOR_GUIDITE_TOOL = TagKey.create(Registries.BLOCK,
            DelveTheDungeon.id("incorrect_for_guidite_tool"));

    public static final ToolMaterial GUIDITE_TOOL_MATERIAL = new ToolMaterial(
            INCORRECT_FOR_GUIDITE_TOOL, // incorrect blocks for drops
            455, // durability
            5.0F, // speed
            1.5F, // attack damage bonus
            22, // enchantment value
            REPAIRS_GUIDITE_ARMOR // repair items
    );

    public static final Item SAC_DAGGER = register(ModItemIds.SAC_DAGGER, Item::new, new Item.Properties().sword(GUIDITE_TOOL_MATERIAL, 1f, 1f));

    public static Item register(ResourceKey<Item> itemKey, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
        // Create the item instance.
        Item item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static void initialize() {
    }

}