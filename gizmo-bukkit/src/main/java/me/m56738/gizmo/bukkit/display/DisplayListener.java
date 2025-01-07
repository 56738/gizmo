package me.m56738.gizmo.bukkit.display;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTeleportEvent;

public class DisplayListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSpawn(EntitySpawnEvent event) {
        if (event.getEntity().hasMetadata("gizmo")) {
            event.setCancelled(false);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTeleport(EntityTeleportEvent event) {
        if (event.getEntity().hasMetadata("gizmo")) {
            event.setCancelled(false);
        }
    }
}
