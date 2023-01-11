package massajp.snowwars.commands.subcommands

import massajp.snowwars.SnowWars
import massajp.snowwars.commands.SubCommand
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GetCommand : SubCommand {
    override fun execute(sender: CommandSender, args: Array<String>) {
        (sender as Player).setItemInHand(SnowWars.gunManager.getPlayerGun(sender))
    }
}