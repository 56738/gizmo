package me.m56738.gizmo.bukkit.viaversion.v1_20_2;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_4;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ClientboundPackets1_20_2;
import me.m56738.gizmo.api.GizmoColor;
import me.m56738.gizmo.bukkit.viaversion.cube.ViaCubeGizmo;
import me.m56738.gizmo.bukkit.viaversion.util.ViaUtil;
import org.joml.Vector3dc;

import java.util.EnumMap;
import java.util.List;
import java.util.UUID;

public class ViaCubeGizmo_v1_20_2 extends ViaCubeGizmo {
    private static final EnumMap<GizmoColor, Integer> COLORS = new EnumMap<>(GizmoColor.class);

    static {
        COLORS.put(GizmoColor.WHITE, 12728);
        COLORS.put(GizmoColor.RED, 12742);
        COLORS.put(GizmoColor.GREEN, 12733);
        COLORS.put(GizmoColor.BLUE, 12739);
        COLORS.put(GizmoColor.YELLOW, 12732);
        COLORS.put(GizmoColor.GRAY, 12736);
        COLORS.put(GizmoColor.AQUA, 12731);
    }

    private final VersionedPacketTransformer<ClientboundPackets1_20_2, ?> transformer;
    private final UserConnection connection;
    private final int id;
    private final UUID uuid = UUID.randomUUID();

    public ViaCubeGizmo_v1_20_2(VersionedPacketTransformer<ClientboundPackets1_20_2, ?> transformer, UserConnection connection, int id) {
        super(connection);
        this.transformer = transformer;
        this.connection = connection;
        this.id = id;
    }

    @Override
    protected void sendAddEntity() {
        Vector3dc position = getPosition();
        transformer.scheduleSend(connection, ClientboundPackets1_20_2.ADD_ENTITY, wrapper -> {
            wrapper.write(Types.VAR_INT, id);
            wrapper.write(Types.UUID, uuid);
            wrapper.write(Types.VAR_INT, EntityTypes1_19_4.BLOCK_DISPLAY.getId());
            wrapper.write(Types.DOUBLE, position.x());
            wrapper.write(Types.DOUBLE, position.y());
            wrapper.write(Types.DOUBLE, position.z());
            wrapper.write(Types.BYTE, (byte) 0);
            wrapper.write(Types.BYTE, (byte) 0);
            wrapper.write(Types.BYTE, (byte) 0);
            wrapper.write(Types.VAR_INT, 0);
            wrapper.write(Types.SHORT, (short) 0);
            wrapper.write(Types.SHORT, (short) 0);
            wrapper.write(Types.SHORT, (short) 0);
        });
    }

    @Override
    protected void sendPosition() {
        Vector3dc position = getPosition();
        transformer.scheduleSend(connection, ClientboundPackets1_20_2.TELEPORT_ENTITY, wrapper -> {
            wrapper.write(Types.VAR_INT, id);
            wrapper.write(Types.DOUBLE, position.x());
            wrapper.write(Types.DOUBLE, position.y());
            wrapper.write(Types.DOUBLE, position.z());
            wrapper.write(Types.BYTE, (byte) 0);
            wrapper.write(Types.BYTE, (byte) 0);
            wrapper.write(Types.BOOLEAN, false);
        });
    }

    @Override
    protected void sendData(List<EntityData> data) {
        transformer.scheduleSend(connection, ClientboundPackets1_20_2.SET_ENTITY_DATA, wrapper -> {
            wrapper.write(Types.VAR_INT, id);
            wrapper.write(Types1_20_2.ENTITY_DATA_LIST, data);
        });
    }

    @Override
    protected void sendRemoveEntity() {
        transformer.scheduleSend(connection, ClientboundPackets1_20_2.REMOVE_ENTITIES, wrapper -> {
            wrapper.write(Types.VAR_INT_ARRAY_PRIMITIVE, new int[]{id});
        });
    }

    @Override
    protected void addInitialData(List<EntityData> data) {
        // flags: glowing
        data.add(new EntityData(0, Types1_20_2.ENTITY_DATA_TYPES.byteType, (byte) 0x40));

        // interpolation duration: 3 ticks
        data.add(new EntityData(9, Types1_20_2.ENTITY_DATA_TYPES.varIntType, 3));

        // teleport duration: 3 ticks
        data.add(new EntityData(10, Types1_20_2.ENTITY_DATA_TYPES.varIntType, 3));

        // brightness: max
        data.add(new EntityData(16, Types1_20_2.ENTITY_DATA_TYPES.varIntType, 0x00F0_00F0));

        addOffsetData(data);
        addRotationData(data);
        addScaleData(data);
        addColorData(data);
        addBillboardData(data);
    }

    @Override
    protected void addUpdateData(List<EntityData> data) {
        // interpolation delay: start now
        data.add(new EntityData(8, Types1_20_2.ENTITY_DATA_TYPES.varIntType, 0));
    }

    @Override
    protected void addOffsetData(List<EntityData> data) {
        data.add(new EntityData(11, Types1_20_2.ENTITY_DATA_TYPES.vector3FType, ViaUtil.convert(getOffset())));
    }

    @Override
    protected void addRotationData(List<EntityData> data) {
        data.add(new EntityData(13, Types1_20_2.ENTITY_DATA_TYPES.quaternionType, ViaUtil.convert(getRotation())));
    }

    @Override
    protected void addScaleData(List<EntityData> data) {
        data.add(new EntityData(12, Types1_20_2.ENTITY_DATA_TYPES.vector3FType, ViaUtil.convert(getScale())));
    }

    @Override
    protected void addColorData(List<EntityData> data) {
        // glow color
        data.add(new EntityData(22, Types1_20_2.ENTITY_DATA_TYPES.varIntType, getColor().getRGB()));

        // block data
        data.add(new EntityData(23, Types1_20_2.ENTITY_DATA_TYPES.blockStateType, COLORS.getOrDefault(getColor(), 1)));
    }

    @Override
    protected void addBillboardData(List<EntityData> data) {
        data.add(new EntityData(15, Types1_20_2.ENTITY_DATA_TYPES.byteType, (byte) (isBillboard() ? 3 : 0)));
    }
}
