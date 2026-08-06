package me.m56738.gizmo.bukkit.display.v1_20_2;

import me.m56738.gizmo.bukkit.display.JOMLMapper;
import me.m56738.gizmo.bukkit.display.v1_19_4.DisplayCubeGizmo_v1_19_4;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@ApiStatus.Internal
public class DisplayCubeGizmo_v1_20_2 extends DisplayCubeGizmo_v1_19_4 {
    private @Nullable BlockData lastBlock;
    private @Nullable Transformation lastTransformation;
    private @Nullable Display.Billboard lastBillboard;

    public DisplayCubeGizmo_v1_20_2(Player player, Plugin plugin, JOMLMapper mapper) {
        super(player, plugin, mapper);
    }

    public static boolean isSupported() {
        try {
            BlockDisplay.class.getMethod("setInterpolationDuration", int.class);
            BlockDisplay.class.getMethod("setTeleportDuration", int.class);
            BlockDisplay.class.getMethod("setInterpolationDelay", int.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    protected @NotNull BlockDisplay spawn(@NotNull World world, @NotNull Location location, @NotNull Consumer<BlockDisplay> consumer) {
        return world.spawn(location, BlockDisplay.class, consumer);
    }

    @Override
    protected void configure(@NotNull BlockDisplay entity) {
        super.configure(entity);
        entity.setInterpolationDuration(3);
        entity.setTeleportDuration(3);
    }

    @Override
    protected void update(@NotNull BlockDisplay entity) {
        super.update(entity);

        BlockData block = entity.getBlock();
        Transformation transformation = entity.getTransformation();
        Display.Billboard billboard = entity.getBillboard();

        if (!block.equals(lastBlock)
                || !transformation.equals(lastTransformation)
                || !billboard.equals(lastBillboard)) {
            entity.setInterpolationDelay(0);
            lastBlock = block;
            lastTransformation = transformation;
            lastBillboard = billboard;
        }
    }
}
