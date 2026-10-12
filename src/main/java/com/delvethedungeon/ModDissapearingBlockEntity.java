package com.delvethedungeon;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import static com.delvethedungeon.ModDissapearingBlock.EXISTENCE;

public class ModDissapearingBlockEntity extends BlockEntity {
    public int ticksSinceLast = 0;
    public ModDissapearingBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.UNCERTAIN_BLOCK_ENTITY, pos, state);
    }
    protected void onTick(final Level level, final BlockState state) {
        BlockPos pos = blockHit.getBlockPos();
        boolean playerSees = hasLineOfSightOfBlock(level, pos);
        if (playerSees) {
            boolean exists = state.getValue(EXISTENCE);
            level.setBlockAndUpdate(pos, state.setValue(EXISTENCE, !exists));
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
