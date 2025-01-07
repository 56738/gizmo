package me.m56738.gizmo.bukkit.display;

import me.m56738.gizmo.api.GizmoFactory;
import me.m56738.gizmo.bukkit.api.BukkitGizmos;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class DisplayGizmos implements BukkitGizmos {
    private final Plugin plugin;
    private final JOMLMapper mapper;
    private final Listener listener;

    public DisplayGizmos(Plugin plugin, JOMLMapper mapper) {
        this.plugin = plugin;
        this.mapper = mapper;
        this.listener = new DisplayListener();
        plugin.getServer().getPluginManager().registerEvents(this.listener, plugin);
    }

    public static @NotNull DisplayGizmos create(@NotNull Plugin plugin) {
        try {
            return new DisplayGizmos(plugin, new JOMLMapper());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull GizmoFactory player(@NotNull Player player) {
        return GizmoFactory.of(new DisplayCubeGizmoFactory(player, plugin, mapper));
    }

    @Override
    public void close() {
        HandlerList.unregisterAll(listener);
    }
}
