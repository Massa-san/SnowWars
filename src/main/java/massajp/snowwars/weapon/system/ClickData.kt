package massajp.snowwars.weapon.system

import org.bukkit.entity.Player

class ClickData (player: Player) {
    private val player: Player

    private var tick: Long = 0
    private var clickType = ClickType.NO_CLICK

    init {
        this.player = player
    }

    fun getTick(): Long {
        return tick
    }

    fun getClickType(): ClickType {
        return clickType
    }


    fun setTick(tick: Long) {
        this.tick = tick
    }

    fun setClickType(clickType: ClickType) {
        this.clickType = clickType
    }
}