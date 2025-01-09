package me.m56738.gizmo.api;

import me.m56738.gizmo.api.cursor.Cursor;
import me.m56738.gizmo.api.cursor.Intersection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public interface Gizmo {
    @NotNull GizmoColor DEFAULT_COLOR = GizmoColor.WHITE;

    @NotNull Vector3dc getPosition();

    void setPosition(@NotNull Vector3dc position);

    @NotNull Vector3dc getOffset();

    void setOffset(@NotNull Vector3dc offset);

    @NotNull Quaterniondc getRotation();

    void setRotation(@NotNull Quaterniondc rotation);

    @NotNull GizmoColor getColor();

    void setColor(@NotNull GizmoColor color);

    @Nullable Intersection intersect(@NotNull Cursor cursor, double threshold);

    void show();

    void update();

    void hide();
}
