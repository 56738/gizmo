package me.m56738.gizmo.api;

import org.jetbrains.annotations.NotNull;

class CubeGizmoGizmoFactory implements GizmoFactory {
    private final CubeGizmoFactory cubeGizmoFactory;

    CubeGizmoGizmoFactory(CubeGizmoFactory cubeGizmoFactory) {
        this.cubeGizmoFactory = cubeGizmoFactory;
    }

    @Override
    public @NotNull PointGizmo createPoint() {
        return PointGizmo.of(cubeGizmoFactory.createCube());
    }

    @Override
    public @NotNull LineGizmo createLine() {
        return LineGizmo.of(cubeGizmoFactory.createCube());
    }

    @Override
    public @NotNull CircleGizmo createCircle() {
        return CircleGizmo.of(16, this::createLine);
    }

    @Override
    public boolean isVisibleThroughWalls() {
        return cubeGizmoFactory.isVisibleThroughWalls();
    }
}
