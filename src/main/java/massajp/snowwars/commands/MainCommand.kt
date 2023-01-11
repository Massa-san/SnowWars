package massajp.snowwars.commands

import massajp.snowwars.SnowWars
import massajp.snowwars.commands.subcommands.GetCommand
import massajp.snowwars.commands.subcommands.RegisterGun
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.*
import org.bukkit.entity.Player


class MainCommand (instance: SnowWars) : CommandExecutor, TabCompleter {

    companion object {
        private val subcommands = mutableMapOf<String, SubCommand>()
    }

    init {
        instance.getCommand("snow")?.setExecutor(this)

        subcommands["regi"] = RegisterGun()
        subcommands["get"] = GetCommand()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val player: Player = if (sender is BlockCommandSender) getNearbyPlayer(sender.block.location) ?: return false
        else sender as Player

        if (args.isEmpty()) return false
        val sc = subcommands[args[0]] ?: return false
        sc.execute(player, args)
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): MutableList<String> {
        return subcommands.keys.toMutableList()
    }

    private fun getNearbyPlayer(loc: Location): Player? {
        var nearest = 100.0
        var nearestPlayer: Player? = null
        for (entity in loc.world?.getNearbyEntities(loc, 3.0, 3.0, 3.0)!!) {
            if (entity is Player) {
                val distance: Double = entity.getLocation().distance(loc)
                if (nearest > distance) {
                    nearest = distance
                    nearestPlayer = entity
                }
            }
        }
        return nearestPlayer
    }
}
