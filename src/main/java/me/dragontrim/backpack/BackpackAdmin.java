package me.dragontrim.backpack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/** /backpack [Name] – nur für Admins (Permission dragonstuff.backpack.admin, Standard: OP). */
public final class BackpackAdmin implements CommandExecutor, TabCompleter, Listener {

    public static final String PERMISSION = "dragonstuff.backpack.admin";
    private static final int PAGE_SIZE = 45;

    private final Plugin plugin;
    private final BackpackManager manager;
    private final BackpackStorage storage;

    public BackpackAdmin(Plugin plugin, BackpackManager manager, BackpackStorage storage) {
        this.plugin = plugin;
        this.manager = manager;
        this.storage = storage;
    }

    private static final class ListHolder implements InventoryHolder {
        private final String filter;
        private final int page;
        private final List<BackpackStorage.Entry> entries;
        private Inventory inventory;

        private ListHolder(String filter, int page, List<BackpackStorage.Entry> entries) {
            this.filter = filter;
            this.page = page;
            this.entries = entries;
        }

        @Override
        public Inventory getInventory() { return inventory; }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Dieser Befehl kann nur im Spiel verwendet werden.");
            return true;
        }
        if (!player.hasPermission(PERMISSION)) {
            player.sendMessage(Component.text("Dafür hast du keine Rechte.", NamedTextColor.RED));
            return true;
        }
        openList(player, args.length > 0 ? args[0] : null, 0);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission(PERMISSION) || args.length != 1) return List.of();
        String prefix = args[0].toLowerCase(Locale.ROOT);
        return storage.all().stream()
                .map(BackpackStorage.Entry::ownerName)
                .distinct()
                .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(prefix))
                .sorted()
                .collect(Collectors.toList());
    }

    private void openList(Player player, String filter, int page) {
        List<BackpackStorage.Entry> entries = storage.all().stream()
                .filter(entry -> filter == null || entry.ownerName().equalsIgnoreCase(filter))
                .sorted(Comparator.comparing((BackpackStorage.Entry entry) ->
                                entry.ownerName().toLowerCase(Locale.ROOT))
                        .thenComparing(BackpackStorage.Entry::tier, Comparator.reverseOrder()))
                .toList();

        if (entries.isEmpty()) {
            player.sendMessage(Component.text("Keine Backpacks gefunden.", NamedTextColor.RED));
            return;
        }

        int pages = (int) Math.ceil(entries.size() / (double) PAGE_SIZE);
        int safePage = Math.max(0, Math.min(page, pages - 1));
        ListHolder holder = new ListHolder(filter, safePage, entries);
        String name = filter == null ? "Alle" : filter;
        holder.inventory = Bukkit.createInventory(holder, 54, Component.text(
                "Backpacks: " + name + " (" + entries.size() + ") " + (safePage + 1) + "/" + pages));

        for (int slot = 0; slot < PAGE_SIZE; slot++) {
            int index = safePage * PAGE_SIZE + slot;
            if (index >= entries.size()) break;
            BackpackStorage.Entry entry = entries.get(index);
            ItemStack item = new ItemStack(Material.PURPLE_SHULKER_BOX);
            item.editMeta(meta -> {
                meta.displayName(BackpackItems.title(entry.tier()));
                meta.lore(List.of(
                        line("Besitzer: " + entry.ownerName(), NamedTextColor.GRAY),
                        line("ID: " + entry.id().toString().substring(0, 8), NamedTextColor.DARK_GRAY),
                        line("Klick zum Öffnen", NamedTextColor.YELLOW)
                ));
            });
            holder.inventory.setItem(slot, item);
        }

        if (safePage > 0) holder.inventory.setItem(45, navigation("« Zurück"));
        if (safePage < pages - 1) holder.inventory.setItem(53, navigation("Weiter »"));
        player.openInventory(holder.inventory);
    }

    private static Component line(String text, NamedTextColor color) {
        return Component.text(text, color).decoration(TextDecoration.ITALIC, false);
    }

    private static ItemStack navigation(String name) {
        ItemStack item = new ItemStack(Material.ARROW);
        item.editMeta(meta -> meta.displayName(line(name, NamedTextColor.LIGHT_PURPLE)));
        return item;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof ListHolder holder)) return;
        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player player)
                || !player.hasPermission(PERMISSION)
                || event.getClickedInventory() != event.getView().getTopInventory()) return;

        int slot = event.getRawSlot();
        if (slot == 45 && holder.page > 0) {
            Bukkit.getScheduler().runTask(plugin, () -> openList(player, holder.filter, holder.page - 1));
        } else if (slot == 53
                && holder.page < Math.ceil(holder.entries.size() / (double) PAGE_SIZE) - 1) {
            Bukkit.getScheduler().runTask(plugin, () -> openList(player, holder.filter, holder.page + 1));
        } else if (slot >= 0 && slot < PAGE_SIZE
                && holder.page * PAGE_SIZE + slot < holder.entries.size()) {
            BackpackStorage.Entry entry = holder.entries.get(holder.page * PAGE_SIZE + slot);
            Bukkit.getScheduler().runTask(plugin,
                    () -> manager.openById(player, entry.id(), entry.tier()));
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getView().getTopInventory().getHolder() instanceof ListHolder) {
            event.setCancelled(true);
        }
    }
}
