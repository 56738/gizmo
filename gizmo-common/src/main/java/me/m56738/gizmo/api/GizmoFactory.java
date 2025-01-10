package me.m56738.gizmo.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface GizmoFactory {
    @NotNull PointGizmo createPoint();

    @NotNull LineGizmo createLine();

    @NotNull CircleGizmo createCircle();

    @NotNull BoxGizmo createBox();

    boolean isVisibleThroughWalls();
}
