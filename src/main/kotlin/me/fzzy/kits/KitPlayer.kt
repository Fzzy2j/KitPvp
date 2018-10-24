package me.fzzy.kits

import org.bukkit.entity.Player
import java.util.*

class KitPlayer private constructor(var player: Player) {



    companion object {
        private val kitPlayers: HashMap<UUID, KitPlayer> = hashMapOf()

        fun getKitPlayer(player: Player): KitPlayer {

            return kitPlayers.getOrDefault(player.uniqueId, KitPlayer(player))
        }
    }

}