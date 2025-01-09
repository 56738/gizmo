package me.m56738.gizmo.api.cursor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

public interface Cursor {
    @Nullable Intersection intersectPoint(@NotNull Vector3dc position, double threshold);

    @Nullable Intersection intersectLine(@NotNull Vector3dc start, @NotNull Vector3dc end, double threshold);

    @Nullable Intersection intersectPlane(@NotNull Vector3dc point, @NotNull Vector3dc normal);

    @Nullable Intersection intersectPlane(@NotNull Vector3dc point, @NotNull Vector3dc normal, double range);

    @Nullable Intersection intersectBox(@NotNull Vector3dc min, @NotNull Vector3dc max);

    @Nullable Intersection intersectCircle(@NotNull Vector3dc center, @NotNull Vector3dc axis, double radius, double threshold);
}
