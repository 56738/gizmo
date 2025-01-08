package me.m56738.gizmo.bukkit.viaversion.entityid;

import org.jetbrains.annotations.ApiStatus;

import java.util.concurrent.atomic.AtomicInteger;

@ApiStatus.Internal
public class AtomicEntityIdProvider implements EntityIdProvider {
    private final AtomicInteger counter;

    public AtomicEntityIdProvider(AtomicInteger counter) {
        this.counter = counter;
    }

    @Override
    public int nextEntityId() {
        return counter.incrementAndGet();
    }
}
