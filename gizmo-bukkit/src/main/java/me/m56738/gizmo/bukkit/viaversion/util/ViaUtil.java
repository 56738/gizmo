package me.m56738.gizmo.bukkit.viaversion.util;

import com.viaversion.viaversion.api.minecraft.Quaternion;
import com.viaversion.viaversion.api.minecraft.Vector3f;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public final class ViaUtil {
    private ViaUtil() {
    }

    public static Vector3f convert(Vector3dc value) {
        return new Vector3f(
                (float) value.x(),
                (float) value.y(),
                (float) value.z());
    }

    public static Quaternion convert(Quaterniondc value) {
        return new Quaternion(
                (float) value.x(),
                (float) value.y(),
                (float) value.z(),
                (float) value.w());
    }
}
