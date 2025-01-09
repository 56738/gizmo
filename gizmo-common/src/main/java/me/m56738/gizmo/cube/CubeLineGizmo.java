package me.m56738.gizmo.cube;

import me.m56738.gizmo.AbstractLineGizmo;
import me.m56738.gizmo.api.GizmoAxis;
import me.m56738.gizmo.api.GizmoColor;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

@ApiStatus.Internal
class CubeLineGizmo extends AbstractLineGizmo {
    private final CubeGizmo cube;

    public CubeLineGizmo(CubeGizmo cube) {
        this.cube = cube;
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
        axis.setValue(translation, 0);
        translation.rotate(rotation);
        translation.add(offset);

        Vector3d scale = new Vector3d(width);
        axis.setValue(scale, length);

        cube.setPosition(position);
        cube.setOffset(translation);
        cube.setRotation(rotation);
        cube.setScale(scale);
        cube.setColor(color);
    }
}
