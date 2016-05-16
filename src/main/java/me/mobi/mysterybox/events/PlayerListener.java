package me.mobi.mysterybox.events;

import me.mobi.mysterybox.MysteryBoxPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

/**
 * Handles all player related events.
 */
public class PlayerListener implements Listener {

    public PlayerListener() {
        Bukkit.getPluginManager().registerEvents(this, MysteryBoxPlugin.getInstance());
    }

    /**
     * This event will cancel any interaction with armor stands in order to avoid people from
     * right clicking the floating block during mystery box animation.
     *
     * @param event event being called by.
     */
    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.ARMOR_STAND) {
            event.setCancelled(true);
        }
    }
}
