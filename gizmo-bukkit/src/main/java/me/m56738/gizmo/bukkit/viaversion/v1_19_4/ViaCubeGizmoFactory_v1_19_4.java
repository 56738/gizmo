package me.m56738.gizmo.bukkit.viaversion.v1_19_4;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ClientboundPackets1_19_4;
import me.m56738.gizmo.cube.CubeGizmo;
import me.m56738.gizmo.cube.CubeGizmoFactory;
import me.m56738.gizmo.bukkit.viaversion.entityid.EntityIdProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public class ViaCubeGizmoFactory_v1_19_4 implements CubeGizmoFactory {
    private final VersionedPacketTransformer<ClientboundPackets1_19_4, ?> transformer;
    private final UserConnection connection;
    private final EntityIdProvider entityIdProvider;

    public ViaCubeGizmoFactory_v1_19_4(VersionedPacketTransformer<ClientboundPackets1_19_4, ?> transformer, UserConnection connection, EntityIdProvider entityIdProvider) {
        this.transformer = transformer;
        this.connection = connection;
        this.entityIdProvider = entityIdProvider;
    }

    @Override
    public @NotNull CubeGizmo createCube() {
        return new ViaCubeGizmo_v1_19_4(transformer, connection, entityIdProvider.nextEntityId());
    }

    @Override
    public boolean isVisibleThroughWalls() {
        return true;
    }
}
