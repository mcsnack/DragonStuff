package me.dragontrim;

import me.dragontrim.lang.Lang;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.components.EquippableComponent;

public final class DragonItems {

    private DragonItems() {}
    private static void applyDragonModel(ItemMeta meta, String modelName) {
        meta.setItemModel(new NamespacedKey("dragonstuff", modelName));
    }

    // Modell fuer getragene Ruestung / Elytra, unabhaengig vom Inventar-Modell.
    // Der Slot wird explizit gesetzt, damit z.B. eine Brustplatte keine Helm-Ausrüstung wird.
    private static void applyDragonEquipment(ItemMeta meta, String modelName, EquipmentSlot slot) {
        EquippableComponent equippable = meta.getEquippable();
        equippable.setSlot(slot);
        equippable.setModel(new NamespacedKey("dragonstuff", modelName));
        meta.setEquippable(equippable);
    }

    // =========================
    // Dragon Sword
    // =========================
    public static ItemStack dragonSword() {
        ItemStack item = new ItemStack(Material.NETHERITE_SWORD);

        item.editMeta(meta -> {
            meta.displayName(Lang.getComponent("items.sword.name"));
            meta.lore(Lang.getComponentList("items.sword.lore"));
            // dragonSword()
            applyDragonModel(meta, "dragon_sword");

            meta.getPersistentDataContainer().set(
                    PDCKeys.DRAGON_TOOL,
                    PersistentDataType.BYTE,
                    (byte) 1
            );

            // ✅ +10 Damage | +2 Speed
            DragonToolAttributes.applyWeapon(meta, 10.0, 2.0);
        });

        return item;
    }


    // =========================
    // Dragon Pickaxe
    // =========================
    public static ItemStack dragonPickaxe() {
        ItemStack item = new ItemStack(Material.NETHERITE_PICKAXE);

        item.editMeta(meta -> {
            meta.displayName(Lang.getComponent("items.pickaxe.name"));
            meta.lore(Lang.getComponentList("items.pickaxe.lore"));
            // dragonPickaxe()
            applyDragonModel(meta, "dragon_pickaxe");

            meta.getPersistentDataContainer().set(
                    PDCKeys.DRAGON_TOOL,
                    PersistentDataType.BYTE,
                    (byte) 1
            );

            DragonToolAttributes.applyMiningEfficiency(meta, 50.0);
        });

        return item;
    }


    // =========================
    // Dragon Axe
    // =========================
    public static ItemStack dragonAxe() {
        ItemStack item = new ItemStack(Material.NETHERITE_AXE);

        item.editMeta(meta -> {
            meta.displayName(Lang.getComponent("items.axe.name"));
            meta.lore(Lang.getComponentList("items.axe.lore"));
            // dragonAxe()
            applyDragonModel(meta, "dragon_axe");

            meta.getPersistentDataContainer().set(
                    PDCKeys.DRAGON_TOOL,
                    PersistentDataType.BYTE,
                    (byte) 1
            );

            DragonToolAttributes.applyWeapon(meta, 15.0, 0.8);
            DragonToolAttributes.applyMiningEfficiency(meta, 30.0);
        });

        return item;
    }




    // =========================
    // Dragon Shovel
    // =========================
    public static ItemStack dragonShovel() {
        ItemStack item = new ItemStack(Material.NETHERITE_SHOVEL);

        item.editMeta(meta -> {
            meta.displayName(Lang.getComponent("items.shovel.name"));
            meta.lore(Lang.getComponentList("items.shovel.lore"));
            // dragonShovel()
            applyDragonModel(meta, "dragon_shovel");

            meta.getPersistentDataContainer().set(
                    PDCKeys.DRAGON_TOOL,
                    PersistentDataType.BYTE,
                    (byte) 1
            );

            DragonToolAttributes.applyMiningEfficiency(meta, 20.0);
        });

        return item;
    }



    // =========================
    // Dragon Hoe
    // =========================
    public static ItemStack dragonHoe() {
        ItemStack item = new ItemStack(Material.NETHERITE_HOE);

        item.editMeta(meta -> {
            meta.displayName(Lang.getComponent("items.hoe.name"));
            meta.lore(Lang.getComponentList("items.hoe.lore"));
            // dragonHoe()
            applyDragonModel(meta, "dragon_hoe");

            meta.getPersistentDataContainer().set(
                    PDCKeys.DRAGON_TOOL,
                    PersistentDataType.BYTE,
                    (byte) 1
            );

            DragonToolAttributes.applyMiningEfficiency(meta, 15.0);
        });

        return item;
    }




    // =========================
    // Dragon Wings (Elytra)
    // =========================
    public static ItemStack dragonWings() {
        ItemStack item = new ItemStack(Material.ELYTRA);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.displayName(Lang.getComponent("items.wings.name"));
        meta.lore(Lang.getComponentList("items.wings.lore"));
        //dragonwings()
        applyDragonModel(meta, "dragon_wings");
        applyDragonEquipment(meta, "dragon_wings", EquipmentSlot.CHEST);



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

        meta.displayName(Lang.getComponent("armor.helmet.name"));
        meta.lore(Lang.getComponentList("armor.helmet.lore"));
        // dragonHelmet()
        applyDragonModel(meta, "dragon_helmet");
        applyDragonEquipment(meta, "dragon_helmet", EquipmentSlot.HEAD);



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

        meta.displayName(Lang.getComponent("armor.chestplate.name"));
        meta.lore(Lang.getComponentList("armor.chestplate.lore"));
        // dragonChestplate()
        applyDragonModel(meta, "dragon_chestplate");
        applyDragonEquipment(meta, "dragon_chestplate", EquipmentSlot.CHEST);



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

        meta.displayName(Lang.getComponent("armor.leggings.name"));
        meta.lore(Lang.getComponentList("armor.leggings.lore"));
        // dragonLeggings()
        applyDragonModel(meta, "dragon_leggings");
        applyDragonEquipment(meta, "dragon_leggings", EquipmentSlot.LEGS);



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

        meta.displayName(Lang.getComponent("armor.boots.name"));
        meta.lore(Lang.getComponentList("armor.boots.lore"));
        // dragonBoots()
        applyDragonModel(meta, "dragon_boots");
        applyDragonEquipment(meta, "dragon_boots", EquipmentSlot.FEET);


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
        return item != null
                && item.getType() == Material.NETHERITE_HELMET
                && isDragonArmor(item);
    }

    public static boolean isDragonChestplate(ItemStack item) {
        return item != null
                && item.getType() == Material.NETHERITE_CHESTPLATE
                && isDragonArmor(item);
    }

    public static boolean isDragonLeggings(ItemStack item) {
        return item != null
                && item.getType() == Material.NETHERITE_LEGGINGS
                && isDragonArmor(item);
    }

    public static boolean isDragonBoots(ItemStack item) {
        return item != null
                && item.getType() == Material.NETHERITE_BOOTS
                && isDragonArmor(item);
    }



}
