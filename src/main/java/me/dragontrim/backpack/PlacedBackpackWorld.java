package me.dragontrim.backpack;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.World;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Ersetzt platzierte DragonBackPack-Shulkerblöcke durch eigenständige ItemDisplay-Modelle.
 *
 * Der Shulkerblock dient nur als Platzierungsanker und wird nach dem erfolgreichen
 * Spawnen des ItemDisplay-/Interaction-Paares entfernt. Die Entities tragen ihre
 * Daten in ihrem PDC und bleiben dadurch beim Chunk-Unload/Serverneustart erhalten.
 */
public final class PlacedBackpackWorld implements Listener {

    private static final String ROLE_DISPLAY = "display";
    private static final String ROLE_HITBOX = "hitbox";

    private final Plugin plugin;
    private final BackpackItems items;
    private final BackpackStorage storage;
    private final BackpackManager manager;

    // Diese zwei Schlüssel entsprechen den Schlüsseln aus BackpackManager für ältere platzierte Rucksäcke.
    private final NamespacedKey blockIdKey;
    private final NamespacedKey blockTierKey;

    private final NamespacedKey entityMarkerKey;
    private final NamespacedKey entityRoleKey;
    private final NamespacedKey entityIdKey;
    private final NamespacedKey entityTierKey;
    private final NamespacedKey entityMaterialKey;
    private final NamespacedKey entityPairKey;

    private final Set<String> scheduledBlocks = ConcurrentHashMap.newKeySet();

    public PlacedBackpackWorld(
            Plugin plugin,
            BackpackItems items,
            BackpackStorage storage,
            BackpackManager manager
    ) {
        this.plugin = plugin;
        this.items = items;
        this.storage = storage;
        this.manager = manager;

        this.blockIdKey = new NamespacedKey(plugin, "placed_backpack_id");
        this.blockTierKey = new NamespacedKey(plugin, "placed_backpack_tier");

        this.entityMarkerKey = new NamespacedKey(plugin, "placed_backpack_entity");
        this.entityRoleKey = new NamespacedKey(plugin, "placed_backpack_role");
        this.entityIdKey = new NamespacedKey(plugin, "placed_backpack_id");
        this.entityTierKey = new NamespacedKey(plugin, "placed_backpack_tier");
        this.entityMaterialKey = new NamespacedKey(plugin, "placed_backpack_material");
        this.entityPairKey = new NamespacedKey(plugin, "placed_backpack_pair");
    }

