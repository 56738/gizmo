package me.m56738.gizmo.bukkit.display;

import me.m56738.gizmo.api.CubeGizmo;
import me.m56738.gizmo.api.CubeGizmoFactory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class DisplayCubeGizmoFactory implements CubeGizmoFactory {
    private final Player player;
    private final Plugin plugin;
    private final JOMLMapper mapper;

    public DisplayCubeGizmoFactory(Player player, Plugin plugin, JOMLMapper mapper) {
        this.player = player;
        this.plugin = plugin;
        this.mapper = mapper;
    }

    @Override
    public @NotNull CubeGizmo createCube() {
        return new DisplayCubeGizmo(player, plugin, mapper);
    }

    @Override
    public boolean isVisibleThroughWalls() {
        return true;
    }
}
