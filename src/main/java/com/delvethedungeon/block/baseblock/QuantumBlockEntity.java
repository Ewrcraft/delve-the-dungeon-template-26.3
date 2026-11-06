package com.delvethedungeon.block.baseblock;
import com.delvethedungeon.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class QuantumBlockEntity extends BlockEntity {
    public int ticksSinceLast = 0;

    public QuantumBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.UNCERTAIN_BLOCK_ENTITY, pos, state);
    }

}
