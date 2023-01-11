package massajp.snowwars.weapon.gun.guns

import massajp.snowwars.weapon.gun.GunWeapon
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class HookShot : GunWeapon {
    override val time: Long = 100
    override val damage: Double = 4.0

    override fun launch(player: Player) {
        val ballet = player.launchProjectile(Arrow::class.java)
        ballet.shooter = player
        ballet.customName = "HookShot"
        ballet.velocity = player.location.direction.multiply(5)
    }

    override fun zoom(player: Player, isZoom: Boolean) {
        TODO("Not yet implemented")
    }

    override fun item(): ItemStack {
        val item = ItemStack(Material.DIAMOND_SHOVEL)
        val meta = item.itemMeta
        meta?.setDisplayName("${ChatColor.BOLD}${ChatColor.AQUA}フックショット")
        meta?.setCustomModelData(1)
        item.itemMeta = meta
        return item
    }

    fun pullPlayer(player: Player) {
        player.velocity = player.eyeLocation.add(0.0, 1.0, 0.0).direction.multiply(3)
    }
}