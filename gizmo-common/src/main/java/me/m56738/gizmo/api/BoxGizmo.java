package me.m56738.gizmo.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.List;
import java.util.function.Supplier;

@ApiStatus.NonExtendable
public interface BoxGizmo extends Gizmo {
    @NotNull Vector3dc DEFAULT_SIZE = new Vector3d();
    double DEFAULT_WIDTH = LineGizmo.DEFAULT_WIDTH;

    static @NotNull BoxGizmo of(@NotNull List<@NotNull LineGizmo> lines) {
        return new LineBoxGizmo(lines.toArray(new LineGizmo[LineBoxGizmo.LINE_INFO.length]));
    }

    static @NotNull BoxGizmo of(@NotNull Supplier<@NotNull LineGizmo> supplier) {
        LineGizmo[] lines = new LineGizmo[LineBoxGizmo.LINE_INFO.length];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = supplier.get();
        }
        return new LineBoxGizmo(lines);
    }

    @NotNull Vector3dc getSize();

    void setSize(@NotNull Vector3dc size);

    double getWidth();

    void setWidth(double width);
}
