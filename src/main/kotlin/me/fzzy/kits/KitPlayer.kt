package me.fzzy.kits

import me.fzzy.kits.kits.Fighter
import ninja.leaping.configurate.ConfigurationNode
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

class KitPlayer private constructor(var p: Player) {

    var kit: Kit
        get() {
            // Default kit is Fighter
            if (node().getNode("kit").isVirtual)
                node().getNode("kit").value = Fighter.name
            return Kit.getKitByName(node().getNode("kit").string!!)!!
        }
        set (value) {
            node().getNode("kit").value = value.name
        }

    var nextKit: Kit? = null

    fun equipKit() {
        if (nextKit != null) kit = nextKit!!
        p.inventory.clear()
        p.gameMode = GameMode.ADVENTURE

        p.inventory.helmet = kit.helmet
        p.inventory.chestplate = kit.chestplate
        p.inventory.leggings = kit.leggings
        p.inventory.boots = kit.boots

        for (weapon in kit.weapons) {
            if (weapon.type == Material.BOW && p.inventory.getItem(9) == null)
                p.inventory.setItem(9, ItemStack(Material.ARROW))
            p.inventory.addItem(weapon)
        }
    }

    private fun node(): ConfigurationNode {
        return KitPvp.instance.playerNode.getNode(p.uniqueId.toString())
    }

    companion object {
        private val kitPlayers: HashMap<UUID, KitPlayer> = hashMapOf()

        fun getKitPlayer(player: Player): KitPlayer {
            return kitPlayers.getOrDefault(player.uniqueId, KitPlayer(player))
        }
    }

}