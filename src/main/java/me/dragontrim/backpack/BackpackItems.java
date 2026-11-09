package me.dragontrim.backpack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** Erzeugt und erkennt DragonBackPack-Items (Tier 1-4). */
public final class BackpackItems {

    public static final int MAX_TIER = 4;
    public static final Material DEFAULT_TYPE = Material.PURPLE_SHULKER_BOX;

    private final NamespacedKey tierKey;
    private final NamespacedKey idKey;

    public BackpackItems(Plugin plugin) {
        this.tierKey = new NamespacedKey(plugin, "backpack_tier");
        this.idKey = new NamespacedKey(plugin, "backpack_id");
    }

    /** Tier 1 = 27, Tier 2 = 36, Tier 3 = 45, Tier 4 = 54 Slots. */
    public static int slotsFor(int tier) {
        int safeTier = Math.max(1, Math.min(MAX_TIER, tier));
        return 27 + 9 * (safeTier - 1);
    }

    public static int tierFor(int slots) {
        return Math.max(1, Math.min(MAX_TIER, (slots - 27) / 9 + 1));
    }

    public static Component title(int tier) {
        int safeTier = Math.max(1, Math.min(MAX_TIER, tier));
        return Component.text("DragonBackPack Tier " + safeTier + " (" + slotsFor(safeTier) + " Slots)",
                NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false);
    }

    public ItemStack create(Material type, int tier, UUID id) {
        if (type == null || !Tag.SHULKER_BOXES.isTagged(type)) {
            type = DEFAULT_TYPE;
        }
        int safeTier = Math.max(1, Math.min(MAX_TIER, tier));
        ItemStack item = new ItemStack(type);
        item.editMeta(meta -> {
            meta.displayName(title(safeTier));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Shift + F oder Rechtsklick zum Öffnen", NamedTextColor.GRAY)
                    .decoration(TextDecoration.ITALIC, false));
            if (safeTier < MAX_TIER) {
                lore.add(Component.text("Upgrade: Dragon-Trim + Netherite-Barren", NamedTextColor.DARK_GRAY)
                        .decoration(TextDecoration.ITALIC, false));
            }
            meta.lore(lore);
            meta.getPersistentDataContainer().set(tierKey, PersistentDataType.INTEGER, safeTier);
            meta.getPersistentDataContainer().set(idKey, PersistentDataType.STRING,
                    (id == null ? UUID.randomUUID() : id).toString());
        });
        return item;
    }

    public Integer tier(ItemStack item) {
        if (item == null || item.getType().isAir()
                || !Tag.SHULKER_BOXES.isTagged(item.getType()) || !item.hasItemMeta()) return null;
        Integer tier = item.getItemMeta().getPersistentDataContainer()
                .get(tierKey, PersistentDataType.INTEGER);
        return tier != null && tier >= 1 && tier <= MAX_TIER ? tier : null;
    }

    public UUID id(ItemStack item) {
        if (item == null || item.getType().isAir()
                || !Tag.SHULKER_BOXES.isTagged(item.getType()) || !item.hasItemMeta()) return null;
        String value = item.getItemMeta().getPersistentDataContainer()
                .get(idKey, PersistentDataType.STRING);
        try {
            return value == null ? null : UUID.fromString(value);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    public boolean isBackpack(ItemStack item) {
        return tier(item) != null && id(item) != null;
    }
}
