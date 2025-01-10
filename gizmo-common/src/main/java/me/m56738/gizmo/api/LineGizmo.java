package me.m56738.gizmo.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface LineGizmo extends Gizmo {
    @NotNull GizmoAxis DEFAULT_AXIS = GizmoAxis.Y;
    double DEFAULT_LENGTH = 1.0;
    double DEFAULT_WIDTH = 1.0 / 32;

    @NotNull GizmoAxis getAxis();

    void setAxis(@NotNull GizmoAxis axis);

    double getLength();

    void setLength(double length);

    double getWidth();

    void setWidth(double width);
}
