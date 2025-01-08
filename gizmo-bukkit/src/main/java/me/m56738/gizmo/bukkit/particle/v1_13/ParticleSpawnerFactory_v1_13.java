package me.m56738.gizmo.bukkit.particle.v1_13;

import me.m56738.gizmo.api.GizmoColor;
import me.m56738.gizmo.bukkit.particle.ParticleSpawner;
import me.m56738.gizmo.bukkit.particle.ParticleSpawnerFactory;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

@ApiStatus.Internal
public class ParticleSpawnerFactory_v1_13 implements ParticleSpawnerFactory {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public ParticleSpawnerFactory_v1_13() throws Throwable {
        Particle.valueOf("REDSTONE");
        Particle.DustOptions.class.getConstructor(Color.class, float.class);
        Player.class.getMethod("spawnParticle", Particle.class, double.class, double.class, double.class, int.class, Object.class);
    }

    @Override
    public @NotNull ParticleSpawner createSpawner(@NotNull Player player) {
        return new Spawner(player);
    }

    private static class Spawner implements ParticleSpawner {
        private final Player player;

        private Spawner(Player player) {
            this.player = player;
        }

        @Override
        public void spawnParticle(Vector3dc position, GizmoColor color, double size) {
            Color bukkitColor = Color.fromRGB(color.getRGB());
            player.spawnParticle(
                    Particle.REDSTONE,
                    position.x(), position.y(), position.z(),
                    1,
                    new Particle.DustOptions(bukkitColor, 0.5f));
        }

        @Override
        public double getDensity(double size) {
            return 5;
        }
    }
}
