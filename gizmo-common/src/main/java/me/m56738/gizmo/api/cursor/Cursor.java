package me.m56738.gizmo.api.cursor;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

@ApiStatus.NonExtendable
public interface Cursor {
    static @NotNull Cursor of(@NotNull Vector3dc origin, @NotNull Vector3dc direction, double length) {
        return new RayCursor(origin, direction, length);
    }

    static @NotNull Cursor of(@NotNull Vector3dc origin, @NotNull Vector3dc end) {
        return new RayCursor(origin, end);
    }

    @Nullable Intersection intersectPoint(@NotNull Vector3dc position, double threshold);

    @Nullable Intersection intersectLine(@NotNull Vector3dc start, @NotNull Vector3dc end, double threshold);

    @Nullable Intersection intersectPlane(@NotNull Vector3dc point, @NotNull Vector3dc normal);

    @Nullable Intersection intersectPlane(@NotNull Vector3dc point, @NotNull Vector3dc normal, double range);

    @Nullable Intersection intersectBox(@NotNull Vector3dc min, @NotNull Vector3dc max);

    @Nullable Intersection intersectBox(@NotNull Vector3dc min, @NotNull Vector3dc max, @NotNull Vector3dc center, @NotNull Quaterniondc rotation);

    @Nullable Intersection intersectCircle(@NotNull Vector3dc center, @NotNull Vector3dc axis, double radius, double threshold);
}
