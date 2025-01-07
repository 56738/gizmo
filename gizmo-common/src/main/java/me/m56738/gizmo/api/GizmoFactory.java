package me.m56738.gizmo.api;

import org.jetbrains.annotations.NotNull;

public interface GizmoFactory {
    static @NotNull GizmoFactory of(@NotNull CubeGizmoFactory cubeGizmoFactory) {
        return new CubeGizmoGizmoFactory(cubeGizmoFactory);
    }

    @NotNull PointGizmo createPoint();

    @NotNull LineGizmo createLine();

    @NotNull CircleGizmo createCircle();

    boolean isVisibleThroughWalls();
}
