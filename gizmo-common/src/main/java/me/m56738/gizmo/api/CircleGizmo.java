package me.m56738.gizmo.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@ApiStatus.NonExtendable
public interface CircleGizmo extends Gizmo {
    @NotNull GizmoAxis DEFAULT_AXIS = GizmoAxis.Y;
    double DEFAULT_RADIUS = 1.0;
    double DEFAULT_WIDTH = LineGizmo.DEFAULT_WIDTH;

    static @NotNull CircleGizmo of(@NotNull List<@NotNull LineGizmo> lines) {
        return new LineCircleGizmo(new ArrayList<>(lines));
    }

    static @NotNull CircleGizmo of(int count, @NotNull Supplier<@NotNull LineGizmo> supplier) {
        ArrayList<LineGizmo> lines = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            lines.add(supplier.get());
        }
        return new LineCircleGizmo(lines);
    }

    @NotNull GizmoAxis getAxis();

    void setAxis(@NotNull GizmoAxis axis);

    double getRadius();

    void setRadius(double radius);

    double getWidth();

    void setWidth(double width);
}
