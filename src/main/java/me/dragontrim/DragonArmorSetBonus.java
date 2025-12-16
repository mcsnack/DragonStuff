package me.dragontrim;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DragonArmorSetBonus implements Listener {

    private final JavaPlugin plugin;

    public DragonArmorSetBonus(JavaPlugin plugin) {
        this.plugin = plugin;
        startTask();
    }

    private void startTask() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {

                if (hasFullDragonSet(player)) {
                    // ✅ Effekt IMMER aktiv halten
                    player.addPotionEffect(
                            new PotionEffect(
                                    PotionEffectType.RESISTANCE,
                                    40, // 2 Sekunden
                                    0,  // Resistance I
                                    true,
                                    false
                            )
                    );
                } else {
                    player.removePotionEffect(PotionEffectType.RESISTANCE);
                }
            }
        }, 0L, 20L); // jede Sekunde
    }

    private boolean hasFullDragonSet(Player player) {
        return DragonItems.isDragonHelmet(player.getInventory().getHelmet())
                && DragonItems.isDragonChestplate(player.getInventory().getChestplate())
                && DragonItems.isDragonLeggings(player.getInventory().getLeggings())
                && DragonItems.isDragonBoots(player.getInventory().getBoots());
    }
}
