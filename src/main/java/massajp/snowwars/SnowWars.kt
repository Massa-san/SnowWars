package massajp.snowwars

import massajp.snowwars.arena.respawn.DeadAndRespawn
import massajp.snowwars.commands.MainCommand
import massajp.snowwars.weapon.gun.GunManager
import massajp.snowwars.weapon.gun.guns.LandEvent
import massajp.snowwars.weapon.system.ClickAPI
import org.bukkit.plugin.java.JavaPlugin

class SnowWars : JavaPlugin() {

    companion object {
        lateinit var instance: SnowWars

        lateinit var gunManager: GunManager
        lateinit var clickAPI: ClickAPI
    }

    override fun onEnable() {
        // Plugin startup logic
        instance = this
        gunManager = GunManager(this)
        clickAPI = ClickAPI(this)

        MainCommand(this)

        DeadAndRespawn(this)
        LandEvent(this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}