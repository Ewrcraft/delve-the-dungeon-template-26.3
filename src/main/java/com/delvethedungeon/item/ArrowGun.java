package com.delvethedungeon.item;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.entity.projectile.hurtingprojectile.windcharge.WindCharge;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;


public class ArrowGun extends Item implements ProjectileItem {
    public ArrowGun(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        System.out.println("Ran the use");
        if (level instanceof ServerLevel serverLevel) {
            System.out.println("Ran this thingy");
            Projectile.spawnProjectileFromRotation((source, l, itemStack) -> new Arrow(level, player.position().x(), player.getEyePosition().y(), player.position().z(), stack, null), serverLevel, stack, player, 0.0F, 70F, 0.0F);
        }

        level.playSound((Entity)null, player.getX(), player.getY(), player.getZ(), SoundEvents.WIND_CHARGE_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResult.SUCCESS;
    }

    public Projectile asProjectile(final Level level, final Position position, final ItemStack itemStack, final Direction direction) {
        RandomSource random = level.getRandom();
        double dirX = random.triangle((double)direction.getStepX(), 0.11485000000000001);
        double dirY = random.triangle((double)direction.getStepY(), 0.11485000000000001);
        double dirZ = random.triangle((double)direction.getStepZ(), 0.11485000000000001);
        Vec3 dir = new Vec3(dirX, dirY, dirZ);
        Projectile arrow = new Arrow(level, position.x(), position.y(), position.z(), itemStack, itemStack);
        arrow.setDeltaMovement(dir);
        return arrow;
    }

    public void shoot(final Projectile projectile, final double xd, final double yd, final double zd, final float pow, final float uncertainty) {
    }

    public ProjectileItem.DispenseConfig createDispenseConfig() {
        return DispenseConfig.builder().positionFunction((source, direction) -> DispenserBlock.getDispensePosition(source, (double)1.0F, Vec3.ZERO)).uncertainty(6.6666665F).power(1.0F).overrideDispenseEvent(1051).build();
    }
}
