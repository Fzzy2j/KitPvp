package me.fzzy.kits.kits

import me.fzzy.kits.Kit
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import java.util.*

class Fighter : Kit {

    override val name: String = "${ChatColor.BLUE}Fighter"
    override val cost: Int = 0

    override val weapons: List<ItemStack>
        get() {
            val item = ItemStack(Material.IRON_SWORD)
            val meta = item.itemMeta
            meta.displayName = "${ChatColor.WHITE}Fighter Sword"
            return Arrays.asList(item)
        }
    override val helmet: ItemStack
        get() {
            val item = ItemStack(Material.LEATHER_HELMET)
            item.addUnsafeEnchantment(Enchantment.BINDING_CURSE, 1)

            val meta = item.itemMeta
            (item.itemMeta as LeatherArmorMeta).color = Color.BLUE
            item.itemMeta = meta

            return item
        }
    override val chestplate: ItemStack
        get() {
            val item = ItemStack(Material.LEATHER_CHESTPLATE)
            item.addUnsafeEnchantment(Enchantment.BINDING_CURSE, 1)
            return item
        }
    override val leggings: ItemStack
        get() {
            val item = ItemStack(Material.LEATHER_LEGGINGS)
            item.addUnsafeEnchantment(Enchantment.BINDING_CURSE, 1)
            return item
        }
    override val boots: ItemStack
        get() {
            val item = ItemStack(Material.LEATHER_BOOTS)
            item.addUnsafeEnchantment(Enchantment.BINDING_CURSE, 1)
            return item
        }

}