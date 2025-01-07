package me.m56738.gizmo.api;

import org.jetbrains.annotations.NotNull;
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

    void show();

    void update();

    void hide();
}
