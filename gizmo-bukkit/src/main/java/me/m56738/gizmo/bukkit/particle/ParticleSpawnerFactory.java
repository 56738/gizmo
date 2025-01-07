package me.m56738.gizmo.bukkit.particle;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface ParticleSpawnerFactory {
    @NotNull ParticleSpawner createSpawner(@NotNull Player player);
}
