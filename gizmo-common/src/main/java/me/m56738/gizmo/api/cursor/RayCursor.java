package me.m56738.gizmo.api.cursor;

import me.m56738.gizmo.intersection.SimpleIntersection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Intersectiond;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public interface RayCursor extends Cursor {
    static @NotNull RayCursor of(@NotNull Vector3dc origin, @NotNull Vector3dc direction, double length) {
        Vector3d normalizedDirection = direction.normalize(new Vector3d());
        Vector3d end = origin.fma(length, normalizedDirection, new Vector3d());
        return new RayCursorImpl(origin, normalizedDirection, end, length);
    }

    static @NotNull RayCursor of(@NotNull Vector3dc origin, @NotNull Vector3dc end) {
        Vector3d direction = end.sub(origin, new Vector3d()).normalize();
        double length = origin.distance(end);
        return new RayCursorImpl(origin, direction, end, length);
    }

    @NotNull Vector3dc origin();

    @NotNull Vector3dc direction();

    @NotNull Vector3dc end();

    double length();

    @Override
    default @Nullable Intersection intersectPoint(@NotNull Vector3dc position, double threshold) {
        Vector3dc cursorOrigin = origin();
        Vector3dc cursorEnd = end();
        Vector3d cursorPoint = new Vector3d();
        Intersectiond.findClosestPointOnLineSegment(
                cursorOrigin.x(),
                cursorOrigin.y(),
                cursorOrigin.z(),
                cursorEnd.x(),
                cursorEnd.y(),
                cursorEnd.z(),
                position.x(),
                position.y(),
                position.z(),
                cursorPoint);

        double distanceSquared = cursorPoint.distanceSquared(position);
        if (distanceSquared <= threshold * threshold) {
            return new SimpleIntersection(cursorPoint, new Vector3d(position));
        } else {
            return null;
        }
    }

    @Override
    default @Nullable Intersection intersectLine(@NotNull Vector3dc start, @NotNull Vector3dc end, double threshold) {
        Vector3dc cursorOrigin = origin();
        Vector3dc cursorEnd = end();
        Vector3d cursorPoint = new Vector3d();
        Vector3d linePoint = new Vector3d();
        double distanceSquared = Intersectiond.findClosestPointsLineSegments(
                cursorOrigin.x(),
                cursorOrigin.y(),
                cursorOrigin.z(),
                cursorEnd.x(),
                cursorEnd.y(),
                cursorEnd.z(),
                start.x(),
                start.y(),
                start.z(),
                end.x(),
                end.y(),
                end.z(),
                cursorPoint,
                linePoint);

        if (distanceSquared <= threshold * threshold) {
            return new SimpleIntersection(cursorPoint, linePoint);
        } else {
            return null;
        }
    }

    @Override
    default @Nullable Intersection intersectPlane(@NotNull Vector3dc point, @NotNull Vector3dc normal) {
        return intersectPlane(point, normal, length());
    }

    @Override
    default @Nullable Intersection intersectPlane(@NotNull Vector3dc point, @NotNull Vector3dc normal, double range) {
        double t = Intersectiond.intersectRayPlane(
                origin(),
                direction(),
                point,
                normal,
                0.1);
        if (t < 0) {
            // didn't hit the front, try the back
            t = Intersectiond.intersectRayPlane(
                    origin(),
                    direction(),
                    point,
                    normal.negate(new Vector3d()),
                    0.1);
        }

        if (t >= 0 && t <= range) {
            return new SimpleIntersection(origin().fma(t, direction(), new Vector3d()));
        } else {
            return null;
        }
    }

    @Override
    default @Nullable Intersection intersectBox(@NotNull Vector3dc min, @NotNull Vector3dc max) {
        Vector2d result = new Vector2d();
        int status = Intersectiond.intersectLineSegmentAab(origin(), end(), min, max, result);
        if (status != Intersectiond.OUTSIDE) {
            return new SimpleIntersection(origin().fma(result.x, direction(), new Vector3d()));
        } else {
            return null;
        }
    }

    @Override
    default @Nullable Intersection intersectCircle(@NotNull Vector3dc center, @NotNull Vector3dc axis, double radius, double threshold) {
        Intersection intersection = intersectPlane(center, axis);
        if (intersection == null) {
            return null;
        }

        Vector3dc planePosition = intersection.positionOnTarget();
        double distance = planePosition.distance(center);
        double circleDistance = Math.abs(distance - radius);

        if (circleDistance <= threshold) {
            Vector3d circlePosition = center.lerp(planePosition, radius / distance, new Vector3d());
            return new SimpleIntersection(planePosition, circlePosition);
        } else {
            return null;
        }
    }
}
