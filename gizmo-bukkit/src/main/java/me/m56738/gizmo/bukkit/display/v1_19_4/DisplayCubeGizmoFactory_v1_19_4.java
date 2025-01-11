package me.m56738.gizmo.bukkit.display.v1_19_4;

import me.m56738.gizmo.bukkit.display.JOMLMapper;
import me.m56738.gizmo.cube.CubeGizmo;
import me.m56738.gizmo.cube.CubeGizmoFactory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public class DisplayCubeGizmoFactory_v1_19_4 implements CubeGizmoFactory {
    private final Player player;
    private final Plugin plugin;
    private final JOMLMapper mapper;

    public DisplayCubeGizmoFactory_v1_19_4(Player player, Plugin plugin, JOMLMapper mapper) {
        this.player = player;
        this.plugin = plugin;
        this.mapper = mapper;
    }

    @Override
    public @NotNull CubeGizmo createCube() {
        return new DisplayCubeGizmo_v1_19_4(player, plugin, mapper);
    }

    @Override
    public boolean isVisibleThroughWalls() {
        return true;
    }
}
