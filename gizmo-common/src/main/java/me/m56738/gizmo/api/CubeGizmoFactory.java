package me.m56738.gizmo.api;

import org.jetbrains.annotations.NotNull;

public interface CubeGizmoFactory {
    @NotNull CubeGizmo createCube();

    boolean isVisibleThroughWalls();
}
