package massajp.snowwars.commands

import org.bukkit.command.CommandSender

interface SubCommand {
    fun execute(sender: CommandSender, args: Array<String>)
}