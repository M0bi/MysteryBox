package me.mobi.mysterybox;

import me.mobi.mysterybox.api.particle.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Test command class used for testing functions for the plugin.
 */
public class TestCommand implements CommandExecutor {

    public static final int DISPLAY_RANGE = 30;

    /**
     * Test command.
     * Currently being used to test mystery box animations.
     *
     * @param sender person executing the command.
     * @param cmd command being executed.
     * @param label label of the command.
     * @param args arguments of the command.
     * @return always true in order to avoid default return message.
     */
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (args[0].equalsIgnoreCase("one")) {
                Location loc = player.getLocation().add(1.0D, 0.0D, 0.0D);
                loc.getBlock().setType(Material.ENDER_CHEST);
                player.sendMessage("ur dad.");
                MysteryBoxPlugin.getBoxList().add(new MysteryBox(loc));
            } else if (args[0].equalsIgnoreCase("two")) {
                for (MysteryBox box : MysteryBoxPlugin.getBoxList()) {
                    box.playAnimation(player);
                }
            } else if (args[0].equalsIgnoreCase("three")) {
                final Location loc = new Location(player.getWorld(), -130.5, 82, 271.5);
                new BukkitRunnable() {
                    public void run() {
                        ParticleEffect.CRIT_MAGIC.display(0.0F, 0.0F, 0.0F, 1.0F, 5,
                                loc, DISPLAY_RANGE);
                        ParticleEffect.EXPLOSION_NORMAL.display(1.0F, 0.5F, 1.0F, 0.0F, 1,
                                loc, DISPLAY_RANGE);
                    }
                }.runTaskTimer(MysteryBoxPlugin.getInstance(), 0L, 1L);
            }
        }

        return true;
    }

}
