package me.dragontrim;

import me.dragontrim.lang.Lang;
import org.bukkit.plugin.java.JavaPlugin;


public class DragonTrim extends JavaPlugin {

    private static DragonTrim instance;

    public static DragonTrim getInstance() {
        return instance;
    }

    public void reloadPlugin() {
        reloadConfig();  //Config Neuladen
        Lang.load(this);    //sprache neu Laden
        getLogger().info("DragonTrim reloaded (config + lang).");
    }


    @Override
    public void onEnable() {
        instance = this;

        // config.yml laden / erstellen
        saveDefaultConfig();

        // Lang
        Lang.load(this);


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
