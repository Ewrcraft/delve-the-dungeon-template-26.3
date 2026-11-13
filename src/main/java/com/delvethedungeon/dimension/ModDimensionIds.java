package com.delvethedungeon.dimension;

import com.delvethedungeon.DelveTheDungeon;
import com.delvethedungeon.chunkGenerator.UncertainChunkGenerator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;

public class ModDimensionIds {
    public static final String dungeon_dimension_regname = "uncertain_cute_dimension";
    public static final ResourceKey<Level> DUNGEON_DIMENSION = ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(DelveTheDungeon.MOD_ID, dungeon_dimension_regname));

    // Make into fabric
    // private static final ResourceKey<Registry<MapCodec<? extends ChunkGenerator>>> CHUNK_GENERATORS = ResourceKey.create(Registries.CHUNK_GENERATOR, DelveTheDungeon.MOD_ID);
    // public static final Holder<MapCodec<? extends ChunkGenerator>, MapCodec<FlatLevelSource>> MY_CHUNK_GEN = CHUNK_GENERATORS.register("dimdungeons_chunkgen", () -> UncertainChunkGenerator.CODEC);

}
