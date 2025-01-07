package me.m56738.gizmo.api;

import org.jetbrains.annotations.NotNull;

public interface PointGizmo extends Gizmo {
    double DEFAULT_SIZE = 1.0 / 16;
    boolean DEFAULT_BILLBOARD = false;

    static @NotNull PointGizmo of(@NotNull CubeGizmo cube) {
        return new CubePointGizmo(cube);
    }

    double getSize();

    void setSize(double size);

    boolean isBillboard();

    void setBillboard(boolean billboard);
}
