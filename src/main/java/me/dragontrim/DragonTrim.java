package me.dragontrim;

import me.dragontrim.backpack.BackpackAdmin;
import me.dragontrim.backpack.BackpackDyeListener;
import me.dragontrim.backpack.BackpackItems;
import me.dragontrim.backpack.BackpackManager;
import me.dragontrim.backpack.BackpackSmithing;
import me.dragontrim.backpack.BackpackStorage;
import me.dragontrim.lang.Lang;
import org.bukkit.plugin.java.JavaPlugin;

public class DragonTrim extends JavaPlugin {

    private static DragonTrim instance;
    private BackpackManager backpackManager;
    private BackpackSmithing backpackSmithing;

    public static DragonTrim getInstance() {
        return instance;
    }

    public void reloadPlugin() {
        reloadConfig();
        Lang.load(this);
        getLogger().info("DragonTrim reloaded (config + lang).");
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        Lang.load(this);

        // PersistentDataContainer Keys initialisieren
        PDCKeys.init(this);

        // Armor Stats und Set-Bonus
        DragonArmorStats.init(this);
        new DragonArmorSetBonus(this);

        // Bestehende Dragon-Trim-Rezepte
        DragonSmithingRecipes.register(this);

        // Bestehende Listener
        getServer().getPluginManager().registerEvents(new DragonSmithingListener(), this);
        getServer().getPluginManager().registerEvents(new DragonDurabilityListener(), this);

        // DragonBackPack-System
        BackpackItems backpackItems = new BackpackItems(this);
        BackpackStorage backpackStorage = new BackpackStorage(this);
        backpackManager = new BackpackManager(this, backpackItems, backpackStorage);
        backpackSmithing = new BackpackSmithing(this, backpackItems, backpackStorage);
        BackpackAdmin backpackAdmin = new BackpackAdmin(this, backpackManager, backpackStorage);

        backpackSmithing.register(DragonTrimItem::create);

        getServer().getPluginManager().registerEvents(backpackManager, this);
        getServer().getPluginManager().registerEvents(backpackSmithing, this);
        getServer().getPluginManager().registerEvents(backpackAdmin, this);
        getServer().getPluginManager().registerEvents(
                new BackpackDyeListener(this, backpackItems, backpackStorage), this);

        var backpackCommand = getCommand("backpack");
        if (backpackCommand != null) {
            backpackCommand.setExecutor(backpackAdmin);
            backpackCommand.setTabCompleter(backpackAdmin);
        } else {
            getLogger().severe("Command /backpack fehlt in plugin.yml!");
        }

        // Bestehender /dragon-Command
        var command = getCommand("dragon");
        if (command != null) {
            command.setExecutor(new DragonCommand());
            command.setTabCompleter(new DragonTabCompleter());
        } else {
            getLogger().severe("Command /dragon fehlt in plugin.yml!");
        }

        getLogger().info("DragonTrim enabled!");
    }

    @Override
    public void onDisable() {
        if (backpackManager != null) {
            backpackManager.closeAll();
        }
        if (backpackSmithing != null) {
            backpackSmithing.unregister();
        }
        getLogger().info("DragonTrim disabled!");
    }
}
