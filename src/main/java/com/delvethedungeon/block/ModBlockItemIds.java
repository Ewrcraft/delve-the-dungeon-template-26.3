package com.delvethedungeon.block;

import com.delvethedungeon.DelveTheDungeon;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;

public class ModBlockItemIds {

    public static final BlockItemId CERTAIN_SOMEONE = create("certain_someone");
    public static final BlockItemId CERTAIN_SOMEONE_PART2 = create("certain_someone_part2");
    public static final BlockItemId UNCERTAIN_SOMEONE = create("uncertain_someone");
    public static final BlockItemId DEFINITIVELY_SOMEONE = create("definitively_someone");
    public static final BlockItemId CHEESE = create("cheese");
    public static final BlockItemId UNCERTAIN_BLOCK = create("uncertain_block");

    private static BlockItemId create(String name) {
        Identifier id = DelveTheDungeon.id(name);
        return BlockItemId.create(id, id);
    }
}