package com.delvethedungeon.block;

import com.delvethedungeon.block.helpers.ImplementedContainer;
import com.delvethedungeon.userinterface.CharismaticBlockMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class CharismaticBlockEntity extends BlockEntity implements ImplementedContainer, MenuProvider {

    public static final int CONTAINER_SIZE = 3 * 3;
    private final NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);


    public CharismaticBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHARISMATIC_BLOCK_ENTITY, pos, state);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, this.items);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        ContainerHelper.saveAllItems(output, this.items);
        super.saveAdditional(output);
    }

    @Override
    @NonNull
    public Component getDisplayName() {
        return Component.translatable("block.delve-the-dungeon.charismatic_block");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new CharismaticBlockMenu(containerId, inventory, this);
    }

}
