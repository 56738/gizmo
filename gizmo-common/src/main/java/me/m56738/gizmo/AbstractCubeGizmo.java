package me.m56738.gizmo;

import me.m56738.gizmo.api.cursor.Cursor;
import me.m56738.gizmo.api.cursor.Intersection;
import me.m56738.gizmo.cube.CubeGizmo;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.Objects;

@ApiStatus.Internal
public abstract class AbstractCubeGizmo extends AbstractGizmo implements CubeGizmo {
    private final Vector3d scale = new Vector3d(DEFAULT_SCALE);
    private boolean billboard = DEFAULT_BILLBOARD;

    @Override
    public @NotNull Vector3dc getScale() {
        return scale;
    }

    @Override
    public void setScale(@NotNull Vector3dc scale) {
        if (!Objects.equals(this.scale, scale)) {
            this.scale.set(scale);
            markDirty();
        }
    }

    @Override
    public boolean isBillboard() {
        return billboard;
    }

    @Override
    public void setBillboard(boolean billboard) {
        if (!Objects.equals(this.billboard, billboard)) {
            this.billboard = billboard;
            markDirty();
        }
    }

    @Override
    public @Nullable Intersection intersect(@NotNull Cursor cursor, double threshold) {
        return null;
    }
}
