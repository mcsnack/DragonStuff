package me.dragontrim;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public final class DragonArmorAttributes {

    private DragonArmorAttributes() {}

    public static void apply(
            ItemStack item,
            EquipmentSlot slot,
            double armor,
            double toughness,
            double knockback
    ) {
        if (item == null) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        // ⚠️ WICHTIG:
        // Wir entfernen NICHT pauschal alle Attribute,
        // sondern überschreiben gezielt die relevanten
        meta.removeAttributeModifier(Attribute.ARMOR);
        meta.removeAttributeModifier(Attribute.ARMOR_TOUGHNESS);
        meta.removeAttributeModifier(Attribute.KNOCKBACK_RESISTANCE);

        // Armor
        meta.addAttributeModifier(
                Attribute.ARMOR,
                new AttributeModifier(
                        UUID.randomUUID(),
                        "dragon_armor",
                        armor,
                        AttributeModifier.Operation.ADD_NUMBER,
                        slot
                )
        );

        // Armor Toughness
        meta.addAttributeModifier(
                Attribute.ARMOR_TOUGHNESS,
                new AttributeModifier(
                        UUID.randomUUID(),
                        "dragon_toughness",
                        toughness,
                        AttributeModifier.Operation.ADD_NUMBER,
                        slot
                )
        );

        // Knockback Resistance
        meta.addAttributeModifier(
                Attribute.KNOCKBACK_RESISTANCE,
                new AttributeModifier(
                        UUID.randomUUID(),
                        "dragon_knockback",
                        knockback,
                        AttributeModifier.Operation.ADD_NUMBER,
                        slot
                )
        );

        item.setItemMeta(meta);
    }
}
