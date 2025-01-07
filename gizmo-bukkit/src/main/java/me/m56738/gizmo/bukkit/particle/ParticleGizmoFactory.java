package me.m56738.gizmo.bukkit.particle;

import me.m56738.gizmo.api.CircleGizmo;
import me.m56738.gizmo.api.GizmoFactory;
import me.m56738.gizmo.api.LineGizmo;
import me.m56738.gizmo.api.PointGizmo;
import org.jetbrains.annotations.NotNull;

public class ParticleGizmoFactory implements GizmoFactory {
    private final ParticleSpawner particleSpawner;

    public ParticleGizmoFactory(ParticleSpawner particleSpawner) {
        this.particleSpawner = particleSpawner;
    }

    @Override
    public @NotNull PointGizmo createPoint() {
        return new ParticlePointGizmo(particleSpawner);
    }

    @Override
    public @NotNull LineGizmo createLine() {
        return new ParticleLineGizmo(particleSpawner);
    }

    @Override
    public @NotNull CircleGizmo createCircle() {
        return new ParticleCircleGizmo(particleSpawner);
    }

    @Override
    public boolean isVisibleThroughWalls() {
        return false;
    }
}
