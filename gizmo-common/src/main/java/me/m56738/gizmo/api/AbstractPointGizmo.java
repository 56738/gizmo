package me.m56738.gizmo.api;

import java.util.Objects;

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
}
