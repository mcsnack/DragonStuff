package me.dragontrim.backpack;

import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.ShulkerBox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingInventory;
import org.bukkit.inventory.SmithingTransformRecipe;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.plugin.Plugin;

import java.util.UUID;
import java.util.function.Supplier;

/** Schmiedetisch-Rezept: Dragon-Trim + Shulker-Box/Backpack + Netherite-Barren. */
public final class BackpackSmithing implements Listener {

    private final Plugin plugin;
    private final BackpackItems items;
    private final BackpackStorage storage;
    private final NamespacedKey recipeKey;

    public BackpackSmithing(Plugin plugin, BackpackItems items, BackpackStorage storage) {
        this.plugin = plugin;
        this.items = items;
        this.storage = storage;
        this.recipeKey = new NamespacedKey(plugin, "backpack");
    }

    /** Dragon-Trim wird aus dem bestehenden DragonStuff-Item-System geliefert. */
    public void register(Supplier<ItemStack> dragonTrim) {
        unregister();
        ItemStack trim = dragonTrim.get();
        if (trim == null || trim.getType().isAir()) {
            plugin.getLogger().severe("Backpack-Rezept nicht registriert: Dragon-Trim fehlt.");
            return;
        }

        SmithingTransformRecipe recipe = new SmithingTransformRecipe(
                recipeKey,
                items.create(BackpackItems.DEFAULT_TYPE, 1, new UUID(0, 0)),
                new RecipeChoice.ExactChoice(trim),
                new RecipeChoice.MaterialChoice(Tag.SHULKER_BOXES),
                new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT)
        );
        if (!Bukkit.addRecipe(recipe)) {
            plugin.getLogger().warning("Backpack-Schmiederezept konnte nicht registriert werden.");
        }
    }

    public void unregister() {
        Bukkit.removeRecipe(recipeKey);
    }

    @EventHandler
    public void onPrepare(PrepareSmithingEvent event) {
        SmithingInventory inventory = event.getInventory();
        if (!(inventory.getRecipe() instanceof Keyed keyed)
                || !keyed.getKey().equals(recipeKey)) return;

        ItemStack base = inventory.getInputEquipment();
        Integer tier = items.tier(base);
        if (tier != null) {
            UUID id = items.id(base);
            event.setResult(id != null && tier < BackpackItems.MAX_TIER
                    ? items.create(base.getType(), tier + 1, id,
                    storage.load(id, BackpackItems.slotsFor(tier))) : null);
            return;
        }

        // Neue Rucksäcke übernehmen die Farbe der als Basis eingesetzten Shulker-Box.
        event.setResult(isEmptyShulker(base)
                ? items.create(base.getType(), 1, UUID.randomUUID()) : null);
    }

    /** Verhindert, dass Inhalte einer befüllten Shulker-Box beim Crafting verloren gehen. */
    private boolean isEmptyShulker(ItemStack stack) {
        if (stack == null || !Tag.SHULKER_BOXES.isTagged(stack.getType())) return false;
        if (!(stack.getItemMeta() instanceof BlockStateMeta meta)) return true;
        return !(meta.getBlockState() instanceof ShulkerBox box) || box.getInventory().isEmpty();
    }
}
