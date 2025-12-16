package me.dragontrim;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public final class DragonToolAttributes {

    private DragonToolAttributes() {}

    public static void applyWeapon(ItemStack item, String type) {
        if (item == null || !item.hasItemMeta()) return;

        FileConfiguration cfg = DragonTrim.getPlugin(DragonTrim.class).getConfig();
        ItemMeta meta = item.getItemMeta();

        double damage = cfg.getDouble("combat." + type + ".bonus_damage", 0);
        double speed  = cfg.getDouble("combat." + type + ".attack_speed", 0);

        meta.addAttributeModifier(
                Attribute.ATTACK_DAMAGE,
                new AttributeModifier(
                        UUID.randomUUID(),
                        "dragon_damage",
                        damage,
                        AttributeModifier.Operation.ADD_NUMBER,
                        EquipmentSlot.HAND
                )
        );

        meta.addAttributeModifier(
                Attribute.ATTACK_SPEED,
                new AttributeModifier(
                        UUID.randomUUID(),
                        "dragon_attack_speed",
                        speed,
                        AttributeModifier.Operation.ADD_NUMBER,
                        EquipmentSlot.HAND
                )
        );

        item.setItemMeta(meta);
    }

    public static void applyMiningEfficiency(ItemStack item, double value) {
        if (item == null) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        // vorhandene Mining-Efficiency entfernen
        meta.removeAttributeModifier(Attribute.MINING_EFFICIENCY);

        meta.addAttributeModifier(
                Attribute.MINING_EFFICIENCY,
                new AttributeModifier(
                        UUID.randomUUID(),
                        "dragon_mining_efficiency",
                        value,
                        AttributeModifier.Operation.ADD_NUMBER,
                        EquipmentSlot.HAND
                )
        );

        item.setItemMeta(meta);
    }
}
