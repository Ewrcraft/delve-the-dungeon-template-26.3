package com.delvethedungeon.block;

import com.delvethedungeon.block.helpers.ImplementedContainer;
import com.delvethedungeon.userinterface.CharismaticBlockMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class CharismaticBlockEntity extends BlockEntity implements ImplementedContainer, MenuProvider {

    public static final int CONTAINER_SIZE = 3 * 3;
    private final NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    private final ContainerOpenersCounter openersCounter;


    public CharismaticBlockEntity(final BlockPos worldPosition, final BlockState blockState) {
        super(ModBlockEntities.CHARISMATIC_BLOCK_ENTITY, worldPosition, blockState);
        this.openersCounter = new ContainerOpenersCounter() {
            {
                Objects.requireNonNull(CharismaticBlockEntity.this);
            }

            protected void onOpen(final Level level, final BlockPos pos, final BlockState state) {
                //CharismaticBlockEntity.this.playSound(state, SoundEvents.BARREL_OPEN);
                CharismaticBlockEntity.this.updateBlockState(state, true);
            }

            protected void onClose(final Level level, final BlockPos pos, final BlockState state) {
                //CharismaticBlockEntity.this.playSound(state, SoundEvents.BARREL_CLOSE);
                CharismaticBlockEntity.this.updateBlockState(state, false);
            }

            protected void openerCountChanged(final Level level, final BlockPos pos, final BlockState blockState, final int previous, final int current) {
            }

            public boolean isOwnContainer(final Player player) {
                if (player.containerMenu instanceof CharismaticBlockMenu) {
                    Container container = ((CharismaticBlockMenu)player.containerMenu).getContainer();
                    return container == CharismaticBlockEntity.this;
                } else {
                    return false;
                }
            }
        };
    }

    private void updateBlockState(final BlockState state, final boolean isOpen) {
        this.level.setBlockAndUpdate(this.getBlockPos(), (BlockState)state.setValue(CharismaticBlock.OPEN, isOpen));
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

    public void startOpen(final ContainerUser containerUser) {
        if (!this.remove && !containerUser.getLivingEntity().isSpectator()) {
            this.openersCounter.incrementOpeners(containerUser.getLivingEntity(), this.getLevel(), this.getBlockPos(), this.getBlockState(), containerUser.getContainerInteractionRange());
        }

    }

    public void stopOpen(final ContainerUser containerUser) {
        if (!this.remove && !containerUser.getLivingEntity().isSpectator()) {
            this.openersCounter.decrementOpeners(containerUser.getLivingEntity(), this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
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

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }
}
