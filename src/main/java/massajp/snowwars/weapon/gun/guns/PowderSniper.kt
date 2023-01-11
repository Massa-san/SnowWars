package massajp.snowwars.weapon.gun.guns

import massajp.snowwars.SnowWars
import massajp.snowwars.weapon.gun.GunWeapon
import org.bukkit.*
import org.bukkit.entity.Arrow
import org.bukkit.entity.Damageable
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Snowball
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector


class PowderSniper : GunWeapon {
    override val time: Long = 50
    override val damage: Double = 20.0

    override fun launch(player: Player) {
        val ballet = player.launchProjectile(Arrow::class.java)
        ballet.shooter = player
        ballet.customName = "PowderSniper"
        ballet.velocity = player.location.direction.multiply(10)

        /*// Player's eye location is the starting location for the particle
        // Player's eye location is the starting location for the particle
        val startLoc: Location = player.eyeLocation

        // We need to clone() this location, because we will add() to it later.

        // We need to clone() this location, because we will add() to it later.
        val particleLoc: Location = startLoc.clone()

        val world: World = startLoc.world!! // We need this later to show the particle


        // dir is the Vector direction (offset from 0,0,0) the player is facing in 3D space

        // dir is the Vector direction (offset from 0,0,0) the player is facing in 3D space
        val dir: Vector = startLoc.direction

        /* vecOffset is used to determine where the next particle should appear
    We are taking the direction and multiplying it by 0.5 to make it appear 1/2 block
      in its continuing Vector direction.
    NOTE: We have to clone() because multiply() modifies the original variable!
    For a straight beam, we only need to calculate this once, as the direction does not change.
    */

        /* vecOffset is used to determine where the next particle should appear
    We are taking the direction and multiplying it by 0.5 to make it appear 1/2 block
      in its continuing Vector direction.
    NOTE: We have to clone() because multiply() modifies the original variable!
    For a straight beam, we only need to calculate this once, as the direction does not change.
    */
        val vecOffset: Vector = dir.clone().multiply(10)

        object : BukkitRunnable() {
            var maxBeamLength = 100 * 2 // Max beam length
            var beamLength = 0 // Current beam length

            // The run() function runs every X number of ticks - see below
            override fun run() {
                // Search for any entities near the particle's current location
                for (entity in world.getNearbyEntities(particleLoc, 5.0, 5.0, 5.0)) {
                    // We only care about living entities. Any others will be ignored.
                    if (entity is LivingEntity) {
                        // Ignore player that initiated the shot
                        if (entity === player) {
                            continue
                        }

                        /* Define the bounding box of the particle.
                    We will use 0.25 here, since the particle is moving 0.5 blocks each time.
                    That means the particle won't miss very small entities like chickens or bats,
                      as the particle bounding box covers 1/2 of the movement distance.
                     */
                        val particleMinVector = Vector(
                                particleLoc.x - 0.25,
                                particleLoc.y - 0.25,
                                particleLoc.z - 0.25)
                        val particleMaxVector = Vector(
                                particleLoc.x + 0.25,
                                particleLoc.y + 0.25,
                                particleLoc.z + 0.25)

                        // Now use a spigot API call to determine if the particle is inside the entity's hitbox
                        if (entity.getBoundingBox().overlaps(particleMinVector, particleMaxVector)) {
                            // We have a hit!
                            // Display a flash at the location of the particle
                            world.spawnParticle(Particle.FLASH, particleLoc, 0)
                            // Play an explosion sound at the particle location
                            world.playSound(particleLoc, Sound.ENTITY_GENERIC_EXPLODE, 2f, 1f)

                            // Knock-back the entity in the same direction from where the particle is coming.
                            entity.setVelocity(entity.getVelocity().add(particleLoc.direction.normalize().multiply(30)))

                            // Damage the target, using the shooter as the damager
                            (entity as Damageable).damage(5.0, player)
                            // Cancel the particle beam
                            cancel()
                            // We must return here, otherwise the code below will display one more particle.
                            return
                        }
                    }
                }
                beamLength++ // This is the distance between each particle

                // Kill this task if the beam length is max
                if (beamLength >= maxBeamLength) {
                    world.spawnParticle(Particle.FLASH, particleLoc, 0)
                    cancel()
                    return
                }

                // Now we add the direction vector offset to the particle's current location
                particleLoc.add(vecOffset)

                // Display the particle in the new location
                world.spawnParticle(Particle.FIREWORKS_SPARK, particleLoc, 0)
            }
        }.runTaskTimer(SnowWars.instance, 0, 1)
        // 0 is the delay in ticks before starting this task
        // 1 is the how often to repeat the run() function, in ticks (20 ticks are in one second)*/
    }



    override fun zoom(player: Player, isZoom: Boolean) {
        if (!isZoom) {
            player.walkSpeed = -0.3f
        } else {
            if (player.walkSpeed == -0.3f) {
                player.walkSpeed = 0.2f
            }
        }
    }

    override fun item(): ItemStack {
        val item = ItemStack(Material.IRON_HORSE_ARMOR)
        val meta = item.itemMeta
        meta?.setDisplayName("${ChatColor.BOLD}${ChatColor.AQUA}スノーパウダースナイパー")
        meta?.setCustomModelData(1)
        item.itemMeta = meta
        return item
    }
}