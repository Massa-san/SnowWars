package massajp.snowwars.weapon.gun.guns

import massajp.snowwars.SnowWars
import org.bukkit.Bukkit
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.ProjectileHitEvent

class LandEvent (instance: SnowWars) : Listener {

    init {
        instance.server.pluginManager.registerEvents(this, instance)
    }

    @EventHandler
    fun onHit(event: ProjectileHitEvent) {
        if (event.entity.shooter !is Player) return
        if (event.hitEntity !is Player) return
        val entity = event.hitEntity as Player
        when {
            
        }
        entity.damage(SnowWars.gunManager.getPlayerGunDamage(event.entity.shooter as Player)!!)
        event.isCancelled = true
    }
}