package me.m56738.gizmo.api;

import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.List;

class LineCircleGizmo extends AbstractCircleGizmo implements CircleGizmo {
    private final List<LineGizmo> pieces;

    LineCircleGizmo(List<LineGizmo> pieces) {
        this.pieces = pieces;
    }

    @Override
    public void show() {
        updatePieces();
        for (LineGizmo piece : pieces) {
            piece.show();
        }
    }

    @Override
    public void update() {
        updatePieces();
        for (LineGizmo piece : pieces) {
            piece.update();
        }
    }

    @Override
    public void hide() {
        for (LineGizmo piece : pieces) {
            piece.hide();
        }
    }

    private void updatePieces() {
        if (!checkAndClearDirty()) {
            return;
        }

        double radius = getRadius();
        double width = getWidth();
        double pieceAngle = 2 * Math.PI / pieces.size();
        double pieceLength = 2 * Math.tan(pieceAngle / 2) * (radius + width / 2);

        Vector3dc axis;
        Vector3dc pieceOffset;
        GizmoAxis pieceAxis;
        switch (getAxis()) {
            case X:
                axis = new Vector3d(1, 0, 0);
                pieceOffset = new Vector3d(0, -pieceLength / 2, radius);
                pieceAxis = GizmoAxis.Y;
                break;
            case Y:
                axis = new Vector3d(0, 1, 0);
                pieceOffset = new Vector3d(radius, 0, -pieceLength / 2);
                pieceAxis = GizmoAxis.Z;
                break;
            case Z:
                axis = new Vector3d(0, 0, 1);
                pieceOffset = new Vector3d(-pieceLength / 2, radius, 0);
                pieceAxis = GizmoAxis.X;
                break;
            default:
                throw new IllegalArgumentException();
        }

        double currentAngle = 0;
        Quaterniond currentRotation = new Quaterniond();
        Vector3d currentOffset = new Vector3d();
        for (LineGizmo piece : pieces) {
            piece.setPosition(getPosition());
            piece.setRotation(getRotation().rotateAxis(currentAngle, axis, currentRotation));
            piece.setOffset(pieceOffset.rotate(currentRotation, currentOffset).add(getOffset()));
            piece.setAxis(pieceAxis);
            piece.setWidth(width);
            piece.setLength(pieceLength);
            piece.setColor(getColor());
            currentAngle += pieceAngle;
        }
    }
}
