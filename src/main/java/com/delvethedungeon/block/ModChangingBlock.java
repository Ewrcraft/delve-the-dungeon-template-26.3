package com.delvethedungeon.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;

import static net.minecraft.world.level.block.TntBlock.prime;

public class ModChangingBlock extends Block {
    public ModChangingBlock(Properties properties) {
        super(properties);
    }

    public BlockState playerWillDestroy(final Level level, final BlockPos pos, final BlockState state, final Player player) {

        prime(level, pos);

        return super.playerWillDestroy(level, pos, state, player);
    }

    public void wasExploded(final ServerLevel level, final BlockPos pos, final Explosion explosion) {
        if ((Boolean)level.getGameRules().get(GameRules.TNT_EXPLODES)) {
            PrimedTnt primed = new PrimedTnt(level, (double)pos.getX() + (double)0.5F, (double)pos.getY(), (double)pos.getZ() + (double)0.5F, explosion.getIndirectSourceEntity());
            primed.setFuse(PrimedTnt.getRandomShortFuse(primed.getFuse(), level.getRandom()));
            level.addFreshEntity(primed);
        }
    }
}
