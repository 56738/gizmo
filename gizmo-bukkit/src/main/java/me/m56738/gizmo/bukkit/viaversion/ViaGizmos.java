package me.m56738.gizmo.bukkit.viaversion;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ClientboundPackets1_20_2;
import me.m56738.gizmo.api.GizmoFactory;
import me.m56738.gizmo.bukkit.api.BukkitGizmos;
import me.m56738.gizmo.bukkit.particle.ParticleGizmoFactory;
import me.m56738.gizmo.bukkit.viaversion.cube.GizmoTracker;
import me.m56738.gizmo.bukkit.viaversion.entityid.EntityIdProvider;
import me.m56738.gizmo.bukkit.viaversion.v1_13.ViaParticleSpawner_v1_13;
import me.m56738.gizmo.bukkit.viaversion.v1_19_4.ViaCubeGizmoFactory_v1_19_4;
import me.m56738.gizmo.bukkit.viaversion.v1_20_2.ViaCubeGizmoFactory_v1_20_2;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public class ViaGizmos implements BukkitGizmos {
    private static final int MAJOR_VERSION = 5;
    private static @Nullable EntityIdProvider ID_PROVIDER;

    static {
        try {
            ID_PROVIDER = EntityIdProvider.create();
        } catch (Throwable ignored) {
        }
    }

    private final BukkitGizmos fallback;
    private final VersionedPacketTransformer<ClientboundPackets1_13, ?> transformer_v1_13;
    private final VersionedPacketTransformer<ClientboundPackets1_19_4, ?> transformer_v1_19_4;
    private final VersionedPacketTransformer<ClientboundPackets1_20_2, ?> transformer_v1_20_2;

    private ViaGizmos(BukkitGizmos fallback) {
        this.fallback = fallback;
        this.transformer_v1_13 = Via.getManager().getProtocolManager().createPacketTransformer(ProtocolVersion.v1_13, ClientboundPackets1_13.class, null);
        this.transformer_v1_19_4 = Via.getManager().getProtocolManager().createPacketTransformer(ProtocolVersion.v1_19_4, ClientboundPackets1_19_4.class, null);
        this.transformer_v1_20_2 = Via.getManager().getProtocolManager().createPacketTransformer(ProtocolVersion.v1_20_2, ClientboundPackets1_20_2.class, null);
    }

    public static boolean isSupported() {
        try {
            return Via.getAPI().majorVersion() == MAJOR_VERSION;
        } catch (Throwable e) {
            return false;
        }
    }

    public static @NotNull ViaGizmos create(@NotNull BukkitGizmos fallback) {
        return new ViaGizmos(fallback);
    }

    @Override
    public @NotNull GizmoFactory player(@NotNull Player player) {
        GizmoFactory viaVersionFactory = findViaVersionFactory(player);
        if (viaVersionFactory != null) {
            return viaVersionFactory;
        } else {
            return fallback.player(player);
        }
    }

    private @Nullable GizmoFactory findViaVersionFactory(@NotNull Player player) {
        UserConnection connection = Via.getAPI().getConnection(player.getUniqueId());
        if (connection == null) {
            return null;
        }

        ProtocolInfo info = connection.getProtocolInfo();
        ProtocolVersion version = info.protocolVersion();
        ProtocolVersion serverVersion = info.serverProtocolVersion();

        if (version.newerThanOrEqualTo(ProtocolVersion.v1_20_2) && serverVersion.olderThan(ProtocolVersion.v1_20_2) && ID_PROVIDER != null) {
            // use display entities with teleport interpolation
            return new ViaCubeGizmoFactory_v1_20_2(transformer_v1_20_2, connection, ID_PROVIDER);
        }

        if (version.newerThanOrEqualTo(ProtocolVersion.v1_19_4) && serverVersion.olderThan(ProtocolVersion.v1_19_4) && ID_PROVIDER != null) {
            // use display entities without teleport interpolation
            return new ViaCubeGizmoFactory_v1_19_4(transformer_v1_19_4, connection, ID_PROVIDER);
        }

        if (version.newerThanOrEqualTo(ProtocolVersion.v1_13) && serverVersion.olderThan(ProtocolVersion.v1_13)) {
            // use modern particles
            return new ParticleGizmoFactory(new ViaParticleSpawner_v1_13(transformer_v1_13, connection));
        }

        return null;
    }

    @Override
    public void close() {
        for (UserConnection connection : Via.getManager().getConnectionManager().getConnections()) {
            GizmoTracker tracker = connection.get(GizmoTracker.class);
            if (tracker != null) {
                tracker.hideAll();
            }
            connection.remove(GizmoTracker.class);
        }
    }
}
