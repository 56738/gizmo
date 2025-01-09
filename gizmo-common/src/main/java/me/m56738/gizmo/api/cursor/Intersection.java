package me.m56738.gizmo.api.cursor;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

public interface Intersection {
    @NotNull Vector3dc positionOnCursor();

    @NotNull Vector3dc positionOnTarget();
}
