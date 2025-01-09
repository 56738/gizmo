package me.m56738.gizmo;

import me.m56738.gizmo.api.PointGizmo;
import me.m56738.gizmo.api.cursor.Cursor;
import me.m56738.gizmo.api.cursor.Intersection;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

import java.util.Objects;

@ApiStatus.Internal
public abstract class AbstractPointGizmo extends AbstractGizmo implements PointGizmo {
    private double size = DEFAULT_SIZE;
    private boolean billboard = DEFAULT_BILLBOARD;

    @Override
    public double getSize() {
        return size;
    }

    @Override
    public void setSize(double size) {
        if (!Objects.equals(this.size, size)) {
            this.size = size;
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
        Vector3d position = getPosition().add(getOffset(), new Vector3d());
        return cursor.intersectPoint(position, threshold);
    }
}
