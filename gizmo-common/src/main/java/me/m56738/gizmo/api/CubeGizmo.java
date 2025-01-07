package me.m56738.gizmo.api;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public interface CubeGizmo extends Gizmo {
    @NotNull Vector3dc DEFAULT_SCALE = new Vector3d(1);
    boolean DEFAULT_BILLBOARD = false;

    @NotNull Vector3dc getScale();

    void setScale(@NotNull Vector3dc scale);

    boolean isBillboard();

    void setBillboard(boolean billboard);
}
