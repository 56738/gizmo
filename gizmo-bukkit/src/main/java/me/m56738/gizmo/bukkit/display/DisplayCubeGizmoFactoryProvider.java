package me.m56738.gizmo.bukkit.display;

import me.m56738.gizmo.api.GizmoFactory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface DisplayCubeGizmoFactoryProvider {
    GizmoFactory createFactory(Player player, Plugin plugin, JOMLMapper mapper);
}
