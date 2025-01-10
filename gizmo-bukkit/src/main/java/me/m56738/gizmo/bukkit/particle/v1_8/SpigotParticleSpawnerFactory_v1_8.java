package me.m56738.gizmo.bukkit.particle.v1_8;

import me.m56738.gizmo.api.color.GizmoColor;
import me.m56738.gizmo.bukkit.particle.ParticleSpawner;
import me.m56738.gizmo.bukkit.particle.ParticleSpawnerFactory;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

import java.lang.invoke.MethodHandle;

import static java.lang.invoke.MethodHandles.publicLookup;
import static java.lang.invoke.MethodType.methodType;

@ApiStatus.Internal
public class SpigotParticleSpawnerFactory_v1_8 implements ParticleSpawnerFactory {
    private final MethodHandle playEffect;
    private final Effect effect;

    public SpigotParticleSpawnerFactory_v1_8() throws Throwable {
        Class<?> spigotType = Class.forName("org.bukkit.entity.Player$Spigot");
        this.playEffect = publicLookup().findVirtual(spigotType, "playEffect", methodType(void.class,
                Location.class,
                Effect.class, int.class, int.class,
                float.class, float.class, float.class,
                float.class, int.class, int.class));
        this.effect = Effect.valueOf("COLOURED_DUST");
    }

    @Override
    public @NotNull ParticleSpawner createSpawner(@NotNull Player player) {
        return new Spawner(player);
    }

    private class Spawner implements ParticleSpawner {
        private final Player player;

        private Spawner(Player player) {
            this.player = player;
        }

        @Override
        public void spawnParticle(Vector3dc position, GizmoColor color, double size) {
            Color bukkitColor = Color.fromRGB(color.asRGB());
            try {
                playEffect.invoke(player.spigot(),
                        new Location(player.getWorld(), position.x(), position.y(), position.z()),
                        effect, 0, 1,
                        Math.max(bukkitColor.getRed() / 255f, Float.MIN_VALUE),
                        bukkitColor.getGreen() / 255f,
                        bukkitColor.getBlue() / 255f,
                        1, 0, 64);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public double getDensity(double size) {
            return 3;
        }
    }
}
