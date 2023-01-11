package massajp.snowwars.weapon.gun.guns

import massajp.snowwars.weapon.gun.GunWeapon
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.entity.Snowball
import org.bukkit.inventory.ItemStack

class BallShooter : GunWeapon {

    override val time: Long = 4
    override val damage: Double = 7.0

    override fun launch(player: Player) {
        val ball = player.launchProjectile(Snowball::class.java)
        ball.shooter = player
        ball.customName = "BallShooter"
        ball.velocity = player.location.direction.multiply(1.3)
    }

    override fun zoom(player: Player, isZoom: Boolean) {
        TODO("Not yet implemented")
    }

    override fun item(): ItemStack {
        val item = ItemStack(Material.DIAMOND_HOE)
        val meta = item.itemMeta
        meta?.setDisplayName("${ChatColor.BOLD}${ChatColor.AQUA}スノーボールシューター")
        meta?.setCustomModelData(1)
        item.itemMeta = meta
        return item
    }

}