package me.m56738.gizmo.bukkit.viaversion.v1_13;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import me.m56738.gizmo.api.GizmoColor;
import me.m56738.gizmo.bukkit.particle.ParticleSpawner;
import me.m56738.gizmo.bukkit.viaversion.cube.GizmoTracker;
import org.bukkit.Color;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Vector3dc;

@ApiStatus.Internal
public class ViaParticleSpawner_v1_13 implements ParticleSpawner {
    private final VersionedPacketTransformer<ClientboundPackets1_13, ?> transformer;
    private final UserConnection connection;

    public ViaParticleSpawner_v1_13(VersionedPacketTransformer<ClientboundPackets1_13, ?> transformer, UserConnection connection) {
        this.transformer = transformer;
        this.connection = connection;
        GizmoTracker.tracker(connection);
    }

    @Override
    public void spawnParticle(Vector3dc position, GizmoColor color, double size) {
        if (!connection.has(GizmoTracker.class)) {
            return;
        }

        Color bukkitColor = Color.fromRGB(color.getRGB());
        transformer.scheduleSend(connection, ClientboundPackets1_13.LEVEL_PARTICLES, wrapper -> {
            wrapper.write(Types.INT, 11);
            wrapper.write(Types.BOOLEAN, true); // long distance
            wrapper.write(Types.FLOAT, (float) position.x());
            wrapper.write(Types.FLOAT, (float) position.y());
            wrapper.write(Types.FLOAT, (float) position.z());
            wrapper.write(Types.FLOAT, 0f); // offset
            wrapper.write(Types.FLOAT, 0f);
            wrapper.write(Types.FLOAT, 0f);
            wrapper.write(Types.FLOAT, 0f); // data
            wrapper.write(Types.INT, 1);
            wrapper.write(Types.FLOAT, bukkitColor.getRed() / 255f);
            wrapper.write(Types.FLOAT, bukkitColor.getGreen() / 255f);
            wrapper.write(Types.FLOAT, bukkitColor.getBlue() / 255f);
            wrapper.write(Types.FLOAT, 0.5f);
        });
    }

    @Override
    public double getDensity(double size) {
        return 5;
    }
}
