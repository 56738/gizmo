package me.m56738.gizmo.api.color;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface GizmoColor {
    @NotNull GizmoColor WHITE = new GizmoColorImpl(0xFFFFFF);
    @NotNull GizmoColor RED = new GizmoColorImpl(0xFF5555);
    @NotNull GizmoColor GREEN = new GizmoColorImpl(0x55FF55);
    @NotNull GizmoColor BLUE = new GizmoColorImpl(0x5555FF);
    @NotNull GizmoColor YELLOW = new GizmoColorImpl(0xFFFF55);
    @NotNull GizmoColor GRAY = new GizmoColorImpl(0xAAAAAA);
    @NotNull GizmoColor AQUA = new GizmoColorImpl(0x55FFFF);

    int asRGB();
}