    /**
     * BackpackManager speichert die ID/Tier-Daten am Shulker-TileState.
     * Erst im nächsten Tick ersetzen wir den Block, wenn alle Place-Handler fertig sind.
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBackpackPlace(BlockPlaceEvent event) {
        ItemStack placedItem = event.getItemInHand();
        if (!items.isBackpack(placedItem)) return;

        UUID id = items.id(placedItem);
        Integer tier = items.tier(placedItem);
        if (id == null || tier == null) return;

        Block block = event.getBlockPlaced();
        Material material = block.getType();
        float facingYaw = normalizeYaw(event.getPlayer().getLocation().getYaw() + 180.0F);

        scheduleConversion(block.getLocation(), material, id, tier, facingYaw);
    }

    /**
     * Migriert ältere platzierte DragonBackPacks, die noch als Shulker-TileState
     * mit placed_backpack_id / placed_backpack_tier in der Welt gespeichert sind.
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onChunkLoad(ChunkLoadEvent event) {
        // Bereits platzierte Rucksack-Displays beim erneuten Laden auf die stehende Darstellung umstellen.
        for (Entity entity : event.getChunk().getEntities()) {
            if (entity instanceof ItemDisplay display && isBackpackEntity(display, ROLE_DISPLAY)) {
                configureDisplay(display);
            }
        }

        for (BlockState state : event.getChunk().getTileEntities()) {
            if (!(state instanceof TileState tile)) continue;
            if (!Tag.SHULKER_BOXES.isTagged(state.getType())) continue;

            PersistentDataContainer pdc = tile.getPersistentDataContainer();
            UUID id = parseUuid(pdc.get(blockIdKey, PersistentDataType.STRING));
            Integer tier = pdc.get(blockTierKey, PersistentDataType.INTEGER);
            if (id == null || tier == null || tier < 1 || tier > BackpackItems.MAX_TIER) continue;

            Block block = state.getBlock();
            scheduleConversion(block.getLocation(), block.getType(), id, tier, 0.0F);
        }
    }

    private void scheduleConversion(
            Location blockLocation,
            Material expectedMaterial,
            UUID id,
            int tier,
            float yaw
    ) {
        Location anchor = blockLocation.getBlock().getLocation();
        String key = locationKey(anchor);
        if (!scheduledBlocks.add(key)) return;

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTask(plugin, () -> {
            try {
                World world = anchor.getWorld();
                if (world == null) return;
                if (!world.isChunkLoaded(anchor.getBlockX() >> 4, anchor.getBlockZ() >> 4)) return;

                Block block = world.getBlockAt(anchor.getBlockX(), anchor.getBlockY(), anchor.getBlockZ());
                if (block.getType() != expectedMaterial || !Tag.SHULKER_BOXES.isTagged(block.getType())) return;
                if (!(block.getState() instanceof TileState tile)) return;

                String storedId = tile.getPersistentDataContainer().get(blockIdKey, PersistentDataType.STRING);
                Integer storedTier = tile.getPersistentDataContainer().get(blockTierKey, PersistentDataType.INTEGER);
                if (!id.toString().equals(storedId) || storedTier == null || storedTier != tier) return;

                if (spawnBackpackPair(block.getLocation(), block.getType(), id, tier, yaw)) {
                    // Nur den Block entfernen, wenn beide Entities erfolgreich erzeugt wurden.
                    // Es gibt dadurch keinen Vanilla-Shulker-Drop und keine doppelte Backpack-ID.
                    block.setType(Material.AIR, false);
                    plugin.getLogger().info("Placed DragonBackPack converted to 3D display: " + id);
                }
            } catch (RuntimeException exception) {
                plugin.getLogger().severe("Could not convert placed DragonBackPack " + id + ": " + exception.getMessage());
            } finally {
                scheduledBlocks.remove(key);
            }
        });
    }

    private boolean spawnBackpackPair(Location blockLocation, Material material, UUID id, int tier, float yaw) {
        World world = blockLocation.getWorld();
        if (world == null || !Tag.SHULKER_BOXES.isTagged(material)) return false;

        Location displayLocation = blockLocation.clone().add(0.5, 1.0, 0.5);
        Location hitboxLocation = blockLocation.clone().add(0.5, 1.0, 0.5);

        ItemStack renderItem = items.create(material, tier, id,
                storage.load(id, BackpackItems.slotsFor(tier)));

        ItemDisplay display = null;
        Interaction hitbox = null;
        try {
            display = world.spawn(displayLocation, ItemDisplay.class, entity -> {
                entity.setItemStack(renderItem);
                configureDisplay(entity);
                entity.setRotation(yaw, 0.0F);
                markEntity(entity, ROLE_DISPLAY, material, id, tier);
            });

            hitbox = world.spawn(hitboxLocation, Interaction.class, entity -> {
                entity.setInteractionWidth(0.95F);
                entity.setInteractionHeight(1.05F);
                entity.setResponsive(true);
                entity.setPersistent(true);
                entity.setGravity(false);
                entity.setSilent(true);
                markEntity(entity, ROLE_HITBOX, material, id, tier);
            });

            display.getPersistentDataContainer().set(
                    entityPairKey, PersistentDataType.STRING, hitbox.getUniqueId().toString());
            hitbox.getPersistentDataContainer().set(
                    entityPairKey, PersistentDataType.STRING, display.getUniqueId().toString());
            return true;
        } catch (RuntimeException exception) {
            if (hitbox != null) hitbox.remove();
            if (display != null) display.remove();
            plugin.getLogger().severe("Could not spawn the DragonBackPack display entities: " + exception.getMessage());
            return false;
        }
    }

    /** Konfiguriert das ItemDisplay als aufrechten Welt-Rucksack statt als flaches Item-Frame-Modell. */
    private void configureDisplay(ItemDisplay display) {
        display.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.NONE);
        display.setTransformation(new Transformation(
                new Vector3f(0.0F, 0.0F, 0.0F),
                new Quaternionf(),
                new Vector3f(2.0F, 2.0F, 2.0F),
                new Quaternionf()
        ));
        display.setPersistent(true);
        display.setGravity(false);
        display.setInvulnerable(true);
        display.setSilent(true);
    }

    private void markEntity(Entity entity, String role, Material material, UUID id, int tier) {
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        pdc.set(entityMarkerKey, PersistentDataType.BYTE, (byte) 1);
        pdc.set(entityRoleKey, PersistentDataType.STRING, role);
        pdc.set(entityIdKey, PersistentDataType.STRING, id.toString());
        pdc.set(entityTierKey, PersistentDataType.INTEGER, tier);
        pdc.set(entityMaterialKey, PersistentDataType.STRING, material.name());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBackpackRightClick(PlayerInteractEntityEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;

        Entity clicked = event.getRightClicked();
        if (!isBackpackEntity(clicked, ROLE_HITBOX)) return;

        event.setCancelled(true);
        UUID id = readId(clicked);
        Integer tier = readTier(clicked);
        if (id != null && tier != null) {
            manager.openById(event.getPlayer(), id, tier);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBackpackAttack(PrePlayerAttackEntityEvent event) {
        Entity attacked = event.getAttacked();
        if (!isBackpackEntity(attacked, ROLE_HITBOX)) return;
        // Andere Plugins dürfen den Abbau weiterhin ausdrücklich blockieren.
        if (event.isCancelled() && event.willAttack()) return;

        // Interaction-Entities melden Angriffe teils bereits als abgebrochen,
        // weil sie keinen normalen Schaden nehmen. Wir verwenden den Angriff hier als Abbauaktion.
        event.setCancelled(true);
        Player player = event.getPlayer();

        GameMode gameMode = player.getGameMode();
        if (gameMode != GameMode.SURVIVAL && gameMode != GameMode.CREATIVE) return;

        UUID id = readId(attacked);
        Integer tier = readTier(attacked);
        Material material = readMaterial(attacked);
        if (id == null || tier == null || material == null) {
            plugin.getLogger().warning("Placed DragonBackPack entity has incomplete persistent data; it was not removed.");
            return;
        }

        Location dropLocation = attacked.getLocation().clone();
        if (gameMode != GameMode.CREATIVE) {
            ItemStack backpackItem = items.create(material, tier, id,
                    storage.load(id, BackpackItems.slotsFor(tier)));
            var leftovers = player.getInventory().addItem(backpackItem);
            leftovers.values().forEach(item -> dropLocation.getWorld().dropItemNaturally(dropLocation, item));
        }

        removeEntityPair(attacked);
    }

    private boolean isBackpackEntity(Entity entity, String expectedRole) {
        if (entity == null) return false;
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        Byte marker = pdc.get(entityMarkerKey, PersistentDataType.BYTE);
        String role = pdc.get(entityRoleKey, PersistentDataType.STRING);
        return marker != null && marker == (byte) 1 && expectedRole.equals(role);
    }

    private UUID readId(Entity entity) {
        return parseUuid(entity.getPersistentDataContainer().get(entityIdKey, PersistentDataType.STRING));
    }

    private Integer readTier(Entity entity) {
        Integer tier = entity.getPersistentDataContainer().get(entityTierKey, PersistentDataType.INTEGER);
        return tier != null && tier >= 1 && tier <= BackpackItems.MAX_TIER ? tier : null;
    }

    private Material readMaterial(Entity entity) {
        String raw = entity.getPersistentDataContainer().get(entityMaterialKey, PersistentDataType.STRING);
        Material material = raw == null ? null : Material.matchMaterial(raw);
        return material != null && Tag.SHULKER_BOXES.isTagged(material) ? material : null;
    }

    private void removeEntityPair(Entity entity) {
        String pairRaw = entity.getPersistentDataContainer().get(entityPairKey, PersistentDataType.STRING);
        UUID pairId = parseUuid(pairRaw);
        if (pairId != null) {
            Entity paired = entity.getWorld().getEntity(pairId);
            if (paired != null) paired.remove();
        }
        entity.remove();
    }

    private UUID parseUuid(String raw) {
        if (raw == null || raw.isBlank()) return null;
        try {
            return UUID.fromString(raw);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    private float normalizeYaw(float yaw) {
        float normalized = yaw % 360.0F;
        return normalized < 0.0F ? normalized + 360.0F : normalized;
    }

    private String locationKey(Location location) {
        return location.getWorld().getUID() + ":"
                + location.getBlockX() + ":"
                + location.getBlockY() + ":"
                + location.getBlockZ();
    }
}
