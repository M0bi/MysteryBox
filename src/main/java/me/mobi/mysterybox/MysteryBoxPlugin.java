package me.mobi.mysterybox;

import me.mobi.mysterybox.events.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This plugin is currently a proof of concept with a fully functioning MysteryBox
 * animation, conceptually the same as Hypixel's but with a different (and cooler)
 * opening animation. This plugin can easily be configured to support whatever
 * sort of crate/mystery box system that needs implementation.
 *
 * @author M0bi
 * @version Proof of concept (indev)
 */
public class MysteryBoxPlugin extends JavaPlugin {

    private static MysteryBoxPlugin instance;

    /**
     * Invoked when the plugin gets enabled by the server.
     */
    public void onEnable() {
        instance = this;

        new PlayerListener();

        getCommand("test").setExecutor(new TestCommand());
    }

    /**
     * Invoked when the plugin gets disabled by the server.
     */
    public void onDisable() {

    }

    /**
     * Allows for easy access to this instance across all source files.
     *
     * @return the main instance of this class.
     */
    public static MysteryBoxPlugin getInstance() {
        return instance;
    }
}
