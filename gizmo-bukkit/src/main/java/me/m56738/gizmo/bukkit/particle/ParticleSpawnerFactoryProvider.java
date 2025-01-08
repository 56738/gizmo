package me.m56738.gizmo.bukkit.particle;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public interface ParticleSpawnerFactoryProvider {
    @NotNull ParticleSpawnerFactory createFactory() throws Throwable;
}
