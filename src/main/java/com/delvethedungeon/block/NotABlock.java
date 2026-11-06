package com.delvethedungeon.block;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class NotABlock extends Block {

    public NotABlock(Properties properties) {
        super(properties);
    }

    public void destroy(final LevelAccessor level, final BlockPos pos, final BlockState state) {
        level.setBlock(pos, Blocks.DIAMOND_BLOCK.defaultBlockState(), 1);
    }

    public void wasExploded(final ServerLevel level, final BlockPos pos, final Explosion explosion) {
        level.setBlock(pos, Blocks.DIAMOND_BLOCK.defaultBlockState(), 1);
    }
}
