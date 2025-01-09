package me.m56738.gizmo.api;

import me.m56738.gizmo.AbstractCircleGizmo;
import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Vector3d;

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

        GizmoAxis rotationAxis = getAxis();
        GizmoAxis pieceAxis = rotationAxis.next();
        GizmoAxis offsetAxis = pieceAxis.next();

        Vector3d pieceOffset = new Vector3d();
        pieceAxis.setValue(pieceOffset, -pieceLength / 2);
        offsetAxis.setValue(pieceOffset, radius);

        double currentAngle = 0;
        Quaterniond currentRotation = new Quaterniond();
        Vector3d currentOffset = new Vector3d();
        for (LineGizmo piece : pieces) {
            piece.setPosition(getPosition());
            piece.setRotation(getRotation().rotateAxis(currentAngle, rotationAxis.direction(), currentRotation));
            piece.setOffset(pieceOffset.rotate(currentRotation, currentOffset).add(getOffset()));
            piece.setAxis(pieceAxis);
            piece.setWidth(width);
            piece.setLength(pieceLength);
            piece.setColor(getColor());
            currentAngle += pieceAngle;
        }
    }
}
