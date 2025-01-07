package me.m56738.gizmo.api;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.Objects;

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
}
