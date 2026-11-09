package me.dragontrim.backpack;

import org.bukkit.Tag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftingInventory;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

/**
 * Erhält Backpack-ID, Tier und Inhalt beim normalen Färben einer Shulker-Box.
 * Normale Shulker-Boxen bleiben vollständig beim Vanilla-Rezept.
 */
public final class BackpackDyeListener implements Listener {

    private final BackpackItems items;
    private final BackpackStorage storage;
    private final Plugin plugin;

    public BackpackDyeListener(Plugin plugin, BackpackItems items, BackpackStorage storage) {
        this.plugin = plugin;
        this.items = items;
        this.storage = storage;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        if (event.isRepair()) return;

        CraftingInventory inventory = event.getInventory();
        ItemStack vanillaResult = inventory.getResult();
        if (vanillaResult == null || vanillaResult.getType().isAir()
                || !Tag.SHULKER_BOXES.isTagged(vanillaResult.getType())) return;

        ItemStack backpack = null;
        for (ItemStack ingredient : inventory.getMatrix()) {
            if (!items.isBackpack(ingredient)) continue;
            if (backpack != null) {
                // Zwei Backpack-IDs können nicht in einem Ergebnis zusammengeführt werden.
                inventory.setResult(null);
                return;
            }
            backpack = ingredient;
        }
        if (backpack == null) return;

        Integer tier = items.tier(backpack);
        UUID id = items.id(backpack);
        if (tier == null || id == null) {
            plugin.getLogger().warning("DragonBackPack konnte beim Färben nicht erkannt werden; Ergebnis wurde blockiert.");
            inventory.setResult(null);
            return;
        }

        ItemStack[] contents = storage.load(id, BackpackItems.slotsFor(tier));
        // Das Vanilla-Färberezept bestimmt die Zielfarbe; PDC und Inhalt werden erhalten.
        inventory.setResult(items.create(vanillaResult.getType(), tier, id, contents));
    }
}
