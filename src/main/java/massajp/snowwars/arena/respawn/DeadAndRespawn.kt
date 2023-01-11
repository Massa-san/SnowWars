package massajp.snowwars.arena.respawn

import massajp.snowwars.SnowWars
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.entity.minecart.StorageMinecart
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import java.util.regex.Pattern


class DeadAndRespawn (instance: SnowWars) : Listener {

    init {
        instance.server.pluginManager.registerEvents(this, instance)
    }

    @EventHandler
    fun onDead(event: EntityDamageEvent) {
        Bukkit.broadcastMessage("health: ${(event.entity as Player).health}, damage: ${event.damage}")
        if (event.entity is Player && ((event.entity as Player).health - event.damage) > 0) return
        Bukkit.broadcastMessage("health: ${(event.entity as Player).health}, damage: ${event.damage}")
        val player = (event.entity as Player)
        player.gameMode = GameMode.SPECTATOR
        event.isCancelled = true
        object : BukkitRunnable() {
            override fun run() {
                val deathBox: Entity = player.world.spawnEntity(player.eyeLocation, EntityType.MINECART_CHEST)
                val cart = deathBox as StorageMinecart
                for (i in 0..8) {
                    if (player.inventory.getItem(i) == null) continue
                    cart.inventory.addItem(player.inventory.getItem(i))
                }
                cart.inventory.addItem(tamashi(player))
            }
        }.runTaskLater(SnowWars.instance, 5)

        Bukkit.broadcastMessage("${player.name} は死にました")
    }

    @EventHandler
    fun respawn(event: PlayerToggleSneakEvent) {
        if (event.player.isSneaking) return
        if ((event.player.inventory.itemInMainHand.itemMeta?.displayName)?.matches(Regex(".*の魂")) == false) return
        val mcid = Regex("§d(.*)の魂").matchEntire(event.player.inventory.itemInMainHand.itemMeta?.displayName!!)?.groups?.get(1)!!.value
        Bukkit.broadcastMessage("$mcid の蘇生を開始します・・・")
        object : BukkitRunnable() {
            var tick = 0
            override fun run() {
                if (!event.player.isSneaking && event.player.inventory.itemInMainHand.type != Material.MAGMA_CREAM) cancel()
                when (tick) {
                    10 -> { event.player.sendMessage("蘇生10%完了")}
                    30 -> { event.player.sendMessage("蘇生30%完了")}
                    100 -> {
                        try {
                            event.player.inventory.setItemInMainHand(null)
                            val player: Player = Bukkit.getPlayer(mcid)!!
                            player.teleport(event.player.location.add(0.0, 25.0, 0.0))
                            player.addPotionEffect(PotionEffect(PotionEffectType.SLOW_FALLING, 10 * 20, 1), true)
                            player.gameMode = GameMode.ADVENTURE
                            event.player.sendMessage("蘇生100%完了")
                        } catch (e: Exception) {
                            e.printStackTrace()
                            event.player.sendMessage("蘇生100%失敗。そのプレイヤーはこのサーバーにいませんでした。")
                        }
                        cancel()
                    }
                }
                tick++
            }
        }.runTaskTimer(SnowWars.instance, 0, 1)
    }

    fun tamashi(player: Player): ItemStack {
        val item = ItemStack(Material.MAGMA_CREAM)
        val meta = item.itemMeta

        meta?.setDisplayName("${ChatColor.LIGHT_PURPLE}${player.name}の魂")
        val lorelist = ArrayList<String>()
        lorelist.add("この魂を蘇生の祭壇で")
        lorelist.add("使用すると味方を")
        lorelist.add("蘇らせることができる")
        meta?.lore = lorelist

        item.itemMeta = meta
        return item
    }
}