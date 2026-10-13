package com.delvethedungeon;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipBlockStateContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModDissapearingBlock extends BaseEntityBlock {
    public static final BooleanProperty EXISTENCE = BooleanProperty.create("existence");
    public static final EnumProperty<Direction> FACING;
    private static final VoxelShape FALLING_COLLISION_SHAPE = Shapes.box((double)0.0F, (double)0.0F, (double)0.0F, (double)1.0F, (double)0.9F, (double)1.0F);


    public ModDissapearingBlock(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH).setValue(EXISTENCE, true));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ModDissapearingBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{EXISTENCE, FACING});
    }

    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    protected VoxelShape getCollisionShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        if (!context.isPlacement() && context instanceof EntityCollisionContext entityCollisionContext) {
            Entity entity = entityCollisionContext.getEntity();
            if (entity != null) {
                boolean exists = state.getValue(EXISTENCE);
                if (exists) {
                    return super.getCollisionShape(state, level, pos, context);
                }
            }
        }
        return Shapes.empty();
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
    }


    public static boolean IsBlock(BlockState blockState) {
        return blockState.getBlock() == ModBlocks.UNCERTAIN_BLOCK;
    }

    public static boolean hasLineOfSightOfBlock(final Entity target, final ClipContext.Block blockCollidingContext, final ClipContext.Fluid fluidCollidingContext, BlockPos pos) {
        if (target.level() != target.level()) {
            return false;
        } else {
            Vec3 from = new Vec3(target.getX(), target.getEyeY(), target.getZ());
            Vec3 to = new Vec3(pos.getX(), pos.getY(), pos.getZ());
            Vec3 directionToBlock = genVecEntityToBlock(target, pos);
            Vec3 currentCameraDirection = target.getHeadLookAngle();
            if (directionToBlock.distanceTo(currentCameraDirection) < 1.5) {
                    return true;
            } else {
                return false;
            }
        }
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.UNCERTAIN_BLOCK_ENTITY, ModDissapearingBlock::tick);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, ModDissapearingBlockEntity entity) {
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

    public static boolean hasLineOfSightOfBlock(Level level, BlockPos pos) {
        List<? extends Player> players = level.players();
        // pos.getX(), pos.getY(), pos.getZ()
        for (Player target : players) {
            double dX = Math.abs(target.getX() - pos.getX());
            double dY = Math.abs(target.getY() - pos.getY());
            double dZ = Math.abs(target.getZ() - pos.getZ());
            double dTotal = dX + dY + dZ;
            if (dTotal < 15)
            if (target != null) {
                return hasLineOfSightOfBlock(target, net.minecraft.world.level.ClipContext.Block.COLLIDER, net.minecraft.world.level.ClipContext.Fluid.NONE, pos);
            } else {
                return false;
            }
        }
        return false;
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
