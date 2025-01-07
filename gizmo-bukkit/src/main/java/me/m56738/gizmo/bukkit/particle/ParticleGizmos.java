package me.m56738.gizmo.bukkit.particle;

import me.m56738.gizmo.api.GizmoFactory;
import me.m56738.gizmo.bukkit.api.BukkitGizmos;
import me.m56738.gizmo.bukkit.particle.v1_13.ParticleSpawnerFactory_v1_13;
import me.m56738.gizmo.bukkit.particle.v1_8.ParticleSpawnerFactory_v1_8;
import me.m56738.gizmo.bukkit.particle.v1_8.SpigotParticleSpawnerFactory_v1_8;
import me.m56738.gizmo.bukkit.particle.v1_9.ParticleSpawnerFactory_v1_9;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class ParticleGizmos implements BukkitGizmos {
    private static final List<ParticleSpawnerFactoryProvider> PROVIDERS = Arrays.asList(
            ParticleSpawnerFactory_v1_13::new,
            ParticleSpawnerFactory_v1_9::new,
            SpigotParticleSpawnerFactory_v1_8::new,
            ParticleSpawnerFactory_v1_8::new);

    private final ParticleSpawnerFactory particleSpawnerFactory;

    ParticleGizmos(ParticleSpawnerFactory particleSpawnerFactory) {
        this.particleSpawnerFactory = particleSpawnerFactory;
    }

    public static @NotNull ParticleGizmos create() {
        RuntimeException exception = new RuntimeException("No supported particle spawner found");

        for (ParticleSpawnerFactoryProvider provider : PROVIDERS) {
            ParticleSpawnerFactory factory;
            try {
                factory = provider.createFactory();
            } catch (Throwable e) {
                exception.addSuppressed(e);
                continue;
            }
            return new ParticleGizmos(factory);
        }

        throw exception;
    }

    @Override
    public @NotNull GizmoFactory player(@NotNull Player player) {
        return new ParticleGizmoFactory(particleSpawnerFactory.createSpawner(player));
    }

    @Override
    public void close() {
    }
}
