package me.m56738.gizmo.bukkit.particle;

import me.m56738.gizmo.api.AbstractCircleGizmo;
import me.m56738.gizmo.api.GizmoColor;
import org.joml.Math;
import org.joml.Quaterniondc;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

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

        Vector3d axis = new Vector3d();
        Vector3d offset = new Vector3d();
        switch (getAxis()) {
            case X:
                axis.x = 1;
                offset.z = radius;
                break;
            case Y:
                axis.y = 1;
                offset.x = radius;
                break;
            case Z:
                axis.z = 1;
                offset.y = radius;
                break;
        }

        Quaterniondc rotation = getRotation();
        offset.rotate(rotation);
        axis.rotate(rotation);

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
