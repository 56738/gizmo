package me.m56738.gizmo.api;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public enum GizmoAxis {
    X(new Vector3d(1, 0, 0)) {
        @Override
        public @NotNull GizmoAxis next() {
            return GizmoAxis.Y;
        }

        @Override
        public double getValue(@NotNull Vector3dc vector) {
            return vector.x();
        }

        @Override
        public void setValue(@NotNull Vector3d vector, double value) {
            vector.x = value;
        }
    },

    Y(new Vector3d(0, 1, 0)) {
        @Override
        public @NotNull GizmoAxis next() {
            return GizmoAxis.Z;
        }

        @Override
        public double getValue(@NotNull Vector3dc vector) {
            return vector.y();
        }

        @Override
        public void setValue(@NotNull Vector3d vector, double value) {
            vector.y = value;
        }
    },

    Z(new Vector3d(0, 0, 1)) {
        @Override
        public @NotNull GizmoAxis next() {
            return GizmoAxis.X;
        }

        @Override
        public double getValue(@NotNull Vector3dc vector) {
            return vector.z();
        }

        @Override
        public void setValue(@NotNull Vector3d vector, double value) {
            vector.z = value;
        }
    };

    private final Vector3dc direction;

    GizmoAxis(Vector3dc direction) {
        this.direction = direction;
    }

    public @NotNull Vector3dc direction() {
        return direction;
    }

    public abstract @NotNull GizmoAxis next();

    public abstract double getValue(@NotNull Vector3dc vector);

    public abstract void setValue(@NotNull Vector3d vector, double value);
}
