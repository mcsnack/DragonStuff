package me.dragontrim;

import org.bukkit.plugin.java.JavaPlugin;


public class DragonTrim extends JavaPlugin {

    @Override
    public void onEnable() {
        // config.yml laden / erstellen
        saveDefaultConfig();

        // PersistentDataContainer Keys initialisieren
        PDCKeys.init(this);


        // Amor Stats
        DragonArmorStats.init(this);

        //Set Bonus
        new DragonArmorSetBonus(this);

        // Rezepte Einbinden
        DragonSmithingRecipes.register(this);

        getServer().getPluginManager().registerEvents(
                new DragonSmithingListener(),
                this
        );


        getLogger().info("DragonTrim enabled!");

        // Command registrieren
        var command = getCommand("dragon");
        if (command != null) {
            command.setExecutor(new DragonCommand());
            command.setTabCompleter(new DragonTabCompleter());
        } else {
            getLogger().severe("Command /dragon not found in plugin.yml!");
        }


        // Listener registrieren
        getServer().getPluginManager().registerEvents(
                new DragonSmithingListener(),
                this
        );

        getServer().getPluginManager().registerEvents(
                new DragonDurabilityListener(),
                this
        );

    }

    @Override
    public void onDisable() {
        getLogger().info("DragonTrim disabled!");
    }
}
