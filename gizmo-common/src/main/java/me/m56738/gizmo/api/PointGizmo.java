package me.m56738.gizmo.api;

public interface PointGizmo extends Gizmo {
    double DEFAULT_SIZE = 1.0 / 16;
    boolean DEFAULT_BILLBOARD = false;

    double getSize();

    void setSize(double size);

    boolean isBillboard();

    void setBillboard(boolean billboard);
}
