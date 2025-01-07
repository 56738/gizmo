package me.m56738.gizmo.bukkit.viaversion.cube;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GizmoTracker implements StorableObject {
    private final Set<ViaCubeGizmo> gizmos = new HashSet<>();

    public static @NotNull GizmoTracker tracker(@NotNull UserConnection connection) {
        GizmoTracker tracker = connection.get(GizmoTracker.class);
        if (tracker == null) {
            tracker = new GizmoTracker();
            connection.put(tracker);
        }
        return tracker;
    }

    @Override
    public void onRemove() {
        for (ViaCubeGizmo gizmo : gizmos) {
            gizmo.remove();
        }
    }

    public void add(ViaCubeGizmo gizmo) {
        gizmos.add(gizmo);
    }

    public void remove(ViaCubeGizmo gizmo) {
        gizmos.remove(gizmo);
    }

    public void hideAll() {
        for (ViaCubeGizmo gizmo : new ArrayList<>(gizmos)) {
            gizmo.hide();
        }
    }
}
