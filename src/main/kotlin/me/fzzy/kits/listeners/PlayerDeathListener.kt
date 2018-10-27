package me.fzzy.kits.listeners

import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

object PlayerDeathListener : Listener {

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        event.keepInventory = event.entity.gameMode == GameMode.CREATIVE
        event.drops.clear()
    }

}