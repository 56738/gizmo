package me.m56738.gizmo.intersection;

import me.m56738.gizmo.api.cursor.Intersection;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

public class SimpleIntersection implements Intersection {
    private final Vector3dc positionOnCursor;
    private final Vector3dc positionOnTarget;

    public SimpleIntersection(Vector3dc positionOnCursor, Vector3dc positionOnTarget) {
        this.positionOnCursor = positionOnCursor;
        this.positionOnTarget = positionOnTarget;
    }

    public SimpleIntersection(Vector3dc position) {
        this(position, position);
    }

    @Override
    public @NotNull Vector3dc positionOnCursor() {
        return positionOnCursor;
    }

    @Override
    public @NotNull Vector3dc positionOnTarget() {
        return positionOnTarget;
    }
}
