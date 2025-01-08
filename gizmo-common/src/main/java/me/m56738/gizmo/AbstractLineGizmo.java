package me.m56738.gizmo;

import me.m56738.gizmo.api.GizmoAxis;
import me.m56738.gizmo.api.LineGizmo;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@ApiStatus.Internal
public abstract class AbstractLineGizmo extends AbstractGizmo implements LineGizmo {
    private GizmoAxis axis = DEFAULT_AXIS;
    private double length = DEFAULT_LENGTH;
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
    public double getLength() {
        return length;
    }

    @Override
    public void setLength(double length) {
        if (!Objects.equals(this.length, length)) {
            this.length = length;
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
