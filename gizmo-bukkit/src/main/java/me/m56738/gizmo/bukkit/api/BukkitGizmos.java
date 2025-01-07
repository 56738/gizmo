package me.m56738.gizmo.bukkit.api;

import me.m56738.gizmo.api.GizmoFactory;
import me.m56738.gizmo.bukkit.display.DisplayGizmos;
import me.m56738.gizmo.bukkit.particle.ParticleGizmos;
import me.m56738.gizmo.bukkit.util.ReflectionUtil;
import me.m56738.gizmo.bukkit.viaversion.ViaGizmos;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface BukkitGizmos extends AutoCloseable {
    static @NotNull BukkitGizmos create(@NotNull Plugin plugin) {
        BukkitGizmos gizmos;
        if (ReflectionUtil.hasClass("org.bukkit.entity.BlockDisplay")) {
            gizmos = DisplayGizmos.create(plugin);
        } else {
            gizmos = ParticleGizmos.create();
        }

        if (ReflectionUtil.hasClass("com.viaversion.viaversion.api.Via")) {
            return ViaGizmos.create(gizmos);
        } else {
            return gizmos;
        }
    }

    @NotNull GizmoFactory player(@NotNull Player player);

    @Override
    void close();
}
