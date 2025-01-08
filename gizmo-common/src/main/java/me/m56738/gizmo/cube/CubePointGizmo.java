package me.m56738.gizmo.cube;

import me.m56738.gizmo.AbstractPointGizmo;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

@ApiStatus.Internal
class CubePointGizmo extends AbstractPointGizmo {
    private final CubeGizmo cube;

    public CubePointGizmo(CubeGizmo cube) {
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

        double size = getSize();
        Vector3dc offset = getOffset();
        Quaterniondc rotation = getRotation();

        Vector3d translation = new Vector3d(-size / 2);
        translation.rotate(rotation);
        translation.add(offset);

        cube.setPosition(getPosition());
        cube.setOffset(translation);
        cube.setRotation(rotation);
        cube.setScale(new Vector3d(size));
        cube.setColor(getColor());
        cube.setBillboard(isBillboard());
    }
}
