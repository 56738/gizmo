package me.m56738.gizmo.bukkit.viaversion.v1_20_2;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ClientboundPackets1_20_2;
import me.m56738.gizmo.api.CubeGizmo;
import me.m56738.gizmo.api.CubeGizmoFactory;
import me.m56738.gizmo.bukkit.viaversion.entityid.EntityIdProvider;
import org.jetbrains.annotations.NotNull;

public class ViaCubeGizmoFactory_v1_20_2 implements CubeGizmoFactory {
    private final VersionedPacketTransformer<ClientboundPackets1_20_2, ?> transformer;
    private final UserConnection connection;
    private final EntityIdProvider entityIdProvider;

    public ViaCubeGizmoFactory_v1_20_2(VersionedPacketTransformer<ClientboundPackets1_20_2, ?> transformer, UserConnection connection, EntityIdProvider entityIdProvider) {
        this.transformer = transformer;
        this.connection = connection;
        this.entityIdProvider = entityIdProvider;
    }

    @Override
    public @NotNull CubeGizmo createCube() {
        return new ViaCubeGizmo_v1_20_2(transformer, connection, entityIdProvider.nextEntityId());
    }

    @Override
    public boolean isVisibleThroughWalls() {
        return true;
    }
}
