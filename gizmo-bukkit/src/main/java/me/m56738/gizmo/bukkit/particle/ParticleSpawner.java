package me.m56738.gizmo.bukkit.particle;

import me.m56738.gizmo.api.GizmoColor;
import org.joml.Vector3dc;

public interface ParticleSpawner {
    void spawnParticle(Vector3dc position, GizmoColor color, double size);

    double getDensity(double size);
}
