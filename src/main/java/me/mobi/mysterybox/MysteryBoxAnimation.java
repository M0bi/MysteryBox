package me.mobi.mysterybox;

import me.mobi.mysterybox.api.particle.ParticleEffect;
import me.mobi.mysterybox.api.sound.Sound;
import me.mobi.mysterybox.utils.LocationUtils;
import me.mobi.mysterybox.utils.NMSUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Class acts as the animation task for the opening of mystery boxes.
 */
public class MysteryBoxAnimation extends BukkitRunnable {

    public static final int DISPLAY_RANGE = 30;

    private final Player player;
    private MysteryBox box;
    private int remainingLevels;

    private ArmorStand armorStand;
    private ArmorStand levelStand;

    private Location loc;
    private Location chestLocation;

    private int tick;
    private double t;
    private double r;

    /**
     * Constructor for the class.
     *
     * @param box mystery box we are playing the animation.
     * @param player player opening the mystery box.
     * @param loc location of the mystery box (ender chest).
     */
    public MysteryBoxAnimation(MysteryBox box, Player player,  Location loc) {
        this.chestLocation = LocationUtils.get(loc, 0.5D, 0.0D, 0.5D);
        this.box = box;
        this.remainingLevels = box.getLevel();
        this.player = player;
        this.loc = LocationUtils.get(loc, 0.5D, 4.0D, 0.5D);

        this.loc.setYaw(player.getLocation().getYaw()
                + (player.getLocation().getYaw() < 0.0F ? -180.0F : 180.0F));

        this.armorStand = (ArmorStand) loc.getWorld().spawn(this.loc, ArmorStand.class);
        this.armorStand.setHelmet(new ItemStack(Material.ENDER_CHEST));
        this.armorStand.setCustomNameVisible(false);
        this.armorStand.setGravity(false);
        this.armorStand.setVisible(false);

        this.levelStand = null;

        this.tick = 0;
        this.t = 0;
        this.r = 1;
    }

    /**
     * Will play the animation of the Mystery Box opening.
     *
     * Tick 1: Display chest in an open state.
     * Ticks 1-80: Box moves down into chest on the y axis.
     * Ticks 80-130: Enchantment table particles for item reveal build-up.
     * Ticks 150-220: More assortment of particles for item reveal build-up.
     * Tick 220: Animated chest gets replaced with item reveal and chest opens up.
     * Ticks 220-250: Item gets revealed by slowly moving up the y axis.
     * Ticks 330+: Animation is over (armor stand is removed, chest is closed, and the runnable is closed).
     */
    public void run() {
        tick += 1;

        playSpiralPattern();

        if (tick == 1) {
            NMSUtil.playChestAction(chestLocation, true, true);
        } else if (tick > 1 && tick < 80) {
            ParticleEffect.FIREWORKS_SPARK.display(0.0F, 0.0F, 0.0F, 1.0F, 1,
                    armorStand.getEyeLocation(), DISPLAY_RANGE);
            ParticleEffect.REDSTONE.display(0.0F, 0.0F, 0.0F, 1.0F, 1,
                    armorStand.getEyeLocation(), DISPLAY_RANGE);
            loc.getWorld().playSound(armorStand.getEyeLocation(), Sound.NOTE_PIANO.getSound(), 1.0F, 2.0F);
            armorStand.teleport(armorStand.getLocation().subtract(0.0D, 0.07D, 0.0D));
        } else if (tick >= 80 && tick < 130) {
            NMSUtil.playChestAction(chestLocation, true, false);
            ParticleEffect.ENCHANTMENT_TABLE.display(0.0F, 0.0F, 0.0F, 1.0F, 60,
                    armorStand.getEyeLocation().add(0.0D, 1.0D, 0.0D), DISPLAY_RANGE);
        } else if (tick >= 150 && tick < 220) {
            ParticleEffect.CRIT_MAGIC.display(0.0F, 0.0F, 0.0F, 1.0F, 5,
                    chestLocation, DISPLAY_RANGE);
            ParticleEffect.EXPLOSION_NORMAL.display(1.0F, 0.5F, 1.0F, 0.0F, 1,
                    chestLocation, DISPLAY_RANGE);
        } else if (tick == 220) {
            NMSUtil.playChestAction(chestLocation, true, true);
            armorStand.setHelmet(new ItemStack(Material.DIAMOND_SWORD));
        } else if (tick > 220 && tick < 250) {
            armorStand.teleport(armorStand.getLocation().add(0.0D, 0.02D, 0.0D));
        } else if (tick >= 310) {
            armorStand.remove();
            NMSUtil.playChestAction(chestLocation, true, false);
            cancel();
        }
    }

    /**
     * Method deals with the math needed to play the spiral effect around the chest.
     */
    public void playSpiralPattern() {
        if (tick >= 150 && t <= Math.PI * 4) {
            t = t + Math.PI / 16;
            double x = r * Math.cos(t);
            double y = t * 0.25;
            double z = r * Math.sin(t);
            ParticleEffect.VILLAGER_HAPPY.display(0.0F, 0.0F, 0.0F, 0.0F, 1,
                    chestLocation.clone().add(x, y, z), DISPLAY_RANGE);
        }
    }
}
