package me.m56738.gizmo.bukkit.display;

import org.bukkit.util.Transformation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

import java.lang.invoke.MethodHandle;

import static java.lang.invoke.MethodHandles.publicLookup;
import static java.lang.invoke.MethodType.methodType;

@ApiStatus.Internal
public final class JOMLMapper {
    private final MethodHandle createVector3f;
    private final MethodHandle createQuaternionf;
    private final MethodHandle createTransformation;

    public JOMLMapper() throws Throwable {
        String joml = String.join(".", "org", "joml");
        Class<?> vectorClass = Class.forName(joml + ".Vector3f");
        Class<?> quaternionClass = Class.forName(joml + ".Quaternionf");
        this.createVector3f = publicLookup().findConstructor(vectorClass,
                methodType(void.class, float.class, float.class, float.class));
        this.createQuaternionf = publicLookup().findConstructor(quaternionClass,
                methodType(void.class, float.class, float.class, float.class, float.class));
        this.createTransformation = publicLookup().findConstructor(Transformation.class,
                methodType(void.class, vectorClass, quaternionClass, vectorClass, quaternionClass));
    }

    public @NotNull Transformation createTransformation(
            @NotNull Vector3dc translation,
            @NotNull Quaterniondc leftRotation,
            @NotNull Vector3dc scale,
            @NotNull Quaterniondc rightRotation) {
        try {
            return (Transformation) createTransformation.invoke(
                    createVector3f(translation),
                    createQuaternionf(leftRotation),
                    createVector3f(scale),
                    createQuaternionf(rightRotation));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private Object createVector3f(Vector3dc value) throws Throwable {
        return createVector3f.invoke((float) value.x(), (float) value.y(), (float) value.z());
    }

    private Object createQuaternionf(Quaterniondc value) throws Throwable {
        return createQuaternionf.invoke((float) value.x(), (float) value.y(), (float) value.z(), (float) value.w());
    }
}
