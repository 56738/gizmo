package me.m56738.gizmo.api;

public enum GizmoColor {
    WHITE(0xFFFFFF),
    RED(0xFF5555),
    GREEN(0x55FF55),
    BLUE(0x5555FF),
    YELLOW(0xFFFF55),
    GRAY(0xAAAAAA),
    AQUA(0x55FFFF);

    private final int color;

    GizmoColor(int color) {
        this.color = color;
    }

    public int getRGB() {
        return color;
    }
}
