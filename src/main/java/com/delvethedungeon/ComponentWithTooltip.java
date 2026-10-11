package com.delvethedungeon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record ComponentWithTooltip(String tooltipText) implements TooltipProvider {
    @Override
    public void addToTooltip(Item.TooltipContext tooltip, Consumer<Component> textConsumer, TooltipFlag type, DataComponentGetter components) {
        textConsumer.accept(Component.translatable(tooltipText, 0).withStyle(ChatFormatting.AQUA));
    }

    public static final Codec<ComponentWithTooltip> CODEC = RecordCodecBuilder.create(builder -> {
        return builder.group(
                Codec.STRING.fieldOf("textVariable").forGetter(ComponentWithTooltip::tooltipText)
        ).apply(builder, ComponentWithTooltip::new);
    });

    public static final StreamCodec<FriendlyByteBuf, ComponentWithTooltip> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, ComponentWithTooltip::tooltipText,
            ComponentWithTooltip::new
    );
}