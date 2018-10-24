package me.fzzy.kits

import me.fzzy.kits.listeners.PlayerDeathListener
import me.fzzy.kits.listeners.PlayerRespawnListener
import ninja.leaping.configurate.ConfigurationNode
import ninja.leaping.configurate.commented.CommentedConfigurationNode
import ninja.leaping.configurate.hocon.HoconConfigurationLoader
import ninja.leaping.configurate.loader.ConfigurationLoader
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class KitPvp : JavaPlugin() {

    private lateinit var playerFile: File
    lateinit var playerManager: ConfigurationLoader<CommentedConfigurationNode>
    lateinit var playerNode: ConfigurationNode

    companion object {
        lateinit var instance: KitPvp
    }

    override fun onEnable() {
        instance = this
        Bukkit.getServer().pluginManager.registerEvents(PlayerDeathListener(), this)
        Bukkit.getServer().pluginManager.registerEvents(PlayerRespawnListener(), this)

        playerFile = File(this.dataFolder.path + File.separator + "players.conf")
        playerFile.parentFile.mkdirs()
        playerManager = HoconConfigurationLoader.builder().setPath(playerFile.toPath()).build()
        playerNode = playerManager.load()
    }

}