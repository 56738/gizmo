package me.m56738.gizmo.bukkit.viaversion.entityid;

import me.m56738.gizmo.bukkit.util.ReflectionUtil;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

@ApiStatus.Internal
public interface EntityIdProvider {
    static @NotNull EntityIdProvider create() throws Throwable {
        String nms = ReflectionUtil.nms();
        Class<?> entityClass = Class.forName(nms + ".Entity");
        Field field;
        try {
            field = entityClass.getDeclaredField("entityCount");
        } catch (NoSuchFieldException e) {
            field = entityClass.getDeclaredField("ENTITY_COUNTER");
        }

        field.setAccessible(true);

        if (field.getType().equals(AtomicInteger.class)) {
            return new AtomicEntityIdProvider((AtomicInteger) field.get(null));
        } else {
            return new LegacyEntityIdProvider(field);
        }
    }

    int nextEntityId();
}
