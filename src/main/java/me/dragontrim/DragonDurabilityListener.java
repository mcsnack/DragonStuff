package me.dragontrim;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class DragonDurabilityListener implements Listener {

    private final FileConfiguration config;

    public DragonDurabilityListener() {
        this.config = DragonTrim.getPlugin(DragonTrim.class).getConfig();
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        ItemStack item = event.getItem();
        if (item == null || !item.hasItemMeta()) return;

        var pdc = item.getItemMeta().getPersistentDataContainer();

        // 🛡 Dragon Armor
        if (pdc.has(PDCKeys.DRAGON_ARMOR, PersistentDataType.BYTE)) {
            double reduction = config.getDouble("durability.armor.reduction", 0.0);
            if (Math.random() < reduction) {
                event.setCancelled(true);
            }
            return;
        }

        // ⚔ Dragon Tool
        if (pdc.has(PDCKeys.DRAGON_TOOL, PersistentDataType.BYTE)) {
            double reduction = config.getDouble("durability.tools.reduction", 0.0);
            if (Math.random() < reduction) {
                event.setCancelled(true);
            }
        }
    }
}
