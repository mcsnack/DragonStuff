package me.dragontrim;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DragonTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String alias,
            @NotNull String[] args
    ) {
        // /dragon <TAB>
        if (args.length == 1) {
            return List.of(
                    "trim",
                    "sword",
                    "pickaxe",
                    "axe",
                    "hoe",
                    "shovel",
                    "helmet",
                    "chestplate",
                    "leggings",
                    "boots",
                    "wings"
            );
        }

        return List.of();
    }
}
