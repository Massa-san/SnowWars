package massajp.snowwars.weapon.gun

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface GunWeapon {
    val time: Long
    val damage: Double
    fun launch(player: Player)
    fun zoom(player: Player, isZoom: Boolean)
    fun item(): ItemStack
}