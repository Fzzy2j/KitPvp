package me.fzzy.kits.kits

import me.fzzy.kits.Kit
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import java.util.*

object Archer : Kit() {

    override val name: String = "${ChatColor.BLUE}Archer"
    override val cost: Int = 0

    override val weapons: List<ItemStack>
        get() {
            val item = ItemStack(Material.BOW)

            item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1)
            item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1)

            val meta = item.itemMeta
            meta.displayName = "${ChatColor.WHITE}Archer Bow"
            item.itemMeta = meta
            return Arrays.asList(item)
        }
    override val helmet: ItemStack?
        get() {
            val item = ItemStack(Material.LEATHER_HELMET)
            item.addUnsafeEnchantment(Enchantment.BINDING_CURSE, 1)
            item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3)

            val meta = item.itemMeta as LeatherArmorMeta
            meta.color = Color.RED

            item.itemMeta = meta

            return item
        }
    override val chestplate: ItemStack? = null
    override val leggings: ItemStack? = null
    override val boots: ItemStack?
        get() {
            val item = ItemStack(Material.LEATHER_BOOTS)
            item.addUnsafeEnchantment(Enchantment.BINDING_CURSE, 1)
            item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
            return item
        }

}