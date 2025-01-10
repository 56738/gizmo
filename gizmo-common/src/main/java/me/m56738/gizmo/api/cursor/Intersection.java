package me.m56738.gizmo.api.cursor;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

@ApiStatus.NonExtendable
public interface Intersection {
    @NotNull Vector3dc positionOnCursor();

    @NotNull Vector3dc positionOnTarget();
}
