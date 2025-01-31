package me.m56738.gizmo.bukkit.particle;

import me.m56738.gizmo.AbstractPointGizmo;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Vector3d;

@ApiStatus.Internal
public class ParticlePointGizmo extends AbstractPointGizmo {
    private final ParticleSpawner particleSpawner;
    private boolean visible;

    public ParticlePointGizmo(ParticleSpawner particleSpawner) {
        this.particleSpawner = particleSpawner;
    }

    @Override
    public void show() {
        visible = true;
    }

    @Override
    public void update() {
        if (visible) {
            particleSpawner.spawnParticle(getPosition().add(getOffset(), new Vector3d()), getColor(), getSize());
        }
    }

    @Override
    public void hide() {
        visible = false;
    }
}
