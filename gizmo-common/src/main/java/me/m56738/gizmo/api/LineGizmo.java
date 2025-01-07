package me.m56738.gizmo.api;

import org.jetbrains.annotations.NotNull;

public interface LineGizmo extends Gizmo {
    @NotNull GizmoAxis DEFAULT_AXIS = GizmoAxis.Y;
    double DEFAULT_LENGTH = 1.0;
    double DEFAULT_WIDTH = 1.0 / 32;

    static @NotNull LineGizmo of(@NotNull CubeGizmo cube) {
        return new CubeLineGizmo(cube);
    }

    @NotNull GizmoAxis getAxis();

    void setAxis(@NotNull GizmoAxis axis);

    double getLength();

    void setLength(double length);

    double getWidth();

    void setWidth(double width);
}