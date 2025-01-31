package me.m56738.gizmo.bukkit.particle;

import me.m56738.gizmo.AbstractLineGizmo;
import me.m56738.gizmo.api.color.GizmoColor;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Math;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public class ParticleLineGizmo extends AbstractLineGizmo {
    private final ParticleSpawner particleSpawner;
    private final List<Vector3d> positions = new ArrayList<>();
    private boolean visible;

    public ParticleLineGizmo(ParticleSpawner particleSpawner) {
        this.particleSpawner = particleSpawner;
    }

    @Override
    public void show() {
        visible = true;
    }

    @Override
    public void update() {
        if (checkAndClearDirty()) {
            updatePositions();
        }

        if (visible) {
            GizmoColor color = getColor();
            double width = getWidth();
            for (Vector3d position : positions) {
                particleSpawner.spawnParticle(position, color, width);
            }
        }
    }

    @Override
    public void hide() {
        visible = false;
    }

    private void updatePositions() {
        double length = getLength();

        double density = particleSpawner.getDensity(getWidth());
        int count = Math.clamp(2, 100, (int) Math.round(length * density));
        while (positions.size() < count) {
            positions.add(new Vector3d());
        }
        while (positions.size() > count) {
            positions.remove(positions.size() - 1);
        }

        Vector3d offset = getAxis().direction().mul(length / (count - 1), new Vector3d());
        offset.rotate(getRotation());

        Vector3d position = getPosition().add(getOffset(), new Vector3d());
        for (int i = 0; i < count; i++) {
            positions.get(i).set(position);
            position.add(offset);
        }
    }
}
