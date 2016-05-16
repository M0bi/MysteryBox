package me.mobi.mysterybox.utils;

import net.minecraft.server.v1_9_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;

public class NMSUtil {

    /**
     * Plays the chest animation to display it in an open or closed state.
     *
     * @param loc chest we want to open or close.
     * @param open true if we want to open the chest, false if we want to close the chest.
     */
    public static void playChestAction(Location loc, boolean enderChest, boolean open) {
        World world = ((CraftWorld)loc.getWorld()).getHandle();
        BlockPosition position = new BlockPosition(loc.getX(), loc.getY(), loc.getZ());
        if (enderChest) {
            TileEntityEnderChest tileChest = (TileEntityEnderChest) world.getTileEntity(position);
            world.playBlockAction(position, tileChest.getBlock(), 1, open ? 1 : 0);
        } else {
            TileEntityChest tileChest = (TileEntityChest) world.getTileEntity(position);
            world.playBlockAction(position, tileChest.getBlock(), 1, open ? 1 : 0);
        }
    }
}
