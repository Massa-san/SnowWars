package massajp.snowwars.weapon.system

import org.bukkit.scheduler.BukkitRunnable

class ClickTimer : BukkitRunnable() {
    var time: Long = 0

    override fun run() {
        time++
    }
}