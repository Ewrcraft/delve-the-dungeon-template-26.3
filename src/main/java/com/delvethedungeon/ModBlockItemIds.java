package com.delvethedungeon;

import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModBlockItemIds {

    public static final BlockItemId CERTAIN_SOMEONE = create("certain_someone");
    public static final BlockItemId CERTAIN_SOMEONE_PART2 = create("certain_someone_part2");
    public static final BlockItemId UNCERTAIN_SOMEONE = create("uncertain_someone");
    public static final BlockItemId DEFINITIVELY_SOMEONE = create("definitively_someone");
    public static final BlockItemId CHEESE = create("cheese");

    private static BlockItemId create(String name) {
        Identifier id = DelveTheDungeon.id(name);
        return BlockItemId.create(id, id);
    }
}