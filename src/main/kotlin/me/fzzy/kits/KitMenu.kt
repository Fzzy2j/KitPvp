package me.fzzy.kits

import me.fzzy.kits.util.Methods
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

object KitMenu : Listener {

    fun openMenu(player: Player) {
        val inv = Bukkit.createInventory(null, 9 * 3, "Kit Menu")
        for (kit in Kit.kits) {
            val icon = kit.weapons[0].clone()
            val meta = icon.itemMeta

            meta.displayName = kit.name

            val lore = arrayListOf<String>()
            if (kit.helmet != null) lore.add("${ChatColor.WHITE}Helmet: ${Methods.makePrettyString(kit.helmet!!.type.name)}")
            if (kit.chestplate != null) lore.add("${ChatColor.WHITE}Chetplate: ${Methods.makePrettyString(kit.chestplate!!.type.name)}")
            if (kit.leggings != null) lore.add("${ChatColor.WHITE}Leggings: ${Methods.makePrettyString(kit.leggings!!.type.name)}")
            if (kit.boots != null) lore.add("${ChatColor.WHITE}Boots: ${Methods.makePrettyString(kit.boots!!.type.name)}")
            meta.lore = lore

            icon.itemMeta = meta
            inv.addItem(icon)
        }
        player.openInventory(inv)
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.name == "Kit Menu") event.isCancelled = true
        if (event.currentItem == null) return
        if (event.currentItem.itemMeta == null) return

        val player = KitPlayer.getKitPlayer(event.whoClicked as Player)
        val kit = Kit.getKitByName(event.currentItem.itemMeta.displayName) ?: return

        player.nextKit = kit
        player.p.sendMessage("${kit.name} ${ChatColor.AQUA}selected.")
        player.p.closeInventory()
        if (player.p.location.distanceSquared(player.p.world.spawnLocation) < Math.pow(6.0, 2.0)) player.equipKit()
        else player.p.sendMessage("${ChatColor.GRAY}You will spawn with ${kit.name} ${ChatColor.GRAY}next life")
    }


}