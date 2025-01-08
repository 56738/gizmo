package me.m56738.gizmo.bukkit.viaversion.cube;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import me.m56738.gizmo.cube.CubeGizmo;
import me.m56738.gizmo.api.GizmoColor;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApiStatus.Internal
public abstract class ViaCubeGizmo implements CubeGizmo {
    private final GizmoTracker tracker;
    private final List<EntityData> changedData = new ArrayList<>();
    private final Vector3d position = new Vector3d();
    private final Vector3d offset = new Vector3d();
    private final Quaterniond rotation = new Quaterniond();
    private final Vector3d scale = new Vector3d(DEFAULT_SCALE);
    private GizmoColor color = DEFAULT_COLOR;
    private boolean billboard = DEFAULT_BILLBOARD;
    private boolean visible;
    private boolean positionChanged;
    private boolean offsetChanged;
    private boolean rotationChanged;
    private boolean scaleChanged;
    private boolean colorChanged;
    private boolean billboardChanged;

    protected ViaCubeGizmo(UserConnection connection) {
        this.tracker = GizmoTracker.tracker(connection);
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return position;
    }

    @Override
    public void setPosition(@NotNull Vector3dc position) {
        if (!Objects.equals(this.position, position)) {
            this.position.set(position);
            this.positionChanged = true;
        }
    }

    @Override
    public @NotNull Vector3dc getOffset() {
        return offset;
    }

    @Override
    public void setOffset(@NotNull Vector3dc offset) {
        if (!Objects.equals(this.offset, offset)) {
            this.offset.set(offset);
            this.offsetChanged = true;
        }
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return rotation;
    }

    @Override
    public void setRotation(@NotNull Quaterniondc rotation) {
        if (!Objects.equals(this.rotation, rotation)) {
            this.rotation.set(rotation);
            this.rotationChanged = true;
        }
    }

    @Override
    public @NotNull Vector3dc getScale() {
        return scale;
    }

    @Override
    public void setScale(@NotNull Vector3dc scale) {
        if (!Objects.equals(this.scale, scale)) {
            this.scale.set(scale);
            this.scaleChanged = true;
        }
    }

    @Override
    public @NotNull GizmoColor getColor() {
        return color;
    }

    @Override
    public void setColor(@NotNull GizmoColor color) {
        if (!Objects.equals(this.color, color)) {
            this.color = color;
            this.colorChanged = true;
        }
    }

    @Override
    public boolean isBillboard() {
        return billboard;
    }

    @Override
    public void setBillboard(boolean billboard) {
        if (!Objects.equals(this.billboard, billboard)) {
            this.billboard = billboard;
            this.billboardChanged = true;
        }
    }

    @Override
    public void show() {
        if (visible) {
            return;
        }
        visible = true;
        tracker.add(this);

        changedData.clear();
        addInitialData(changedData);

        sendAddEntity();
        sendData(changedData);
    }

    @Override
    public void update() {
        if (!visible) {
            return;
        }

        if (positionChanged) {
            positionChanged = false;
            sendPosition();
        }

        changedData.clear();

        if (offsetChanged) {
            offsetChanged = false;
            addOffsetData(changedData);
        }

        if (rotationChanged) {
            rotationChanged = false;
            addRotationData(changedData);
        }

        if (colorChanged) {
            colorChanged = false;
            addColorData(changedData);
        }

        if (scaleChanged) {
            scaleChanged = false;
            addScaleData(changedData);
        }

        if (billboardChanged) {
            billboardChanged = false;
            addBillboardData(changedData);
        }

        if (!changedData.isEmpty()) {
            addUpdateData(changedData);
            sendData(changedData);
            changedData.clear();
        }
    }

    @Override
    public void hide() {
        if (!visible) {
            return;
        }
        visible = false;
        tracker.remove(this);
        sendRemoveEntity();
    }

    protected abstract void sendAddEntity();

    protected abstract void sendPosition();

    protected abstract void sendData(List<EntityData> data);

    protected abstract void sendRemoveEntity();

    protected abstract void addInitialData(List<EntityData> data);

    protected abstract void addUpdateData(List<EntityData> data);

    protected abstract void addOffsetData(List<EntityData> data);

    protected abstract void addRotationData(List<EntityData> data);

    protected abstract void addScaleData(List<EntityData> data);

    protected abstract void addColorData(List<EntityData> data);

    protected abstract void addBillboardData(List<EntityData> data);

    public void remove() {
        visible = false;
    }
}
