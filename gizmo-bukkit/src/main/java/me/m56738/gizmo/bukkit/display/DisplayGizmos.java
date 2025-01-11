package me.m56738.gizmo.bukkit.display;

import me.m56738.gizmo.api.GizmoFactory;
import me.m56738.gizmo.bukkit.api.BukkitGizmos;
import me.m56738.gizmo.bukkit.display.v1_19_4.DisplayCubeGizmoFactory_v1_19_4;
import me.m56738.gizmo.bukkit.display.v1_20_2.DisplayCubeGizmoFactory_v1_20_2;
import me.m56738.gizmo.bukkit.display.v1_20_2.DisplayCubeGizmo_v1_20_2;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public class DisplayGizmos implements BukkitGizmos {
    private final Plugin plugin;
    private final JOMLMapper mapper;
    private final DisplayCubeGizmoFactoryProvider provider;
    private final Listener listener;

    private DisplayGizmos(Plugin plugin, JOMLMapper mapper, DisplayCubeGizmoFactoryProvider provider) {
        this.plugin = plugin;
        this.mapper = mapper;
        this.provider = provider;
        this.listener = new DisplayListener();
        plugin.getServer().getPluginManager().registerEvents(this.listener, plugin);
    }

    public static boolean isSupported() {
        try {
            Class.forName("org.bukkit.entity.BlockDisplay");
            new JOMLMapper();
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    public static @NotNull DisplayGizmos create(@NotNull Plugin plugin) {
        DisplayCubeGizmoFactoryProvider provider;
        if (DisplayCubeGizmo_v1_20_2.isSupported()) {
            provider = DisplayCubeGizmoFactory_v1_20_2::new;
        } else {
            provider = DisplayCubeGizmoFactory_v1_19_4::new;
        }

        try {
            return new DisplayGizmos(plugin, new JOMLMapper(), provider);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull GizmoFactory player(@NotNull Player player) {
        return provider.createFactory(player, plugin, mapper);
    }

    @Override
    public void close() {
        HandlerList.unregisterAll(listener);
    }
}
