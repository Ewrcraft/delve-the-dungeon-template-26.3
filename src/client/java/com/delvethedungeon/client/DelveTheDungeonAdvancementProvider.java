package com.delvethedungeon.client;

import com.delvethedungeon.DelveTheDungeon;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.*;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static com.delvethedungeon.ModBlocks.CERTAIN_SOMEONE;

public class DelveTheDungeonAdvancementProvider extends FabricAdvancementProvider {

    protected DelveTheDungeonAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider wrapperLookup, Consumer<AdvancementHolder> consumer) {

        Item item = CERTAIN_SOMEONE.asItem();

                AdvancementHolder getBlockThing = Advancement.Builder.advancement()
                        .parent(createPlaceholder(Identifier.withDefaultNamespace("adventure/root")))
                .display(
                        item, // The display icon
                        Component.literal("Who might you be?"), // The title
                        Component.literal("The texture could have been worse."), // The description
                        //Identifier.withDefaultNamespace("gui/advancements/backgrounds/adventure"), // Background image for the tab in the advancements page, if this is a root advancement (has no parent)
                        AdvancementType.TASK, // TASK, CHALLENGE, or GOAL
                        true, // Show the toast when completing it
                        true, // Announce it to chat
                        false // Hide it in the advancement tab until it's achieved
                )
                // "got_dirt" is the name referenced by other advancements when they want to have "requirements."
                .addCriterion("got_blockthing", InventoryChangeTrigger.TriggerInstance.hasItems(item))
                // Give the advancement an id
                .save(consumer, DelveTheDungeon.id("get_blockthing"));

    }
}
