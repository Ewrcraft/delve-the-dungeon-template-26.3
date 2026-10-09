package com.delvethedungeon;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PlushieBlockEntity extends BlockEntity {

    public PlushieBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CERTAIN_SOMEONE_PART2_BLOCK_ENTITY, pos, state);
    }

}
