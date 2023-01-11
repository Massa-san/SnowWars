package massajp.snowwars.weapon.gun.guns

import massajp.snowwars.weapon.gun.GunWeapon
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.entity.Snowball
import org.bukkit.inventory.ItemStack

class PowderShooter : GunWeapon {

    override val time: Long = 2
    override val damage: Double = 5.0

    override fun launch(player: Player) {
        val ball = player.launchProjectile(Snowball::class.java)
        ball.shooter = player
        ball.customName = "PowderShooter"
        ball.velocity = player.location.direction.multiply(0.8)
    }

    override fun zoom(player: Player, isZoom: Boolean) {
        TODO("Not yet implemented")
    }

    override fun item(): ItemStack {
        val item = ItemStack(Material.IRON_HOE)
        val meta = item.itemMeta
        meta?.setDisplayName("${ChatColor.BOLD}${ChatColor.AQUA}スノーパウダーシューター")
        meta?.setCustomModelData(1)
        item.itemMeta = meta
        return item
    }

}