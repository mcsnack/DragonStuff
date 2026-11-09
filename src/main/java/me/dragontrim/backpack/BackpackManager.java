package me.dragontrim.backpack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Öffnen, GUI, Speichern und Besitzer-Index für DragonBackPacks. */
public final class BackpackManager implements Listener {

    private final Plugin plugin;
    private final BackpackItems items;
    private final BackpackStorage storage;
    private final Map<UUID, Session> sessions = new HashMap<>();

    public BackpackManager(Plugin plugin, BackpackItems items, BackpackStorage storage) {
        this.plugin = plugin;
        this.items = items;
        this.storage = storage;
    }

    private record Session(UUID id, UUID viewer, ItemStack[] contents) {}

    private static final class Holder implements InventoryHolder {
        private final UUID id;
        private Inventory inventory;

        private Holder(UUID id) { this.id = id; }

        @Override
        public Inventory getInventory() { return inventory; }
    }

    public void open(Player player, ItemStack item) {
        Integer tier = items.tier(item);
        UUID id = items.id(item);
        if (tier != null && id != null) openById(player, id, tier);
    }

    /** Öffnet einen Rucksack anhand seiner persistenten ID, auch aus dem Admin-GUI. */
    public void openById(Player player, UUID id, int tier) {
        Session session = sessions.get(id);
        if (session != null) {
            player.sendMessage(Component.text("Dieser Rucksack ist bereits geöffnet.", NamedTextColor.RED));
            return;
        }

        ItemStack[] contents = storage.load(id, BackpackItems.slotsFor(tier));
        session = new Session(id, player.getUniqueId(), contents);
        sessions.put(id, session);

        Holder holder = new Holder(id);
        holder.inventory = Bukkit.createInventory(holder, contents.length,
                BackpackItems.title(BackpackItems.tierFor(contents.length)));
        holder.inventory.setContents(contents);
        player.openInventory(holder.inventory);
    }

    private void flush(Holder holder) {
        Session session = sessions.get(holder.id);
        if (session == null || holder.inventory == null) return;
        ItemStack[] current = holder.inventory.getContents();
        for (int i = 0; i < session.contents().length; i++) {
            session.contents()[i] = current[i] == null ? null : current[i].clone();
        }
    }

    private boolean forbidden(ItemStack item) {
        return item != null && !item.getType().isAir() && Tag.SHULKER_BOXES.isTagged(item.getType());
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (!player.isSneaking()) return;

        ItemStack mainHand = event.getMainHandItem();
        ItemStack offHand = event.getOffHandItem();
        ItemStack backpack = items.isBackpack(mainHand) ? mainHand
                : items.isBackpack(offHand) ? offHand : null;
        if (backpack == null) return;

        event.setCancelled(true);
        open(player, backpack);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND || !items.isBackpack(event.getItem())) return;
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_AIR
                || (action == Action.RIGHT_CLICK_BLOCK && event.getPlayer().isSneaking())) {
            event.setCancelled(true);
            open(event.getPlayer(), event.getItem());
        } else if (action == Action.RIGHT_CLICK_BLOCK) {
            event.setUseItemInHand(Event.Result.DENY);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (items.isBackpack(event.getItemInHand())) event.setCancelled(true);
    }

    @EventHandler
    public void onDispense(BlockDispenseEvent event) {
        if (items.isBackpack(event.getItem())) event.setCancelled(true);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory top = event.getView().getTopInventory();
        if (!(top.getHolder() instanceof Holder) || !(event.getWhoClicked() instanceof Player player)) return;

        int rawSlot = event.getRawSlot();
        if (rawSlot >= 0 && rawSlot < top.getSize()) {
            boolean blocked = forbidden(event.getCursor());
            if (event.getClick() == ClickType.NUMBER_KEY && event.getHotbarButton() >= 0) {
                blocked |= forbidden(player.getInventory().getItem(event.getHotbarButton()));
            }
            if (event.getClick() == ClickType.SWAP_OFFHAND) {
                blocked |= forbidden(player.getInventory().getItemInOffHand());
            }
            if (blocked) event.setCancelled(true);
        } else if (event.getClick().isShiftClick() && forbidden(event.getCurrentItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        Inventory top = event.getView().getTopInventory();
        if (!(top.getHolder() instanceof Holder) || !forbidden(event.getOldCursor())) return;
        for (int rawSlot : event.getRawSlots()) {
            if (rawSlot < top.getSize()) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        if (event.getInventory().getHolder() instanceof Holder holder) {
            flush(holder);
            Session session = sessions.remove(holder.id);
            if (session != null) storage.save(session.id(), session.contents());
            scan(player);
        } else if (event.getInventory().getType() == InventoryType.SMITHING && plugin.isEnabled()) {
            Bukkit.getScheduler().runTask(plugin, () -> scan(player));
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        scan(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        scan(event.getPlayer());
    }

    /** Registriert den zuletzt beobachteten Besitzer in Inventar und Endertruhe. */
    private void scan(Player player) {
        for (ItemStack[] contents : new ItemStack[][]{
                player.getInventory().getContents(), player.getEnderChest().getContents()
        }) {
            for (ItemStack item : contents) {
                Integer tier = items.tier(item);
                UUID id = items.id(item);
                if (tier != null && id != null) {
                    storage.registerOwner(id, player.getUniqueId(), player.getName(), tier);
                }
            }
        }
    }

    /** In onDisable() aufrufen, damit geöffnete Rucksäcke gespeichert werden. */
    public void closeAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getOpenInventory().getTopInventory().getHolder() instanceof Holder) {
                player.closeInventory();
            }
        }
        for (Session session : sessions.values()) {
            storage.save(session.id(), session.contents());
        }
        sessions.clear();
    }
}
