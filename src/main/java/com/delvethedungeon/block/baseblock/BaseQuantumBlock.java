package com.delvethedungeon.block.baseblock;

import com.delvethedungeon.block.ModBlockEntities;
import com.delvethedungeon.block.ModDissapearingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseQuantumBlock extends BaseEntityBlock {
    public static final BooleanProperty EXISTENCE = BooleanProperty.create("existence");

    public BaseQuantumBlock(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(EXISTENCE, true));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new QuantumBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{EXISTENCE});
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.UNCERTAIN_BLOCK_ENTITY, ModDissapearingBlock::tick);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, QuantumBlockEntity entity) {
        entity.ticksSinceLast += 1;
        if (entity.ticksSinceLast < 160) {
            boolean playerSees = hasLineOfSightOfBlock(level, blockPos);
            if (playerSees) {
                level.setBlockAndUpdate(blockPos, blockState.setValue(EXISTENCE, true));
            } else {
                level.setBlockAndUpdate(blockPos, blockState.setValue(EXISTENCE, false));
            }
            hasLineOfSightOfBlock(level, blockPos);
            entity.ticksSinceLast = 0;
        }
    }

    protected VoxelShape getCollision(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {return super.getCollisionShape(state, level, pos, context);}

    protected VoxelShape getCollisionShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        if (!context.isPlacement() && context instanceof EntityCollisionContext entityCollisionContext) {
            Entity entity = entityCollisionContext.getEntity();
            if (entity != null && state.getValue(EXISTENCE)) {
                return getCollision(state, level, pos, context);
            }
        }
        return Shapes.empty();
    }

    public static boolean hasLineOfSightOfBlock(final Entity target, final ClipContext.Block blockCollidingContext, final ClipContext.Fluid fluidCollidingContext, BlockPos pos) {
        if (target.level() != target.level()) {
            return false;
        } else {
            Vec3 directionToBlock = genVecEntityToBlock(target, pos);
            Vec3 currentCameraDirection = target.getHeadLookAngle();
            if (directionToBlock.distanceTo(currentCameraDirection) < 1.5) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean hasLineOfSightOfBlock(Level level, BlockPos pos) {
        List<? extends Player> players = level.players();
        boolean isSeen = false;
        for (Player target : players) {
            double dX = Math.abs(target.getX() - pos.getX());
            double dY = Math.abs(target.getY() - pos.getY());
            double dZ = Math.abs(target.getZ() - pos.getZ());
            double dTotal = dX + dY + dZ;
            if (dTotal < 15)
                if (target != null && !target.isSpectator()) {
                    isSeen = hasLineOfSightOfBlock(target, net.minecraft.world.level.ClipContext.Block.COLLIDER, net.minecraft.world.level.ClipContext.Fluid.NONE, pos);
                    if (isSeen) {
                        break;
                    }
                }
        }
        return isSeen;
    }

    public static Vec3 genVecEntityToBlock(Entity a, BlockPos b) {
        double dX = a.getX() - b.getX()-0.5;
        double dY = a.getY() - b.getY()+1;
        double dZ = a.getZ() - b.getZ()-0.5;
        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.sin(pitch) * Math.sin(yaw);
        double z = Math.cos(pitch);

        Vec3 vector = new Vec3(x, z, y);
        vector = vector.normalize();
        return vector;
    }

}
