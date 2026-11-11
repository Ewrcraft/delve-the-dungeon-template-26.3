package com.delvethedungeon.client.dimension;

import com.delvethedungeon.DelveTheDungeon;
import com.mojang.serialization.MapCodec;
import net.fabricmc.loader.impl.lib.tinyremapper.extension.mixin.common.data.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TimelineTags;
import net.minecraft.util.ARGB;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.attribute.*;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.densityfunction.SamplerContext;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDimensions {
    public static final ResourceKey<LevelStem> UNCERTAIN_CUTE_DIMENSION_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            Identifier.fromNamespaceAndPath(DelveTheDungeon.MOD_ID, "uncertain_cute_dimension"));

    public static final ResourceKey<Level> UNCERTAIN_CUTE_DIMENSION_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            Identifier.fromNamespaceAndPath(DelveTheDungeon.MOD_ID, "uncertain_cute_dimension"));

    public static final ResourceKey<DimensionType> UNCERTAIN_CUTE_DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            Identifier.fromNamespaceAndPath(DelveTheDungeon.MOD_ID, "uncertain_cute_dimension_type"));

    public static void bootstrapType(BootstrapContext<DimensionType> context) {
        var timelines = context.lookup(Registries.TIMELINE);
        var clocks = context.lookup(Registries.WORLD_CLOCK);
        var blocks = context.lookup(Registries.BLOCK);

        context.register(UNCERTAIN_CUTE_DIMENSION_TYPE, new DimensionType(
                false, // hasFixedTime
                true, // hasSkylight
                false, // hasCeiling
                false, // hasEnderDragonFight
                1.0, // coordinateScale
                -100, // minY
                400, // height
                256, // logicalHeight
                blocks.getOrThrow(BlockTags.INFINIBURN_END), // infiniburn
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(ConstantInt.of(0), 0),
                DimensionType.Skybox.OVERWORLD,
                CardinalLighting.Type.DEFAULT,
                EnvironmentAttributeMap.builder()
                        .set(EnvironmentAttributes.FOG_COLOR, ARGB.vector3fFromRGB24(0))
                        .set(EnvironmentAttributes.SKY_COLOR, ARGB.vector3fFromRGB24(2))
                        .set(EnvironmentAttributes.AMBIENT_LIGHT_COLOR, ARGB.vector3fFromRGB24(-4129186))
                        .set(EnvironmentAttributes.CLOUD_COLOR, ARGB.vector4fFromARGB32(50))
                        .set(EnvironmentAttributes.CLOUD_HEIGHT, 123F)
                        .set(EnvironmentAttributes.BACKGROUND_MUSIC, BackgroundMusic.OVERWORLD)
                        .set(EnvironmentAttributes.BED_RULE, BedRule.CAN_SLEEP_WHEN_DARK)
                        .set(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS, false)
                        .set(EnvironmentAttributes.NETHER_PORTAL_SPAWNS_PIGLINS, true)
                        .set(EnvironmentAttributes.AMBIENT_SOUNDS, AmbientSounds.LEGACY_CAVE_SETTINGS)
                        .build(),
                timelines.getOrThrow(TimelineTags.IN_OVERWORLD),
                Optional.of(clocks.getOrThrow(WorldClocks.OVERWORLD))));
    }

    static ChunkGenerator test = new ChunkGenerator(DimensionTypes.bootstrap();) {
        @Override
        protected MapCodec<? extends ChunkGenerator> codec() {
            return null;
        }

        @Override
        public void spawnOriginalMobs(WorldGenRegion worldGenRegion) {

        }

        @Override
        public int getGenDepth() {
            return 0;
        }

        @Override
        public CompletableFuture<ChunkAccess> buildTerrain(ChunkAccess chunk, Blender blender, RandomState randomState, StructureManager structureManager, BiomeManager biomeManager, @Nullable WorldGenRegion carverBiomeRegion, Set<Holder<Biome>> possibleBiomes) {
            return null;
        }

        @Override
        public int getSeaLevel() {
            return 0;
        }

        @Override
        public int getMinY() {
            return 0;
        }

        @Override
        public int getBaseHeight(int x, int z, Heightmap.Types type, LevelHeightAccessor heightAccessor, RandomState randomState) {
            return 0;
        }

        @Override
        public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor heightAccessor, RandomState randomState) {
            return null;
        }

        @Override
        public void addDebugScreenInfo(List<String> result, RandomState randomState, BlockPos feetPos, SamplerContext samplerContext) {

        }
    };

    public static void bootstrapStem(BootstrapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);

        LevelStem stem = new LevelStem(dimTypes.getOrThrow(ModDimensions.UNCERTAIN_CUTE_DIMENSION_TYPE), test);

        context.register(UNCERTAIN_CUTE_DIMENSION_KEY, stem);
    }
}
