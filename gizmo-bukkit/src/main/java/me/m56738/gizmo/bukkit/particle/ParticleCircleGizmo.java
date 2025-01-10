package me.m56738.gizmo.bukkit.particle;

import me.m56738.gizmo.AbstractCircleGizmo;
import me.m56738.gizmo.api.color.GizmoColor;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Math;
import org.joml.Quaterniondc;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public class ParticleCircleGizmo extends AbstractCircleGizmo {
    private final ParticleSpawner particleSpawner;
    private final List<Vector3d> positions = new ArrayList<>();
    private boolean visible;

    public ParticleCircleGizmo(ParticleSpawner particleSpawner) {
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
        double radius = getRadius();

        Vector3d axis = new Vector3d(getAxis().direction());
        Vector3d offset = new Vector3d(getAxis().next().direction()).mul(radius);
        Quaterniondc rotation = getRotation();
        axis.rotate(rotation);
        offset.rotate(rotation);

        double axisX = axis.x();
        double axisY = axis.y();
        double axisZ = axis.z();

        double circumference = 2 * Math.PI * radius;
        double density = particleSpawner.getDensity(getWidth());
        int count = Math.clamp(0, 100, (int) Math.round(circumference * density));

        while (positions.size() < count) {
            positions.add(new Vector3d());
        }
        while (positions.size() > count) {
            positions.remove(positions.size() - 1);
        }

        double angle = 2 * Math.PI / count;
        for (int i = 0; i < count; i++) {
            offset.rotateAxis(angle, axisX, axisY, axisZ);
            getPosition().add(offset, positions.get(i));
        }
    }
}
