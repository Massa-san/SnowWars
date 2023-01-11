package massajp.snowwars.weapon.system

import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin


class ClickAPI (plugin: JavaPlugin) {

    private val plugin: JavaPlugin
    private var timer: ClickTimer

    private var dataMap: MutableMap<Player, ClickData>

    /**
     * @param plugin JavaPlugin that tasks will be allocated on.
     */
    init {
        this.plugin = plugin
        timer = ClickTimer()
        timer.runTaskTimer(plugin, 0, 1)
        dataMap = mutableMapOf()
    }


    /**
     * Call this method once before checking the player's click.
     *
     * @param player Player you want to register.
     */
    private fun registerPlayer(player: Player) {
        if (!dataMap.containsKey(player)) {
            val data = ClickData(player)
            dataMap[player] = data
        }
    }


    /**
     * Call this method when a player right-clicks.
     *
     * @param player Player who right-clicks.
     */
    fun fireClickEvent(player: Player) {
        if (!dataMap.containsKey(player)) {
            registerPlayer(player)
            return
        }
        val data: ClickData = dataMap[player] ?: return
        val tick: Long = timer.time - data.getTick()
        if (tick < 4) {
            data.setClickType(ClickType.BUTTON_MASHING)
        } else {
            if (tick <= 9) data.setClickType(ClickType.HOLD)
            else data.setClickType(ClickType.FIRST_CLICK)
        }
        data.setTick(timer.time)
    }


    /**
     * @param player Player you want to get whose click type.
     * @return Player's click type.
     */
    fun getPlayerClickType(player: Player): ClickType? {
        if (!dataMap.containsKey(player)) {
            registerPlayer(player)
            return ClickType.NO_CLICK
        }
        val data: ClickData = dataMap[player] ?: return null
        val tick: Long = timer.time - data.getTick()
        return if (tick >= 9) {
            data.setClickType(ClickType.NO_CLICK)
            ClickType.NO_CLICK
        } else {
            data.getClickType()
        }
    }

}