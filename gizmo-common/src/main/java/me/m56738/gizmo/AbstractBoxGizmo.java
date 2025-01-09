package me.m56738.gizmo;

import me.m56738.gizmo.api.BoxGizmo;
import me.m56738.gizmo.api.cursor.Cursor;
import me.m56738.gizmo.api.cursor.Intersection;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.Objects;

@ApiStatus.Internal
public abstract class AbstractBoxGizmo extends AbstractGizmo implements BoxGizmo {
    private final Vector3d size = new Vector3d(DEFAULT_SIZE);
    private double width = DEFAULT_WIDTH;

    @Override
    public @NotNull Vector3dc getSize() {
        return size;
    }

    @Override
    public void setSize(@NotNull Vector3dc size) {
        if (!Objects.equals(this.size, size)) {
            this.size.set(size);
            markDirty();
        }
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public void setWidth(double width) {
        if (!Objects.equals(this.width, width)) {
            this.width = width;
            markDirty();
        }
    }

    @Override
    public @Nullable Intersection intersect(@NotNull Cursor cursor, double threshold) {
        Vector3d center = getPosition().add(getOffset(), new Vector3d());
        Vector3d min = center.fma(-0.5, getSize(), new Vector3d());
        Vector3d max = center.fma(0.5, getSize(), new Vector3d());
        return cursor.intersectBox(min, max, center, getRotation());
    }
}
