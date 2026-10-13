package com.delvethedungeon;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModDissapearingBlockEntity extends BlockEntity {
    public int ticksSinceLast = 0;
    public ModDissapearingBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.UNCERTAIN_BLOCK_ENTITY, pos, state);
    }


}
