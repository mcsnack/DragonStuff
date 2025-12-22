package me.dragontrim;

import me.dragontrim.lang.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DragonCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cNur Spieler können diesen Befehl nutzen.");
            return true;
        }

        if (!player.hasPermission("dragontrim.admin")) {
            player.sendMessage("§cDazu hast du keine Berechtigung.");
            return true;
        }

        // ✅ HIER rein (nach Permission, vor args.length == 0)
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            DragonTrim.getInstance().reloadPlugin();
            player.sendMessage("§aDragonTrim wurde neu geladen.");
            return true;
        }

        // 🔽 ab hier dein bestehender Code
        if (args.length == 0) {
            player.sendMessage("§7Verwendung:");
            player.sendMessage("§5/dragon trim");
            player.sendMessage("§5/dragon sword");
            return true;
        }


        switch (args[0].toLowerCase()) {
            case "trim" -> {
                player.getInventory().addItem(DragonTrimItem.create());
                player.sendMessage("§5Du hast das §lDragon Trim§5 erhalten!");
            }

            case "sword" -> {
                player.getInventory().addItem(DragonItems.dragonSword());
                player.sendMessage(Lang.getComponent("messages.give-sword"));
            }

            case "pickaxe" -> {
                player.getInventory().addItem(DragonItems.dragonPickaxe());
                player.sendMessage(Lang.getComponent("messages.give-pickaxe"));
            }

            case "axe" -> {
                player.getInventory().addItem(DragonItems.dragonAxe());
                player.sendMessage(Lang.getComponent("messages.give-axe"));
            }

            case "shovel" -> {
                player.getInventory().addItem(DragonItems.dragonShovel());
                player.sendMessage(Lang.getComponent("messages.give-shovel"));
            }

            case "hoe" -> {
                player.getInventory().addItem(DragonItems.dragonHoe());
                player.sendMessage(Lang.getComponent("messages.give-hoe"));
            }

            case "wings" -> {
                player.getInventory().addItem(DragonItems.dragonWings());
                player.sendMessage(Lang.getComponent("messages.give-wings"));
            }

            case "helmet" -> {
                player.getInventory().addItem(DragonItems.dragonHelmet());
                player.sendMessage(Lang.getComponent("messages.give-helmet"));
            }

            case "chestplate" -> {
                player.getInventory().addItem(DragonItems.dragonChestplate());
                player.sendMessage(Lang.getComponent("messages.give-chestplate"));
            }

            case "leggings" -> {
                player.getInventory().addItem(DragonItems.dragonLeggings());
                player.sendMessage(Lang.getComponent("messages.give-leggings"));
            }

            case "boots" -> {
                player.getInventory().addItem(DragonItems.dragonBoots());
                player.sendMessage(Lang.getComponent("messages.give-boots"));
            }


            case "reload" -> {
                DragonTrim plugin = DragonTrim.getInstance();
                plugin.reloadConfig();
                Lang.reload(plugin);

                player.sendMessage(Lang.getComponent("command.reload"));
            }



            default -> {
                player.sendMessage("§cUnbekanntes Argument.");
                player.sendMessage("§7Verwendung: /dragon <trim|sword|wings[..]>");
            }

        }

        return true;
    }


}