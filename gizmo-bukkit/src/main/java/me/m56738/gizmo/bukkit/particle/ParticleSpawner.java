package me.m56738.gizmo.bukkit.particle;

import me.m56738.gizmo.api.color.GizmoColor;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Vector3dc;

@ApiStatus.Internal
public interface ParticleSpawner {
    void spawnParticle(Vector3dc position, GizmoColor color, double size);

    double getDensity(double size);
}
