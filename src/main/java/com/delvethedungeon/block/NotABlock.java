package com.delvethedungeon.block;

import com.delvethedungeon.DelveTheDungeon;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.TntBlock.prime;

public class NotABlock extends Block {

    public static final IntegerProperty INTEGRITY;

    public NotABlock(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(this.INTEGRITY, 2));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{INTEGRITY});
    }

    public BlockState playerWillDestroy(final Level level, final BlockPos pos, final BlockState state, final Player player) {

        DelveTheDungeon.LOGGER.info("Value on breaking: " + state.getValue(INTEGRITY));

        if (state.getValue(INTEGRITY) == 0) {
            return super.playerWillDestroy(level, pos, state, player);
        }

        //level.setBlock(pos, ModBlocks.NOT_A_BLOCK.defaultBlockState().setValue(INTEGRITY, state.getValue(INTEGRITY)-1), 1);

        return state;
    }

    static {
        INTEGRITY = IntegerProperty.create("integrity", 0, 2);
    }

}
