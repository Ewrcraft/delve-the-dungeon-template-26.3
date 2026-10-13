package com.delvethedungeon.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record ComponentWithTooltip(String translationKey, String formatting) implements TooltipProvider {
    @Override
    public void addToTooltip(Item.TooltipContext tooltip, Consumer<Component> textConsumer, TooltipFlag type, DataComponentGetter components) {
        textConsumer.accept(formattedString(translationKey, formatting));
    }

    private static MutableComponent formattedString(String translationKey, String formatting) {
        MutableComponent baseString = Component.translatable(translationKey);
        for (int i = 0; i < formatting.length(); i++) {
            baseString = baseString.withStyle(ChatFormatting.getByCode(formatting.charAt(i)));
        }
        return baseString;
    }

    public String formattingAsString() {
        return "" + this.formatting;
    }

    public static final Codec<ComponentWithTooltip> CODEC = RecordCodecBuilder.create(builder -> {
        return builder.group(
                Codec.STRING.fieldOf("translationkey").forGetter(ComponentWithTooltip::translationKey),
                Codec.STRING.fieldOf("formatting").forGetter(ComponentWithTooltip::formatting)
        ).apply(builder, ComponentWithTooltip::new);
    });

    public static final StreamCodec<FriendlyByteBuf, ComponentWithTooltip> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, ComponentWithTooltip::translationKey,
            ByteBufCodecs.STRING_UTF8, ComponentWithTooltip::formatting,
            ComponentWithTooltip::new
    );
}