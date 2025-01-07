package me.m56738.gizmo.api;

import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

class CubeLineGizmo extends AbstractLineGizmo {
    private final CubeGizmo cube;

    public CubeLineGizmo(CubeGizmo cube) {
        this.cube = cube;
    }

    private static void setValue(GizmoAxis axis, Vector3d vector, double value) {
        switch (axis) {
            case X:
                vector.x = value;
                break;
            case Y:
                vector.y = value;
                break;
            case Z:
                vector.z = value;
                break;
        }
    }

    @Override
    public void show() {
        updateCube();
        cube.show();
    }

    @Override
    public void update() {
        updateCube();
        cube.update();
    }

    @Override
    public void hide() {
        cube.hide();
    }

    private void updateCube() {
        if (!checkAndClearDirty()) {
            return;
        }

        GizmoAxis axis = getAxis();
        double length = getLength();
        double width = getWidth();
        Vector3dc position = getPosition();
        Vector3dc offset = getOffset();
        Quaterniondc rotation = getRotation();
        GizmoColor color = getColor();

        Vector3d translation = new Vector3d(-width / 2);
        Vector3d scale = new Vector3d(width);
        switch (axis) {
            case X:
                translation.x = 0;
                scale.x = length;
                break;
            case Y:
                translation.y = 0;
                scale.y = length;
                break;
            case Z:
                translation.z = 0;
                scale.z = length;
                break;
        }
        translation.rotate(rotation);
        translation.add(offset);

        cube.setPosition(position);
        cube.setOffset(translation);
        cube.setRotation(rotation);
        cube.setScale(scale);
        cube.setColor(color);
    }
}
