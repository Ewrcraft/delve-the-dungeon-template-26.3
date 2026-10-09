package com.delvethedungeon;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ModItemIds {

    public static final ResourceKey<Item> SAC_DAGGER = create("sac_dagger");

    public static ResourceKey<Item> create(String name) {
        // Create the item key.
        return ResourceKey.create(Registries.ITEM, DelveTheDungeon.id(name));
    }
}
