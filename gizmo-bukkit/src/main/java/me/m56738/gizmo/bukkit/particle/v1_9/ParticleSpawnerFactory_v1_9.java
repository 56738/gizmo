package me.m56738.gizmo.bukkit.particle.v1_9;

import me.m56738.gizmo.api.color.GizmoColor;
import me.m56738.gizmo.bukkit.particle.ParticleSpawner;
import me.m56738.gizmo.bukkit.particle.ParticleSpawnerFactory;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

@ApiStatus.Internal
public class ParticleSpawnerFactory_v1_9 implements ParticleSpawnerFactory {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public ParticleSpawnerFactory_v1_9() throws Throwable {
        Particle.valueOf("REDSTONE");
        Player.class.getMethod("spawnParticle", Particle.class, double.class, double.class, double.class, int.class, double.class, double.class, double.class, double.class);
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
            Color bukkitColor = Color.fromRGB(color.asRGB());
            player.spawnParticle(
                    Particle.REDSTONE,
                    position.x(), position.y(), position.z(),
                    0,
                    bukkitColor.getRed() / 255.0,
                    bukkitColor.getGreen() / 255.0,
                    bukkitColor.getBlue() / 255.0,
                    1);
        }

        @Override
        public double getDensity(double size) {
            return 3;
        }
    }
}
