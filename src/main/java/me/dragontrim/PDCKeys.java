package me.dragontrim;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class PDCKeys {

    // 🔑 Marker für das Smithing-Template (Netherstar)
    public static NamespacedKey DRAGON_TRIM;

    // 🔑 Marker für Dragon-Rüstung (Helmet, Chestplate, Leggings, Boots)
    public static NamespacedKey DRAGON_ARMOR;

    // 🔑 Marker für Dragon-Tools + Wings
    public static NamespacedKey DRAGON_TOOL;

    // Marker für Dragon Wings
    public static NamespacedKey DRAGON_WINGS;


    private PDCKeys() {
        // Utility class
    }

    public static void init(JavaPlugin plugin) {
        DRAGON_TRIM  = new NamespacedKey(plugin, "dragon_trim");
        DRAGON_ARMOR = new NamespacedKey(plugin, "dragon_armor");
        DRAGON_TOOL  = new NamespacedKey(plugin, "dragon_tool");
        DRAGON_WINGS = new NamespacedKey(plugin, "dragon_wings");

    }
}
