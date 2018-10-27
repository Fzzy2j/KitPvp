package me.fzzy.kits.commands

import me.fzzy.kits.KitMenu
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandKit : CommandExecutor {

    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (sender is Player) {
            KitMenu.openMenu(sender)
            return true
        }
        return false
    }

}