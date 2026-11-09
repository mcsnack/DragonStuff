package me.dragontrim.backpack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
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
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Öffnen, GUI, Platzieren, Abbauen und Besitzer-Index für DragonBackPacks. */
public final class BackpackManager implements Listener {

    private final Plugin plugin;
    private final BackpackItems items;
    private final BackpackStorage storage;
    private final NamespacedKey blockIdKey;
    private final NamespacedKey blockTierKey;
    private final Map<UUID, Session> sessions = new HashMap<>();

    public BackpackManager(Plugin plugin, BackpackItems items, BackpackStorage storage) {
        this.plugin = plugin;
        this.items = items;
        this.storage = storage;
        this.blockIdKey = new NamespacedKey(plugin, "placed_backpack_id");
        this.blockTierKey = new NamespacedKey(plugin, "placed_backpack_tier");
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

    private UUID placedId(Block block) {
        if (block == null || !Tag.SHULKER_BOXES.isTagged(block.getType())) return null;
        if (!(block.getState() instanceof TileState tile)) return null;
        String raw = tile.getPersistentDataContainer().get(blockIdKey, PersistentDataType.STRING);
        try {
            return raw == null ? null : UUID.fromString(raw);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    private int placedTier(Block block) {
        if (!(block.getState() instanceof TileState tile)) return 1;
        Integer tier = tile.getPersistentDataContainer().get(blockTierKey, PersistentDataType.INTEGER);
        return tier == null ? 1 : Math.max(1, Math.min(BackpackItems.MAX_TIER, tier));
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
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getAction() != Action.RIGHT_CLICK_AIR
                && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        // Ein bereits platzierter DragonBackPack wird über seine Block-PDC erkannt.
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null) {
            UUID placedId = placedId(event.getClickedBlock());
            if (placedId != null) {
                event.setCancelled(true);
                openById(event.getPlayer(), placedId, placedTier(event.getClickedBlock()));
                return;
            }
        }

        // In der Hand: Rechtsklick in die Luft öffnet; Rechtsklick auf einen Block platziert normal.
        if (items.isBackpack(event.getItem()) && event.getAction() == Action.RIGHT_CLICK_AIR) {
            event.setCancelled(true);
            open(event.getPlayer(), event.getItem());
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        ItemStack placed = event.getItemInHand();
        if (!items.isBackpack(placed)) return;

        UUID id = items.id(placed);
        Integer tier = items.tier(placed);
        if (id == null || tier == null || !(event.getBlockPlaced().getState() instanceof TileState tile)) {
            event.setCancelled(true);
            return;
        }

        PersistentDataContainer pdc = tile.getPersistentDataContainer();
        pdc.set(blockIdKey, PersistentDataType.STRING, id.toString());
        pdc.set(blockTierKey, PersistentDataType.INTEGER, tier);
        tile.update(true, false);
        storage.registerOwner(id, event.getPlayer().getUniqueId(), event.getPlayer().getName(), tier);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        UUID id = placedId(block);
        if (id == null) return;

        int tier = placedTier(block);
        ItemStack[] contents = storage.load(id, BackpackItems.slotsFor(tier));
        ItemStack drop = items.create(block.getType(), tier, id, contents);

        // Kein normaler Shulker-Drop: Er würde die Backpack-ID verlieren bzw. duplizieren.
        event.setDropItems(false);
        block.getWorld().dropItemNaturally(block.getLocation().add(0.5, 0.5, 0.5), drop);
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
            if (session != null) {
                storage.save(session.id(), session.contents());
                refreshHeldBackpacks(player, session.id(), session.contents());
            }
            scan(player);
        } else if (event.getInventory().getType() == InventoryType.SMITHING && plugin.isEnabled()) {
            Bukkit.getScheduler().runTask(plugin, () -> scan(player));
        }
    }

    /** Aktualisiert den Tooltip eines getragenen Rucksacks nach dem Schließen des Inventars. */
    private void refreshHeldBackpacks(Player player, UUID id, ItemStack[] contents) {
        for (int slot = 0; slot < player.getInventory().getSize(); slot++) {
            ItemStack current = player.getInventory().getItem(slot);
            if (!id.equals(items.id(current))) continue;
            Integer tier = items.tier(current);
            if (tier != null) player.getInventory().setItem(slot,
                    items.create(current.getType(), tier, id, contents));
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
