package me.m56738.gizmo;

import me.m56738.gizmo.api.Gizmo;
import me.m56738.gizmo.api.GizmoColor;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.Objects;

@ApiStatus.Internal
public abstract class AbstractGizmo implements Gizmo {
    private final Vector3d position = new Vector3d();
    private final Vector3d offset = new Vector3d();
    private final Quaterniond rotation = new Quaterniond();
    private GizmoColor color = DEFAULT_COLOR;
    private boolean dirty = true;

    @Override
    public @NotNull Vector3dc getPosition() {
        return position;
    }

    @Override
    public void setPosition(@NotNull Vector3dc position) {
        if (!Objects.equals(this.position, position)) {
            this.position.set(position);
            markDirty();
        }
    }

    @Override
    public @NotNull Vector3dc getOffset() {
        return offset;
    }

    @Override
    public void setOffset(@NotNull Vector3dc offset) {
        if (!Objects.equals(this.offset, offset)) {
            this.offset.set(offset);
            markDirty();
        }
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return rotation;
    }

    @Override
    public void setRotation(@NotNull Quaterniondc rotation) {
        if (!Objects.equals(this.rotation, rotation)) {
            this.rotation.set(rotation);
            markDirty();
        }
    }

    @Override
    public @NotNull GizmoColor getColor() {
        return color;
    }

    @Override
    public void setColor(@NotNull GizmoColor color) {
        if (!Objects.equals(this.color, color)) {
            this.color = color;
            markDirty();
        }
    }

    protected void markDirty() {
        dirty = true;
    }

    protected boolean checkAndClearDirty() {
        boolean dirty = this.dirty;
        this.dirty = false;
        return dirty;
    }
}
