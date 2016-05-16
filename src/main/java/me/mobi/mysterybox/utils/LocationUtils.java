package me.mobi.mysterybox.utils;

import org.bukkit.Location;

/**
 * Class of static location utility methods.
 */
public class LocationUtils {

    /**
     * Returns a new Location with adjusted x, y, and z values.
     *
     * @param loc location to copy.
     * @param x value to add to location's x.
     * @param y value to add to location's y.
     * @param z value to add to location's z.
     * @return a new location with adjusted values.
     */
    public static Location get(Location loc, double x, double y, double z) {
        return new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
    }
}
