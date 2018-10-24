package me.fzzy.kits

import org.bukkit.inventory.ItemStack

interface Kit {

    val helmet: ItemStack
    val chestplate: ItemStack
    val leggings: ItemStack
    val boots: ItemStack
    val weapons: List<ItemStack>

    val name: String
    val cost: Int

}