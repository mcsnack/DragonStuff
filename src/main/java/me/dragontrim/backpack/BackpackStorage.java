package me.dragontrim.backpack;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/** Persistiert Backpack-Inhalte und den zuletzt bekannten Besitzer in plugins/DragonTrim/backpacks.yml. */
public final class BackpackStorage {

    public record Entry(UUID id, UUID owner, String ownerName, int tier) {}

    private final Plugin plugin;
    private final File file;
    private final YamlConfiguration yaml;

    public BackpackStorage(Plugin plugin) {
        this.plugin = plugin;
        if (!plugin.getDataFolder().exists() && !plugin.getDataFolder().mkdirs()) {
            plugin.getLogger().severe("Plugin-Datenordner konnte nicht erstellt werden.");
        }
        this.file = new File(plugin.getDataFolder(), "backpacks.yml");
        this.yaml = YamlConfiguration.loadConfiguration(file);
    }

    /** Gibt nie weniger Slots zurück als gespeichert sind, damit Inhalte nicht abgeschnitten werden. */
    public synchronized ItemStack[] load(UUID id, int size) {
        String data = yaml.getString("contents." + id);
        ItemStack[] stored;
        try {
            stored = data == null ? new ItemStack[0]
                    : ItemStack.deserializeItemsFromBytes(Base64.getDecoder().decode(data));
        } catch (RuntimeException ex) {
            plugin.getLogger().severe("Backpack-Daten für " + id + " sind beschädigt: " + ex.getMessage());
            return new ItemStack[Math.max(27, size)];
        }

        ItemStack[] result = new ItemStack[Math.max(size, stored.length)];
        System.arraycopy(stored, 0, result, 0, stored.length);
        return result;
    }

    public synchronized void save(UUID id, ItemStack[] contents) {
        ItemStack[] copy = new ItemStack[contents.length];
        for (int i = 0; i < contents.length; i++) {
            copy[i] = contents[i] == null ? new ItemStack(Material.AIR) : contents[i].clone();
        }
        yaml.set("contents." + id,
                Base64.getEncoder().encodeToString(ItemStack.serializeItemsAsBytes(copy)));
        write();
    }

    public synchronized void registerOwner(UUID id, UUID owner, String ownerName, int tier) {
        String path = "meta." + id + ".";
        if (owner.toString().equals(yaml.getString(path + "owner"))
                && ownerName.equals(yaml.getString(path + "name"))
                && tier == yaml.getInt(path + "tier")) return;

        yaml.set(path + "owner", owner.toString());
        yaml.set(path + "name", ownerName);
        yaml.set(path + "tier", tier);
        write();
    }

    public synchronized List<Entry> all() {
        List<Entry> entries = new ArrayList<>();
        ConfigurationSection meta = yaml.getConfigurationSection("meta");
        if (meta == null) return entries;

        for (String key : meta.getKeys(false)) {
            try {
                entries.add(new Entry(
                        UUID.fromString(key),
                        UUID.fromString(meta.getString(key + ".owner", "")),
                        meta.getString(key + ".name", "?"),
                        meta.getInt(key + ".tier", 1)
                ));
            } catch (IllegalArgumentException ignored) {
                plugin.getLogger().warning("Ungültiger Backpack-Metadatensatz: " + key);
            }
        }
        return entries;
    }

    private void write() {
        try {
            yaml.save(file);
        } catch (IOException ex) {
            plugin.getLogger().severe("Konnte backpacks.yml nicht speichern: " + ex.getMessage());
        }
    }
}
