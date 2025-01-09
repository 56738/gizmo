package me.m56738.gizmo.api;

import org.jetbrains.annotations.NotNull;

public interface GizmoFactory {
    @NotNull PointGizmo createPoint();

    @NotNull LineGizmo createLine();

    @NotNull CircleGizmo createCircle();

    @NotNull BoxGizmo createBox();

    boolean isVisibleThroughWalls();
}
