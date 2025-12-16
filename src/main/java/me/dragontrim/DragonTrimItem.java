package me.dragontrim;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class DragonTrimItem {

    public static ItemStack create() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return item;

        meta.setDisplayName("§5§lDragon Trim");

        meta.setLore(List.of(
                "§7Ein uraltes Relikt voller Macht",
                "§8Nur Drachen würdig"
        ));

        //  Markierung: DAS ist ein Dragon Trim
        meta.getPersistentDataContainer().set(
                PDCKeys.DRAGON_TRIM,
                PersistentDataType.BYTE,
                (byte) 1
        );

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);
        return item;
    }
}
