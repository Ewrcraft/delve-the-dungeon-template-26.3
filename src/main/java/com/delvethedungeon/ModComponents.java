package com.delvethedungeon;

import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;

public class ModComponents {

    public static final DataComponentType<Integer> CLICK_COUNT_COMPONENT = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            DelveTheDungeon.id("click_count"),
            DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build()
    );

    public static final DataComponentType<ComponentWithTooltip> COMPONENT_WITH_TOOLTIP = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            DelveTheDungeon.id("tooltip_first"),
            DataComponentType.<ComponentWithTooltip>builder().persistent(ComponentWithTooltip.CODEC).networkSynchronized(ComponentWithTooltip.STREAM_CODEC).build()
    );

    public static void initialize() {
        ItemComponentTooltipProviderRegistry.addAfter(DataComponents.DAMAGE, ModComponents.COMPONENT_WITH_TOOLTIP);
    }

}
