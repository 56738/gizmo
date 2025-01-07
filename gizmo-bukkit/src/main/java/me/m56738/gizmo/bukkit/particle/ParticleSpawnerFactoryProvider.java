package me.m56738.gizmo.bukkit.particle;

import org.jetbrains.annotations.NotNull;

public interface ParticleSpawnerFactoryProvider {
    @NotNull ParticleSpawnerFactory createFactory() throws Throwable;
}
