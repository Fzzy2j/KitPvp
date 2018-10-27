package me.fzzy.kits

import me.fzzy.kits.kits.Archer
import me.fzzy.kits.kits.Fighter
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class Kit {

    companion object {
        val kits = arrayListOf(
                Fighter,
                Archer
        )

        fun getKitByName(name: String): Kit? {
            for (kit in kits) {
                if (kit.name == name)
                    return kit
            }
            return null
        }
    }

    abstract val helmet: ItemStack?
    abstract val chestplate: ItemStack?
    abstract val leggings: ItemStack?
    abstract val boots: ItemStack?
    abstract val weapons: List<ItemStack>

    abstract val name: String
    abstract val cost: Int

    override fun equals(other: Any?): Boolean {
        if (other !is Kit) return false
        return this.name == other.name
    }

}