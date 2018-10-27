package me.fzzy.kits.listeners

import me.fzzy.kits.KitPlayer
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

object PlayerRespawnListener : Listener {

    @EventHandler
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        val player = KitPlayer.getKitPlayer(event.player)
        if (event.player.gameMode != GameMode.CREATIVE)
            player.equipKit()
    }

}