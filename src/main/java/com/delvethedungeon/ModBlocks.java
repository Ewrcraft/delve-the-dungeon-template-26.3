package com.delvethedungeon;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class ModBlocks {

    public static final Block CERTAIN_SOMEONE = register(
            ModBlockItemIds.CERTAIN_SOMEONE,
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.AMETHYST)
    );
    public static final Block CHEESE = register(
            ModBlockItemIds.CHEESE,
            Block::new,
            BlockBehaviour.Properties.of().sound(SoundType.AMETHYST)
    );
    public static final Block CERTAIN_SOMEONE_PART2 = register(
            ModBlockItemIds.CERTAIN_SOMEONE_PART2,
            PlushieBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.CINNABAR)
    );
    public static final Block UNCERTAIN_SOMEONE = registerWithTooltip(
            ModBlockItemIds.UNCERTAIN_SOMEONE,
            PlushieBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.CINNABAR),
            ModComponents.COMPONENT_WITH_TOOLTIP
    );
    public static final Block DEFINITIVELY_SOMEONE = register(
            ModBlockItemIds.DEFINITIVELY_SOMEONE,
            PlushieBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.CINNABAR)
    );

    private static Block register(ResourceKey<Block> id, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties properties) {
        // Create the block instance
        Block block = blockFactory.apply(properties.setId(id));

        return Registry.register(BuiltInRegistries.BLOCK, id, block);
    }

    private static Block register(BlockItemId id, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties properties) {
        // Create the block instance
        Block block = register(id.block(), blockFactory, properties);

        // Create the block item instance
        BlockItem blockItem = new BlockItem(block, new Item.Properties().useBlockDescriptionPrefix().setId(id.item()));
        Registry.register(BuiltInRegistries.ITEM, id.item(), blockItem);

        return block;
    }

    private static Block registerWithTooltip(BlockItemId id, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties properties, DataComponentType component) {
        // Create the block instance
        Block block = register(id.block(), blockFactory, properties);

        // Create the block item instance
        BlockItem blockItem = new BlockItem(block, new Item.Properties().useBlockDescriptionPrefix().setId(id.item()).component(component, 0));
        Registry.register(BuiltInRegistries.ITEM, id.item(), blockItem);

        return block;
    }

    public static void initialize() {
    }
}
