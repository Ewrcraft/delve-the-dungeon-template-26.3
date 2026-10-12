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
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
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
                if (exists || entity instanceof Projectile) {
                    return super.getCollisionShape(state, level, pos, context);
                }
            }
        }
        return Shapes.empty();
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!player.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        } else {
            boolean exists = state.getValue(EXISTENCE);
            level.setBlockAndUpdate(pos, state.setValue(EXISTENCE, !exists));
            level.playSound(player, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 1.0F, 1.0F);

            return InteractionResult.SUCCESS;
        }
    }

    protected void onProjectileHit(final Level level, final BlockState state, final BlockHitResult blockHit, final Projectile projectile) {
        BlockPos pos = blockHit.getBlockPos();
        boolean playerSees = hasLineOfSightOfBlock(level, pos);
        if (playerSees) {
            boolean exists = state.getValue(EXISTENCE);
            level.setBlockAndUpdate(pos, state.setValue(EXISTENCE, !exists));
        }
    }



        public boolean hasLineOfSightOfBlock(final Entity target, final ClipContext.Block blockCollidingContext, final ClipContext.Fluid fluidCollidingContext, BlockPos pos) {
            if (target.level() != target.level()) {
                return false;
            } else {
                Vec3 from = new Vec3(target.getX(), target.getEyeY(), target.getZ());
                Vec3 to = new Vec3(pos.getX(), pos.getY(), pos.getZ());
                if (to.distanceTo(from) > (double)128.0F) {
                    return false;
                } else {
                    Vec3 directionToBlock = genVecEntityToBlock(target, pos);
                    Vec3 currentCameraDirection = target.getHeadLookAngle();
                    System.out.println(directionToBlock);
                    System.out.println(currentCameraDirection);
                    System.out.println(directionToBlock.distanceTo(currentCameraDirection));
                    if (directionToBlock.distanceTo(currentCameraDirection) < 0.3) {

                        return target.level().clip(new ClipContext(from, to, blockCollidingContext, fluidCollidingContext, target)).getType() == net.minecraft.world.phys.HitResult.Type.MISS;
                    }
                    return false;
                }
            }
        }

        public boolean hasLineOfSightOfBlock(Level level, BlockPos pos) {
            Player target = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 10.0, false);
            if (target != null) {
                return this.hasLineOfSightOfBlock(target, net.minecraft.world.level.ClipContext.Block.COLLIDER, net.minecraft.world.level.ClipContext.Fluid.NONE, pos);
            } else {
                return false;
            }

        }

        public static Vec3 genVecEntityToBlock(Entity a, BlockPos b) {
            double dX = a.getX() - b.getX()+0.5;
            double dY = a.getY() - b.getY()+0.5;
            double dZ = a.getZ() - b.getZ()+0.5;
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
