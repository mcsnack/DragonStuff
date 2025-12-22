package me.dragontrim.lang;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public final class Lang {

    private static FileConfiguration lang;
    private static File langFile;

    private static final MiniMessage MM = MiniMessage.miniMessage();

    private Lang() {}

    // =========================
    // LOAD / RELOAD
    // =========================
    public static void load(JavaPlugin plugin) {
        String code = plugin.getConfig().getString("language", "de_DE");

        File folder = new File(plugin.getDataFolder(), "lang");
        if (!folder.exists()) folder.mkdirs();

        langFile = new File(folder, code + ".yml");

        if (!langFile.exists()) {
            plugin.saveResource("lang/" + code + ".yml", false);
        }

        lang = YamlConfiguration.loadConfiguration(langFile);

        Bukkit.getLogger().info("[DragonTrim] Loaded language: " + langFile.getName());
    }

    public static void reload(JavaPlugin plugin) {
        load(plugin);
    }

    // =========================
    // COMPONENTS
    // =========================
    public static Component getComponent(String path) {
        String raw = lang.getString(path);
        if (raw == null) {
            Bukkit.getLogger().warning("[DragonTrim] Missing lang key: " + path);
            return Component.text(path);
        }
        return MM.deserialize(raw);
    }

    public static List<Component> getComponentList(String path) {
        List<String> list = lang.getStringList(path);
        if (list == null || list.isEmpty()) {
            Bukkit.getLogger().warning("[DragonTrim] Missing lang list: " + path);
            return List.of(Component.text(path));
        }

        return list.stream()
                .map(MM::deserialize)
                .toList();
    }
}
