package com.delvethedungeon.item;
import com.ibm.icu.impl.coll.CollationRoot;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.entity.projectile.hurtingprojectile.windcharge.WindCharge;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.List;

import static net.minecraft.world.item.Items.WIND_CHARGE;


public class ArrowGun extends BowItem {
    public ArrowGun(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        ItemStack projectile = player.getProjectile(itemStack);
        ItemStack windchargeitem = null;
        for (ItemStack windprojectile : player.getInventory()){
            if (windprojectile.is(WIND_CHARGE)){
                windchargeitem = windprojectile;
                break;
            }
        }
        if (projectile.isEmpty() || windchargeitem == null && !player.hasInfiniteMaterials()) {
            return InteractionResult.FAIL;
        }
        System.out.println("Ran the use");
        List<ItemStack> firedProjectiles = draw(itemStack, projectile, player);
        if (level instanceof ServerLevel serverLevel) {
            System.out.println("Ran this thingy");
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WIND_CHARGE_BURST, SoundSource.NEUTRAL, 1F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.NEUTRAL, 1F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            this.shoot(serverLevel, player, player.getUsedItemHand(), projectile, firedProjectiles, 30F, 1.0F, 30F == 30F, (LivingEntity)null);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.hasInfiniteMaterials()) {
            windchargeitem.consume(1, player);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void shoot(final ServerLevel level, final LivingEntity shooter, final InteractionHand hand, final ItemStack weapon, final List<ItemStack> projectiles, final float power, final float uncertainty, final boolean isCrit, final @Nullable LivingEntity targetOverride) {
        float maxAngle = EnchantmentHelper.processProjectileSpread(level, weapon, shooter, 0.0F);
        float angleStep = projectiles.size() == 1 ? 0.0F : 2.0F * maxAngle / (float)(projectiles.size() - 1);
        float angleOffset = (float)((projectiles.size() - 1) % 2) * angleStep / 2.0F;
        float direction = 1.0F;

        for(var ref = new Object() {
            int i = 0;
        }; ref.i < projectiles.size(); ++ref.i) {
            ItemStack projectile = (ItemStack)projectiles.get(ref.i);
            if (!projectile.isEmpty()) {
                float angle = angleOffset + direction * (float)((ref.i + 1) / 2) * angleStep;
                direction = -direction;
                Projectile.spawnProjectile(this.createProjectile(level, shooter, weapon, projectile, isCrit), level, projectile, (projectileEntity) -> this.shootProjectile(shooter, projectileEntity, ref.i, power, uncertainty, angle, targetOverride));
                weapon.hurtAndBreak(this.getDurabilityUse(projectile), shooter, hand.asEquipmentSlot());
                if (weapon.isEmpty()) {
                    break;
                }
            }
        }

    }

}
