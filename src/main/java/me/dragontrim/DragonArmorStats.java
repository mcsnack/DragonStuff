package me.dragontrim;

import org.bukkit.configuration.file.FileConfiguration;

public final class DragonArmorStats {

    private static FileConfiguration config;

    private DragonArmorStats() {}

    public static void init(DragonTrim plugin) {
        config = plugin.getConfig();
    }

    public static double armor(String part) {
        return config.getDouble("armor." + part + ".armor");
    }

    public static double toughness(String part) {
        return config.getDouble("armor." + part + ".toughness");
    }

    public static double knockback(String part) {
        return config.getDouble("armor." + part + ".knockback");
    }
}
