package me.m56738.gizmo.bukkit.util;

import org.bukkit.Bukkit;

public final class ReflectionUtil {
    private static final String CB_PACKAGE = Bukkit.getServer().getClass().getPackage().getName();
    private static final String NMS_PACKAGE = CB_PACKAGE.replace("org.bukkit.craftbukkit", "net.minecraft.server");

    private ReflectionUtil() {
    }

    public static boolean hasClass(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static String cb() {
        return CB_PACKAGE;
    }

    public static String nms() {
        return NMS_PACKAGE;
    }
}
