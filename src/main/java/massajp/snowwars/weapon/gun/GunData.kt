package massajp.snowwars.weapon.gun

import massajp.snowwars.SnowWars
import massajp.snowwars.weapon.system.ClickType
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class GunData (player: Player) {
    private val player: Player

    private var gun: GunWeapon?
    private var isFree: Boolean
    private var time: Long
    private var isZoom: Boolean

    init {
        this.player = player
        this.gun = null
        this.isFree = true
        this.time = 0
        this.isZoom = false
    }

    fun setGun(gunWeapon: GunWeapon) {
        this.gun = gunWeapon
        this.time = gunWeapon.time
    }

    fun getGun(): GunWeapon? {
        return gun
    }

    fun checkState(): Boolean {
        return isFree
    }

    fun fireGun() {
        if (gun == null) return
        if (!isFree) return
        gun!!.launch(player)
        isFree = false
        object : BukkitRunnable() {
            override fun run() {
                isFree = true
                if (SnowWars.clickAPI.getPlayerClickType(player) == ClickType.HOLD) fireGun()
            }
        }.runTaskLater(SnowWars.instance, time)
    }

    fun zoom() {
        if (gun == null) return
        gun!!.zoom(player, isZoom)
        isZoom = !isZoom
    }

}