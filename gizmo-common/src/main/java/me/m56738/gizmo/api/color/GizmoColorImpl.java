package me.m56738.gizmo.api.color;

class GizmoColorImpl implements GizmoColor {
    private final int color;

    GizmoColorImpl(int color) {
        this.color = color;
    }

    @Override
    public int asRGB() {
        return color;
    }
}
