package me.dragontrim;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public final class DragonToolAttributes {

    private DragonToolAttributes() {}

    // =========================
    // WEAPON DAMAGE + SPEED
    // =========================
    public static void applyWeapon(ItemMeta meta, double bonusDamage, double bonusSpeed) {

        meta.addAttributeModifier(
                Attribute.ATTACK_DAMAGE,
                new AttributeModifier(
                        UUID.randomUUID(),
                        "dragon_bonus_damage",
                        bonusDamage,
                        AttributeModifier.Operation.ADD_NUMBER,
                        EquipmentSlot.HAND
                )
        );

        meta.addAttributeModifier(
                Attribute.ATTACK_SPEED,
                new AttributeModifier(
                        UUID.randomUUID(),
                        "dragon_bonus_speed",
                        bonusSpeed,
                        AttributeModifier.Operation.ADD_NUMBER,
                        EquipmentSlot.HAND
                )
        );
    }

    // =========================
    // MINING EFFICIENCY
    // =========================
    public static void applyMiningEfficiency(ItemMeta meta, double value) {
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
    }
}
