package me.m56738.gizmo;

import me.m56738.gizmo.api.CircleGizmo;
import me.m56738.gizmo.api.GizmoAxis;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@ApiStatus.Internal
public abstract class AbstractCircleGizmo extends AbstractGizmo implements CircleGizmo {
    private GizmoAxis axis = DEFAULT_AXIS;
    private double radius = DEFAULT_RADIUS;
    private double width = DEFAULT_WIDTH;

    @Override
    public @NotNull GizmoAxis getAxis() {
        return axis;
    }

    @Override
    public void setAxis(@NotNull GizmoAxis axis) {
        if (!Objects.equals(this.axis, axis)) {
            this.axis = axis;
            markDirty();
        }
    }

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public void setRadius(double radius) {
        if (!Objects.equals(this.radius, radius)) {
            this.radius = radius;
            markDirty();
        }
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public void setWidth(double width) {
        if (!Objects.equals(this.width, width)) {
            this.width = width;
            markDirty();
        }
    }
}
