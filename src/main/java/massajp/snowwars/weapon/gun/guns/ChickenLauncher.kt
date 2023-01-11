package massajp.snowwars.weapon.gun.guns

import massajp.snowwars.SnowWars
import massajp.snowwars.weapon.gun.GunWeapon
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.*
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

class ChickenLauncher : GunWeapon {
    override val time: Long = 10
    override val damage: Double = 7.0

    override fun launch(player: Player) {
        val chicken: Entity = player.world.spawnEntity(player.eyeLocation, EntityType.CHICKEN)
        chicken.customName = "chicken"
        chicken.velocity = player.eyeLocation.add(0.0, 1.0, 0.0).direction.multiply(3)
        object : BukkitRunnable() {
            var tick = 0
            override fun run() {
                if (tick == 40) {
                    val location = chicken.location
                    chicken.remove()
                    location.world?.createExplosion(location, 4f, false, false)
                    cancel()
                } else if (chicken.isOnGround) {
                    val location = chicken.location
                    chicken.remove()
                    location.world?.createExplosion(location, 4f, false, false)
                    cancel()
                }
                tick++
            }
        }.runTaskTimer(SnowWars.instance, 0, 1)
    }

    override fun zoom(player: Player, isZoom: Boolean) {
        TODO("Not yet implemented")
    }

    override fun item(): ItemStack {
        val item = ItemStack(Material.IRON_SHOVEL)
        val meta = item.itemMeta
        meta?.setDisplayName("${ChatColor.BOLD}${ChatColor.AQUA}チキンランチャー")
        meta?.setCustomModelData(1)
        item.itemMeta = meta
        return item
    }
}