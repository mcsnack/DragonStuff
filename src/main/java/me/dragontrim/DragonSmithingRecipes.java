package me.dragontrim;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingTransformRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class DragonSmithingRecipes {

    public static void register(JavaPlugin plugin) {

        // =========================
        // Dragon Sword
        // =========================
        ItemStack result = DragonItems.dragonSword();

        NamespacedKey key = new NamespacedKey(plugin, "dragon_sword_smithing");

        SmithingTransformRecipe recipe = new SmithingTransformRecipe(
                key,
                result,
                new RecipeChoice.ExactChoice(DragonTrimItem.create()), // Dragon Trim (PDC!)
                new RecipeChoice.MaterialChoice(Material.NETHERITE_SWORD),
                new RecipeChoice.MaterialChoice(Material.DIAMOND)
        );

        Bukkit.addRecipe(recipe);

        // =========================
        // Dragon Chestplate
        // =========================
        ItemStack chestplateResult = DragonItems.dragonChestplate();
        NamespacedKey chestKey = new NamespacedKey(plugin, "dragon_chestplate_smithing");

        SmithingTransformRecipe chestRecipe = new SmithingTransformRecipe(
                chestKey,
                chestplateResult,
                new RecipeChoice.ExactChoice(DragonTrimItem.create()),
                new RecipeChoice.MaterialChoice(Material.NETHERITE_CHESTPLATE),
                new RecipeChoice.MaterialChoice(Material.DIAMOND)
        );

        Bukkit.addRecipe(chestRecipe);

        // =========================
        // Dragon Leggings
        // =========================
        ItemStack leggingsResult = DragonItems.dragonLeggings();
        NamespacedKey leggingsKey = new NamespacedKey(plugin, "dragon_leggings_smithing");

        SmithingTransformRecipe leggingsRecipe = new SmithingTransformRecipe(
                leggingsKey,
                leggingsResult,

                // Template: Dragon Trim (PDC Nether Star)
                new RecipeChoice.ExactChoice(DragonTrimItem.create()),

                // Base: Netherite Leggings
                new RecipeChoice.MaterialChoice(Material.NETHERITE_LEGGINGS),

                // Addition: Diamond
                new RecipeChoice.MaterialChoice(Material.DIAMOND)
        );

        Bukkit.addRecipe(leggingsRecipe);


        // =========================
        // Dragon Helmet
        // =========================
        ItemStack helmetResult = DragonItems.dragonHelmet();
        NamespacedKey helmetKey = new NamespacedKey(plugin, "dragon_helmet_smithing");

        SmithingTransformRecipe helmetRecipe = new SmithingTransformRecipe(
                helmetKey,
                helmetResult,
                new RecipeChoice.ExactChoice(DragonTrimItem.create()), // Dragon Trim (Nether Star mit PDC)
                new RecipeChoice.MaterialChoice(Material.NETHERITE_HELMET),
                new RecipeChoice.MaterialChoice(Material.DIAMOND)
        );

        Bukkit.addRecipe(helmetRecipe);

        // =========================
        // Dragon Boots
        // =========================
        ItemStack bootsResult = DragonItems.dragonBoots();
        NamespacedKey bootsKey = new NamespacedKey(plugin, "dragon_boots_smithing");

        SmithingTransformRecipe bootsRecipe = new SmithingTransformRecipe(
                bootsKey,
                bootsResult,

                // Template: Dragon Trim (PDC Nether Star)
                new RecipeChoice.ExactChoice(DragonTrimItem.create()),

                // Base: Netherite Boots
                new RecipeChoice.MaterialChoice(Material.NETHERITE_BOOTS),

                // Addition: Diamond
                new RecipeChoice.MaterialChoice(Material.DIAMOND)
        );

        Bukkit.addRecipe(bootsRecipe);

        // =========================
        // Dragon Pickaxe
        // =========================
        ItemStack pickaxeResult = DragonItems.dragonPickaxe();
        NamespacedKey pickaxeKey = new NamespacedKey(plugin, "dragon_pickaxe_smithing");

        SmithingTransformRecipe pickaxeRecipe = new SmithingTransformRecipe(
                pickaxeKey,
                pickaxeResult,

                // Template: Dragon Trim (PDC Nether Star)
                new RecipeChoice.ExactChoice(DragonTrimItem.create()),

                // Base: Netherite Pickaxe
                new RecipeChoice.MaterialChoice(Material.NETHERITE_PICKAXE),

                // Addition: Diamond
                new RecipeChoice.MaterialChoice(Material.DIAMOND)
        );

        Bukkit.addRecipe(pickaxeRecipe);

        // =========================
        // Dragon Axe
        // =========================
        ItemStack axeResult = DragonItems.dragonAxe();
        NamespacedKey axeKey = new NamespacedKey(plugin, "dragon_axe_smithing");

        SmithingTransformRecipe axeRecipe = new SmithingTransformRecipe(
                axeKey,
                axeResult,

                new RecipeChoice.ExactChoice(DragonTrimItem.create()),
                new RecipeChoice.MaterialChoice(Material.NETHERITE_AXE),
                new RecipeChoice.MaterialChoice(Material.DIAMOND)
        );

        Bukkit.addRecipe(axeRecipe);

        // =========================
        // Dragon Shovel
        // =========================
        ItemStack shovelResult = DragonItems.dragonShovel();
        NamespacedKey shovelKey = new NamespacedKey(plugin, "dragon_shovel_smithing");

        SmithingTransformRecipe shovelRecipe = new SmithingTransformRecipe(
                shovelKey,
                shovelResult,

                new RecipeChoice.ExactChoice(DragonTrimItem.create()),
                new RecipeChoice.MaterialChoice(Material.NETHERITE_SHOVEL),
                new RecipeChoice.MaterialChoice(Material.DIAMOND)
        );

        Bukkit.addRecipe(shovelRecipe);


        // =========================
        // Dragon Hoe
        // =========================
        ItemStack hoeResult = DragonItems.dragonHoe();
        NamespacedKey hoeKey = new NamespacedKey(plugin, "dragon_hoe_smithing");

        SmithingTransformRecipe hoeRecipe = new SmithingTransformRecipe(
                hoeKey,
                hoeResult,

                new RecipeChoice.ExactChoice(DragonTrimItem.create()),
                new RecipeChoice.MaterialChoice(Material.NETHERITE_HOE),
                new RecipeChoice.MaterialChoice(Material.DIAMOND)
        );

        Bukkit.addRecipe(hoeRecipe);




    }



}
