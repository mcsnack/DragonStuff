package me.dragontrim;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class DragonSmithingListener implements Listener {

    @EventHandler
    public void onSmithing(PrepareSmithingEvent event) {
        ItemStack result = event.getResult();
        if (result == null) return;

        ItemMeta meta = result.getItemMeta();
        if (meta == null) return;

        // 🔥 Nur Netherite Armor behandeln
        switch (result.getType()) {

            case NETHERITE_HELMET -> applyArmor(
                    result, meta,
                    EquipmentSlot.HEAD,
                    4.0, 4.0, 0.2
            );

            case NETHERITE_CHESTPLATE -> applyArmor(
                    result, meta,
                    EquipmentSlot.CHEST,
                    10.0, 4.0, 0.2
            );

            case NETHERITE_LEGGINGS -> applyArmor(
                    result, meta,
                    EquipmentSlot.LEGS,
                    7.0, 4.0, 0.2
            );

            case NETHERITE_BOOTS -> applyArmor(
                    result, meta,
                    EquipmentSlot.FEET,
                    4.0, 4.0, 0.2
            );

            default -> {
                return; // Kein Armor → nichts tun
            }
        }

        event.setResult(result);
    }

    private void applyArmor(
            ItemStack item,
            ItemMeta meta,
            EquipmentSlot slot,
            double armor,
            double toughness,
            double knockback
    ) {
        // 🔥 Dragon Armor markieren (Trim bleibt!)
        meta.getPersistentDataContainer().set(
                PDCKeys.DRAGON_ARMOR,
                PersistentDataType.BYTE,
                (byte) 1
        );

        item.setItemMeta(meta);

        // 🔥 Attribute setzen (nach Meta!)
        DragonArmorAttributes.apply(
                item,
                slot,
                armor,
                toughness,
                knockback
        );
    }
}
