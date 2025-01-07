package me.m56738.gizmo.bukkit.viaversion.entityid;

import java.util.concurrent.atomic.AtomicInteger;

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
