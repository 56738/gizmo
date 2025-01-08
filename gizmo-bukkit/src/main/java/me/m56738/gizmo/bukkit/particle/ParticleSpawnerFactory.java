package me.m56738.gizmo.bukkit.particle;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public interface ParticleSpawnerFactory {
    @NotNull ParticleSpawner createSpawner(@NotNull Player player);
}
