package massajp.snowwars.weapon.gun

import massajp.snowwars.SnowWars
import massajp.snowwars.weapon.gun.guns.BallShooter
import massajp.snowwars.weapon.gun.guns.PowderShooter
import massajp.snowwars.weapon.gun.guns.ChickenLauncher
import massajp.snowwars.weapon.gun.guns.PowderSniper
import massajp.snowwars.weapon.system.ClickType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class GunManager (instance: SnowWars) : Listener {


    companion object {
        private var gunList = mutableMapOf<String, GunWeapon>()
        private var dataMap = mutableMapOf<Player, GunData>()
    }

    private val instance: SnowWars

    init {
        this.instance = instance
        this.instance.server.pluginManager.registerEvents(this, instance)

        gunList["ball_shooter"] = BallShooter()
        gunList["powder_shooter"] = PowderShooter()
        gunList["chicken_launcher"] = ChickenLauncher()
        gunList["powder_sniper"] = PowderSniper()
    }

    @EventHandler
    fun onGunClick(event: PlayerInteractEvent) {
        val player = event.player
        if (!isMainHandEqualsSelectGun(player)) return

        if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
            val clickAPI = SnowWars.clickAPI
            clickAPI.fireClickEvent(player)

            fireStateChangeEvent(player)
        } else if (event.action == Action.LEFT_CLICK_AIR || event.action == Action.LEFT_CLICK_BLOCK) {
            zoomEvent(player)
        }
    }

    private fun registerPlayer(player: Player) {
        if (!dataMap.containsKey(player)) {
            val data = GunData(player)
            dataMap[player] = data
        }
    }

    fun fireStateChangeEvent(player: Player) {
        if (!dataMap.containsKey(player)) {
            registerPlayer(player)
            return
        }

        val data = dataMap[player]
        if (data?.checkState() == false) return

        data?.fireGun()
    }

    fun zoomEvent(player: Player) {
        if (!dataMap.containsKey(player)) {
            registerPlayer(player)
            return
        }

        val data = dataMap[player]
        data?.zoom()
    }

    fun registerGun(player: Player, name: String) {
        if (!dataMap.containsKey(player)) registerPlayer(player)
        dataMap[player]?.setGun(getGun(name)!!)
        player.sendMessage("regi ok")
    }

    fun getGun(name: String): GunWeapon? {
        return gunList[name]
    }

    fun isMainHandEqualsSelectGun(player: Player): Boolean {
        try {
            if (player.inventory.itemInMainHand == dataMap[player]?.getGun()?.item()) return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun getPlayerGun(player: Player): ItemStack? {
        return dataMap[player]?.getGun()?.item()
    }

    fun getPlayerGunDamage(player: Player): Double? {
        return dataMap[player]?.getGun()?.damage
    }
}