package me.m56738.gizmo.cube;

import me.m56738.gizmo.api.Gizmo;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

@ApiStatus.Internal
public interface CubeGizmo extends Gizmo {
    @NotNull Vector3dc DEFAULT_SCALE = new Vector3d(1);
    boolean DEFAULT_BILLBOARD = false;

    @NotNull Vector3dc getScale();

    void setScale(@NotNull Vector3dc scale);

    boolean isBillboard();

    void setBillboard(boolean billboard);
}
