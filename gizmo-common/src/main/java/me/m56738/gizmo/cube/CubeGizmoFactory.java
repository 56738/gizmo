package me.m56738.gizmo.cube;

import me.m56738.gizmo.api.BoxGizmo;
import me.m56738.gizmo.api.CircleGizmo;
import me.m56738.gizmo.api.GizmoFactory;
import me.m56738.gizmo.api.LineGizmo;
import me.m56738.gizmo.api.PointGizmo;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public interface CubeGizmoFactory extends GizmoFactory {
    @NotNull CubeGizmo createCube();

    @Override
    default @NotNull PointGizmo createPoint() {
        return new CubePointGizmo(createCube());
    }

    @Override
    default @NotNull LineGizmo createLine() {
        return new CubeLineGizmo(createCube());
    }

    @Override
    default @NotNull CircleGizmo createCircle() {
        return CircleGizmo.of(16, this::createLine);
    }

    default @NotNull BoxGizmo createBox() {
        return BoxGizmo.of(this::createLine);
    }
}
