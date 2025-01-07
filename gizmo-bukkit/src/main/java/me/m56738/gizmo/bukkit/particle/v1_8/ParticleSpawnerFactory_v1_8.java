package me.m56738.gizmo.bukkit.particle.v1_8;

import me.m56738.gizmo.api.GizmoColor;
import me.m56738.gizmo.bukkit.particle.ParticleSpawner;
import me.m56738.gizmo.bukkit.particle.ParticleSpawnerFactory;
import me.m56738.gizmo.bukkit.util.ReflectionUtil;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

import java.lang.invoke.MethodHandle;

import static java.lang.invoke.MethodHandles.publicLookup;
import static java.lang.invoke.MethodType.methodType;

public class ParticleSpawnerFactory_v1_8 implements ParticleSpawnerFactory {
    private final Object particleType;
    private final MethodHandle createPacket;
    private final MethodHandle getHandle;
    private final MethodHandle getConnection;
    private final MethodHandle sendPacket;

    public ParticleSpawnerFactory_v1_8() throws Throwable {
        String cb = ReflectionUtil.cb();
        String nms = ReflectionUtil.nms();

        Class<?> particleTypeClass = Class.forName(nms + ".EnumParticle");
        particleType = particleTypeClass.getDeclaredField("REDSTONE").get(null);

        Class<?> particlePacketClass = Class.forName(nms + ".PacketPlayOutWorldParticles");
        createPacket = publicLookup().findConstructor(particlePacketClass, methodType(
                void.class,
                particleTypeClass,
                boolean.class,
                float.class, float.class, float.class,
                float.class, float.class, float.class,
                float.class,
                int.class,
                int[].class
        ));

        Class<?> playerClass = Class.forName(cb + ".entity.CraftPlayer");
        Class<?> handleClass = Class.forName(nms + ".EntityPlayer");
        getHandle = publicLookup().findVirtual(playerClass, "getHandle", methodType(handleClass));

        Class<?> connectionClass = Class.forName(nms + ".PlayerConnection");
        getConnection = publicLookup().findGetter(handleClass, "playerConnection", connectionClass);

        Class<?> packetClass = Class.forName(nms + ".Packet");
        sendPacket = publicLookup().findVirtual(connectionClass, "sendPacket", methodType(void.class, packetClass));
    }

    public void sendParticle(Object connection, Vector3dc position, GizmoColor color) {
        Color bukkitColor = Color.fromRGB(color.getRGB());
        try {
            Object packet = createPacket.invoke(
                    particleType,
                    true,
                    (float) position.x(),
                    (float) position.y(),
                    (float) position.z(),
                    Math.max(bukkitColor.getRed() / 255f, Float.MIN_VALUE),
                    bukkitColor.getGreen() / 255f,
                    bukkitColor.getBlue() / 255f,
                    1f,
                    0);
            sendPacket.invoke(connection, packet);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public @NotNull ParticleSpawner createSpawner(@NotNull Player player) {
        try {
            Object handle = getHandle.invoke(player);
            Object connection = getConnection.invoke(handle);
            return new Spawner(connection);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private class Spawner implements ParticleSpawner {
        private final Object connection;

        private Spawner(Object connection) {
            this.connection = connection;
        }

        @Override
        public void spawnParticle(Vector3dc position, GizmoColor color, double size) {
            sendParticle(connection, position, color);
        }

        @Override
        public double getDensity(double size) {
            return 3;
        }
    }
}
