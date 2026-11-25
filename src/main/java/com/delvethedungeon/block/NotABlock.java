package com.delvethedungeon.block;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.ArrayList;
import java.util.List;

public class NotABlock extends Block {

    public static final Block[] BLOCKS = createArrayList(ModBlocks.CERTAIN_SOMEONE, ModBlocks.CHEESE);
    public static final int MAX_CYCLE = BLOCKS.length-1;
    public static final IntegerProperty CYCLE = IntegerProperty.create("cycle", 0, MAX_CYCLE);

    private static Block[] createArrayList(Block... blocks) {
        return blocks;
    }

    public NotABlock(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(CYCLE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{CYCLE});
    }

    public void destroy(final LevelAccessor level, final BlockPos pos, final BlockState state) {
        if (state.getValue(CYCLE) == 0) {

        }
    }

    public void wasExploded(final ServerLevel level, final BlockPos pos, final Explosion explosion) {
        this.destroy(level, pos, level.getBlockState(pos));
    }
}
