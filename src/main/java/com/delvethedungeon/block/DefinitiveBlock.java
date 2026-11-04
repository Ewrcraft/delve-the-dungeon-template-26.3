package com.delvethedungeon.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class DefinitiveBlock extends BaseEntityBlock {

    public DefinitiveBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DefinitiveBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!(world.getBlockEntity(pos) instanceof DefinitiveBlockEntity definitiveBlockEntity)) {
            return InteractionResult.PASS;
        }

        if (!player.getItemInHand(hand).isEmpty() && definitiveBlockEntity.isEmpty()) {
            definitiveBlockEntity.setItem(0, player.getItemInHand(hand).copy());
            player.getItemInHand(hand).setCount(0);
        }
        else {
            ItemStack nothing = player.getItemInHand(hand);
            player.setItemInHand(hand, definitiveBlockEntity.getItem(0));
            definitiveBlockEntity.setItem(0, nothing);
        }

        return InteractionResult.SUCCESS;
    }

}
