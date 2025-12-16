package me.dragontrim;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;


import java.util.List;

public final class DragonItems {

    private DragonItems() {}

    // =========================
    // Dragon Sword
    // =========================
    public static ItemStack dragonSword() {
        ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Dragon Sword"));
        meta.setLore(List.of(
                "§7Geschmiedet aus Drachenmacht",
                "§8Eine legendäre Waffe"
        ));



        // 🔥 Dragon Armor markieren (PDC)
        meta.getPersistentDataContainer().set(
                PDCKeys.DRAGON_TOOL,
                PersistentDataType.BYTE,
                (byte) 1
        );

        item.setItemMeta(meta);
        DragonToolAttributes.applyWeapon(item, "sword");

        return item;
    }

    // =========================
    // Dragon Pickaxe
    // =========================
    public static ItemStack dragonPickaxe() {
        ItemStack item = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Dragon Pickaxe"));


        // 🔥 Dragon Armor markieren (PDC)
        meta.getPersistentDataContainer().set(
                PDCKeys.DRAGON_TOOL,
                PersistentDataType.BYTE,
                (byte) 1
        );

        item.setItemMeta(meta);
        DragonToolAttributes.applyWeapon(item, "pickaxe");
        DragonToolAttributes.applyMiningEfficiency(item, 50.0);
        return item;
    }

    // =========================
    // Dragon Axe
    // =========================
    public static ItemStack dragonAxe() {
        ItemStack item = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Dragon Axe"));


        // 🔥 Dragon Armor markieren (PDC)
        meta.getPersistentDataContainer().set(
                PDCKeys.DRAGON_TOOL,
                PersistentDataType.BYTE,
                (byte) 1
        );

        item.setItemMeta(meta);
        DragonToolAttributes.applyWeapon(item, "axe");
        DragonToolAttributes.applyMiningEfficiency(item, 50.0);


        return item;
    }

    // =========================
    // Dragon Shovel
    // =========================
    public static ItemStack dragonShovel() {
        ItemStack item = new ItemStack(Material.NETHERITE_SHOVEL);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Dragon Shovel"));


        // 🔥 Dragon Armor markieren (PDC)
        meta.getPersistentDataContainer().set(
                PDCKeys.DRAGON_TOOL,
                PersistentDataType.BYTE,
                (byte) 1
        );

        item.setItemMeta(meta);
        DragonToolAttributes.applyWeapon(item, "shovel");
        DragonToolAttributes.applyMiningEfficiency(item, 50.0);
        return item;
    }

    // =========================
    // Dragon Hoe
    // =========================
    public static ItemStack dragonHoe() {
        ItemStack item = new ItemStack(Material.NETHERITE_HOE);


        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Dragon Hoe"));


        // 🔥 Dragon Armor markieren (PDC)
        meta.getPersistentDataContainer().set(
                PDCKeys.DRAGON_TOOL,
                PersistentDataType.BYTE,
                (byte) 1
        );

        item.setItemMeta(meta);
        DragonToolAttributes.applyWeapon(item, "hoe");
        DragonToolAttributes.applyMiningEfficiency(item, 50.0);
        return item;
    }

    // =========================
    // Dragon Wings (Elytra)
    // =========================
    public static ItemStack dragonWings() {
        ItemStack item = new ItemStack(Material.ELYTRA);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Dragon Wings"));
        meta.setUnbreakable(true);


        // 🔥 Dragon Armor markieren (PDC)
        meta.getPersistentDataContainer().set(
                PDCKeys.DRAGON_WINGS,
                PersistentDataType.BYTE,
                (byte) 1
        );

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack dragonHelmet() {
        ItemStack item = new ItemStack(Material.NETHERITE_HELMET);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.setDisplayName("§6Dragon Helmet");

        // 🔥 Dragon Armor markieren (PDC)
        meta.getPersistentDataContainer().set(
                PDCKeys.DRAGON_ARMOR,
                PersistentDataType.BYTE,
                (byte) 1
        );

        item.setItemMeta(meta);

        // 🔥 Attribute IMMER NACH setItemMeta
        DragonArmorAttributes.apply(
                item,
                EquipmentSlot.HEAD,
                4.0,
                4.0,
                0.2
        );

        return item;
    }


    public static ItemStack dragonChestplate() {
        ItemStack item = new ItemStack(Material.NETHERITE_CHESTPLATE);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.setDisplayName("§6Dragon Chestplate");

        // 🔥 Dragon Armor markieren (PDC)
        meta.getPersistentDataContainer().set(
                PDCKeys.DRAGON_ARMOR,
                PersistentDataType.BYTE,
                (byte) 1
        );

        item.setItemMeta(meta);

        // 🔥 Attribute IMMER NACH setItemMeta
        DragonArmorAttributes.apply(
                item,
                EquipmentSlot.CHEST,
                10.0,
                4.0,
                0.2
        );

        return item;
    }



    public static ItemStack dragonLeggings() {
        ItemStack item = new ItemStack(Material.NETHERITE_LEGGINGS);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.setDisplayName("§6Dragon Leggings");

        // 🔥 Dragon Armor markieren (PDC)
        meta.getPersistentDataContainer().set(
                PDCKeys.DRAGON_ARMOR,
                PersistentDataType.BYTE,
                (byte) 1
        );

        item.setItemMeta(meta);

        // 🔥 Attribute IMMER NACH setItemMeta
        DragonArmorAttributes.apply(
                item,
                EquipmentSlot.LEGS,
                7.0,
                4.0,
                0.2
        );

        return item;
    }


    public static ItemStack dragonBoots() {
        ItemStack item = new ItemStack(Material.NETHERITE_BOOTS);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.setDisplayName("§6Dragon Boots");

        meta.getPersistentDataContainer().set(
                PDCKeys.DRAGON_ARMOR,
                PersistentDataType.BYTE,
                (byte) 1
        );

        item.setItemMeta(meta);

        // 🔥 Attribute IMMER NACH setItemMeta
        DragonArmorAttributes.apply(
                item,
                EquipmentSlot.FEET,
                4.0,
                4.0,
                0.2
        );

        return item;
    }



    public static boolean isDragonArmor(ItemStack item) {
        if (item == null) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;

        return meta.getPersistentDataContainer().has(
                PDCKeys.DRAGON_ARMOR,
                PersistentDataType.BYTE
        );
    }

    public static boolean isDragonHelmet(ItemStack item) {
        return isDragonArmor(item);
    }

    public static boolean isDragonChestplate(ItemStack item) {
        return isDragonArmor(item);
    }

    public static boolean isDragonLeggings(ItemStack item) {
        return isDragonArmor(item);
    }

    public static boolean isDragonBoots(ItemStack item) {
        return isDragonArmor(item);
    }



}
