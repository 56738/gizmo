package me.m56738.gizmo.api;

import me.m56738.gizmo.AbstractBoxGizmo;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

class LineBoxGizmo extends AbstractBoxGizmo {
    static final LineInfo[] LINE_INFO = new LineInfo[]{
            new LineInfo(0, 0, 0, GizmoAxis.X),
            new LineInfo(0, 0, 1, GizmoAxis.X),
            new LineInfo(0, 1, 0, GizmoAxis.X),
            new LineInfo(0, 1, 1, GizmoAxis.X),
            new LineInfo(0, 0, 0, GizmoAxis.Y),
            new LineInfo(0, 0, 1, GizmoAxis.Y),
            new LineInfo(1, 0, 0, GizmoAxis.Y),
            new LineInfo(1, 0, 1, GizmoAxis.Y),
            new LineInfo(0, 0, 0, GizmoAxis.Z),
            new LineInfo(1, 0, 0, GizmoAxis.Z),
            new LineInfo(0, 1, 0, GizmoAxis.Z),
            new LineInfo(1, 1, 0, GizmoAxis.Z)
    };

    private final LineGizmo[] lines;

    LineBoxGizmo(LineGizmo[] lines) {
        if (lines.length != LINE_INFO.length) {
            throw new IllegalArgumentException();
        }
        this.lines = lines;
        for (int i = 0; i < LINE_INFO.length; i++) {
            lines[i].setAxis(LINE_INFO[i].axis);
        }
    }

    @Override
    public void show() {
        updateLines();
        for (LineGizmo line : lines) {
            line.show();
        }
    }

    @Override
    public void update() {
        updateLines();
        for (LineGizmo line : lines) {
            line.update();
        }
    }

    @Override
    public void hide() {
        for (LineGizmo line : lines) {
            line.hide();
        }
    }

    private void updateLines() {
        if (!checkAndClearDirty()) {
            return;
        }

        Vector3dc position = getPosition();
        Vector3dc offset = getOffset();
        Quaterniondc rotation = getRotation();
        GizmoColor color = getColor();
        Vector3dc size = getSize();
        double width = getWidth();

        Vector3d currentOffset = new Vector3d();
        for (int i = 0; i < LINE_INFO.length; i++) {
            LineGizmo line = lines[i];
            LineInfo info = LINE_INFO[i];
            line.setPosition(position);
            line.setOffset(info.offset
                    .mul(size, currentOffset)
                    .fma(-width / 2, info.axis.direction())
                    .rotate(rotation)
                    .add(offset));
            line.setRotation(rotation);
            line.setColor(color);
            line.setWidth(width);
            line.setLength(info.axis.direction().dot(size) + width);
        }
    }

    static class LineInfo {
        private final Vector3dc offset;
        private final GizmoAxis axis;

        private LineInfo(double x, double y, double z, GizmoAxis axis) {
            this.offset = new Vector3d(x - 0.5, y - 0.5, z - 0.5);
            this.axis = axis;
        }
    }
}
