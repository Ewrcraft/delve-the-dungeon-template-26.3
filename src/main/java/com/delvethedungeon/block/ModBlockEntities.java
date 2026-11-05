package com.delvethedungeon.block;

import com.delvethedungeon.DelveTheDungeon;
import com.delvethedungeon.block.baseblock.QuantumBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {

    public static final BlockEntityType<PlushieBlockEntity> CERTAIN_SOMEONE_PART2_BLOCK_ENTITY =
            register("counter", PlushieBlockEntity::new, ModBlocks.CERTAIN_SOMEONE_PART2, ModBlocks.UNCERTAIN_SOMEONE, ModBlocks.DEFINITIVELY_SOMEONE);

    public static final BlockEntityType<QuantumBlockEntity> UNCERTAIN_BLOCK_ENTITY =
            register("countercool", QuantumBlockEntity::new, ModBlocks.UNCERTAIN_BLOCK);

    public static final BlockEntityType<DefinitiveBlockEntity> DEFINITIVE_BLOCK_ENTITY =
            register("definitive_container_block_entity", DefinitiveBlockEntity::new, ModBlocks.DEFINITIVE_BLOCK);

    public static final BlockEntityType<DefinitiveBlockEntity> CHARISMATIC_BLOCK_ENTITY =
            register("charismatic_container_block_entity", DefinitiveBlockEntity::new, ModBlocks.CHARISMATIC_BLOCK);

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        Identifier id = DelveTheDungeon.id(name);

        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static void initialize() {
    }

}
