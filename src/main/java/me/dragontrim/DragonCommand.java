package me.dragontrim;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DragonCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Nur Spieler können diesen Befehl nutzen.");
            return true;
        }

        if (!player.hasPermission("dragontrim.admin")) {
            player.sendMessage("§cDazu hast du keine Berechtigung.");
            return true;
        }

        // 🔹 Schritt 2: Argumente prüfen
        if (args.length == 0) {
            player.sendMessage("§7Verwendung:");
            player.sendMessage("§5/dragon trim");
            player.sendMessage("§5/dragon sword");
            return true;
        }

        // 🔹 Schritt 3: Subcommands
        switch (args[0].toLowerCase()) {

            case "trim" -> {
                player.getInventory().addItem(DragonTrimItem.create());
                player.sendMessage("§5Du hast das §lDragon Trim§5 erhalten!");
            }

            case "sword" -> {
                player.getInventory().addItem(DragonItems.dragonSword());
                player.sendMessage("§5Du hast das §lDragon Sword§5 erhalten!");
            }

            case "pickaxe" -> {
                player.getInventory().addItem(DragonItems.dragonPickaxe());
                player.sendMessage("§5Du hast die §dDragon Pickaxe§5 erhalten!");
            }

            case "axe" -> {
                player.getInventory().addItem(DragonItems.dragonAxe());
                player.sendMessage("§5Du hast die §dDragon Axe§5 erhalten!");
            }

            case "shovel" -> {
                player.getInventory().addItem(DragonItems.dragonShovel());
                player.sendMessage("§5Du hast die §dDragon Shovel§5 erhalten!");
            }

            case "hoe" -> {
                player.getInventory().addItem(DragonItems.dragonHoe());
                player.sendMessage("§5Du hast die §dDragon Hoe§5 erhalten!");
            }

            case "wings" -> {
                player.getInventory().addItem(DragonItems.dragonWings());
                player.sendMessage("§5Du hast die §dDragon Wings§5 erhalten!");
            }

            case "helmet" -> {
                player.getInventory().addItem(DragonItems.dragonHelmet());
                player.sendMessage("§5Du hast den §dDragon Helmet§5 erhalten!");
            }

            case "chestplate" -> {
                player.getInventory().addItem(DragonItems.dragonChestplate());
                player.sendMessage("§5Du hast die §dDragon Chestplate§5 erhalten!");
            }

            case "leggings" -> {
                player.getInventory().addItem(DragonItems.dragonLeggings());
                player.sendMessage("§5Du hast die §dDragon Leggings§5 erhalten!");
            }

            case "boots" -> {
                player.getInventory().addItem(DragonItems.dragonBoots());
                player.sendMessage("§5Du hast die §dDragon Boots§5 erhalten!");
            }


            default -> {
                player.sendMessage("§cUnbekanntes Argument.");
                player.sendMessage("§7Verwendung: /dragon <trim|sword|wings>");
            }

        }

        return true;
    }
}