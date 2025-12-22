package me.dragontrim;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DragonArmorSetBonus {

    private final JavaPlugin plugin;

    public DragonArmorSetBonus(JavaPlugin plugin) {
        this.plugin = plugin;
        startTask();
    }

    private void startTask() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {

            if (!plugin.getConfig().getBoolean("setbonus.enabled", true)) {
                return;
            }

            PotionEffectType effectType =
                    PotionEffectType.getByName(
                            plugin.getConfig()
                                    .getString("setbonus.effect", "RESISTANCE")
                                    .toUpperCase()
                    );

            if (effectType == null) {
                return; // ungültiger Effektname in der Config
            }


            int amplifier = plugin.getConfig().getInt("setbonus.amplifier", 0);
            boolean ambient = plugin.getConfig().getBoolean("setbonus.ambient", true);
            boolean particles = plugin.getConfig().getBoolean("setbonus.particles", false);

            for (Player player : Bukkit.getOnlinePlayers()) {

                if (hasFullDragonSet(player)) {
                    player.addPotionEffect(
                            new PotionEffect(effectType, 40, amplifier, ambient, particles)
                    );
                } else {
                    player.removePotionEffect(effectType);
                }
            }

        }, 0L, 20L); // jede Sekunde
    }

    private boolean hasFullDragonSet(Player player) {
        return isDragon(player.getInventory().getHelmet())
                && isDragon(player.getInventory().getChestplate())
                && isDragon(player.getInventory().getLeggings())
                && isDragon(player.getInventory().getBoots());
    }

    private boolean isDragon(ItemStack item) {
        return item != null && DragonItems.isDragonArmor(item);
    }
}
