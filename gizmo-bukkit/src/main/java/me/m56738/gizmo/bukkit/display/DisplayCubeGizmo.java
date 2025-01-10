package me.m56738.gizmo.bukkit.display;

import me.m56738.gizmo.AbstractCubeGizmo;
import me.m56738.gizmo.api.color.GizmoColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.Display.Billboard;
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
import java.util.HashMap;
import java.util.Map;

import static java.lang.invoke.MethodHandles.publicLookup;
import static java.lang.invoke.MethodType.methodType;

@ApiStatus.Internal
public class DisplayCubeGizmo extends AbstractCubeGizmo {
    private static final Quaterniondc IDENTITY = new Quaterniond();
    private static final Map<GizmoColor, BlockData> COLORS = new HashMap<>();
    private static final MethodHandle TELEPORT_DURATION_SETTER = findTeleportDurationSetter();

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

    public DisplayCubeGizmo(Player player, Plugin plugin, JOMLMapper mapper) {
        this.player = player;
        this.plugin = plugin;
        this.mapper = mapper;
    }

    @SuppressWarnings("JavaLangInvokeHandleSignature")
    private static MethodHandle findTeleportDurationSetter() {
        try {
            return publicLookup().findVirtual(Display.class, "setTeleportDuration", methodType(void.class, int.class));
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    private static void setTeleportDuration(Display entity, int duration) {
        if (TELEPORT_DURATION_SETTER == null) {
            return;
        }
        try {
            TELEPORT_DURATION_SETTER.invoke(entity, duration);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void show() {
        entity = player.getWorld().spawn(getLocation(), BlockDisplay.class, this::configure);
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

    @SuppressWarnings("UnstableApiUsage")
    protected void configure(@NotNull BlockDisplay entity) {
        entity.setPersistent(false);
        entity.setVisibleByDefault(false);
        entity.setMetadata("gizmo", new FixedMetadataValue(plugin, this));
        entity.setInterpolationDuration(3);
        entity.setBrightness(new Display.Brightness(15, 15));
        entity.setGlowing(true);
        setTeleportDuration(entity, 3);
        update(entity);
    }

    protected void update(@NotNull BlockDisplay entity) {
        GizmoColor color = getColor();
        entity.setBlock(COLORS.get(color));
        entity.setGlowColorOverride(Color.fromRGB(color.asRGB()));
        entity.setTransformation(mapper.createTransformation(getOffset(), getRotation(), getScale(), IDENTITY));
        entity.setBillboard(isBillboard() ? Billboard.CENTER : Billboard.FIXED);
        entity.setInterpolationDelay(0);
    }
}
