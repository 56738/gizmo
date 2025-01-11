package me.m56738.gizmo.bukkit.display.v1_19_4;

import me.m56738.gizmo.AbstractCubeGizmo;
import me.m56738.gizmo.api.color.GizmoColor;
import me.m56738.gizmo.bukkit.display.JOMLMapper;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static java.lang.invoke.MethodHandles.publicLookup;

@ApiStatus.Internal
public class DisplayCubeGizmo_v1_19_4 extends AbstractCubeGizmo {
    private static final Quaterniondc IDENTITY = new Quaterniond();
    private static final Map<GizmoColor, BlockData> COLORS = new HashMap<>();
    private static final MethodHandle spawnHandle = findSpawnHandle();

    static {
        COLORS.put(GizmoColor.WHITE, Material.WHITE_CONCRETE.createBlockData());
        COLORS.put(GizmoColor.RED, Material.RED_CONCRETE.createBlockData());
        COLORS.put(GizmoColor.GREEN, Material.LIME_CONCRETE.createBlockData());
        COLORS.put(GizmoColor.BLUE, Material.BLUE_CONCRETE.createBlockData());
        COLORS.put(GizmoColor.YELLOW, Material.YELLOW_CONCRETE.createBlockData());
        COLORS.put(GizmoColor.GRAY, Material.LIGHT_GRAY_CONCRETE.createBlockData());
        COLORS.put(GizmoColor.AQUA, Material.LIGHT_BLUE_CONCRETE.createBlockData());
    }

    private final Player player;
    private final Plugin plugin;
    private final JOMLMapper mapper;
    private @Nullable BlockDisplay entity;

    public DisplayCubeGizmo_v1_19_4(Player player, Plugin plugin, JOMLMapper mapper) {
        this.player = player;
        this.plugin = plugin;
        this.mapper = mapper;
    }

    @SuppressWarnings({"JavaLangInvokeHandleSignature", "deprecation"})
    private static MethodHandle findSpawnHandle() {
        try {
            return publicLookup().findVirtual(World.class, "spawn", MethodType.methodType(Entity.class, Location.class, Class.class, org.bukkit.util.Consumer.class));
        } catch (Throwable e) {
            return null;
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void show() {
        entity = spawn(player.getWorld(), getLocation(), this::configure);
        if (plugin.isEnabled()) {
            player.showEntity(plugin, entity);
        }
        checkAndClearDirty();
    }

    @Override
    public void update() {
        if (entity == null) {
            return;
        }
        if (checkAndClearDirty()) {
            entity.teleport(getLocation());
            update(entity);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void hide() {
        if (entity == null) {
            return;
        }
        if (plugin.isEnabled()) {
            player.hideEntity(plugin, entity);
        }
        entity.remove();
        entity = null;
    }

    protected @NotNull Location getLocation() {
        Vector3dc position = getPosition();
        return new Location(player.getWorld(), position.x(), position.y(), position.z());
    }

    @SuppressWarnings("deprecation")
    protected @NotNull BlockDisplay spawn(@NotNull World world, @NotNull Location location, @NotNull Consumer<BlockDisplay> consumer) {
        Objects.requireNonNull(spawnHandle);
        try {
            return (BlockDisplay) spawnHandle.invoke(
                    world, location, BlockDisplay.class, (org.bukkit.util.Consumer<BlockDisplay>) this::configure);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    protected void configure(@NotNull BlockDisplay entity) {
        entity.setPersistent(false);
        entity.setVisibleByDefault(false);
        entity.setMetadata("gizmo", new FixedMetadataValue(plugin, this));
        entity.setBrightness(new Display.Brightness(15, 15));
        entity.setGlowing(true);
        update(entity);
    }

    protected void update(@NotNull BlockDisplay entity) {
        GizmoColor color = getColor();
        entity.setBlock(COLORS.get(color));
        entity.setGlowColorOverride(Color.fromRGB(color.asRGB()));
        entity.setTransformation(mapper.createTransformation(getOffset(), getRotation(), getScale(), IDENTITY));
        entity.setBillboard(isBillboard() ? Billboard.CENTER : Billboard.FIXED);
    }
}
