package me.m56738.gizmo.api.cursor;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

class RayCursorImpl implements RayCursor {
    private final Vector3dc origin;
    private final Vector3dc direction;
    private final Vector3dc end;
    private final double length;

    RayCursorImpl(Vector3dc origin, Vector3dc direction, Vector3dc end, double length) {
        this.origin = origin;
        this.direction = direction;
        this.end = end;
        this.length = length;
    }

    @Override
    public @NotNull Vector3dc origin() {
        return origin;
    }

    @Override
    public @NotNull Vector3dc direction() {
        return direction;
    }

    @Override
    public @NotNull Vector3dc end() {
        return end;
    }

    @Override
    public double length() {
        return length;
    }
}
