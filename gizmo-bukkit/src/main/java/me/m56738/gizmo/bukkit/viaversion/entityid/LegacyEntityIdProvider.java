package me.m56738.gizmo.bukkit.viaversion.entityid;

import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Field;

@ApiStatus.Internal
public class LegacyEntityIdProvider implements EntityIdProvider {
    private final Field counter;

    public LegacyEntityIdProvider(Field counter) {
        this.counter = counter;
    }

    @Override
    public int nextEntityId() {
        try {
            int id = counter.getInt(null);
            counter.setInt(null, id + 1);
            return id;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
