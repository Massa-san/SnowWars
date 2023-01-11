package massajp.snowwars.commands.subcommands

import massajp.snowwars.SnowWars
import massajp.snowwars.commands.SubCommand
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RegisterGun : SubCommand {
    override fun execute(sender: CommandSender, args: Array<String>) {
        sender.sendMessage("実行したね？！その心実行してるね？！")
        SnowWars.gunManager.registerGun(sender as Player, args[1])
    }
}