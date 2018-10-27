package me.fzzy.kits

import me.fzzy.kits.commands.CommandKit
import me.fzzy.kits.listeners.PlayerDamageListener
import me.fzzy.kits.listeners.PlayerDeathListener
import me.fzzy.kits.listeners.PlayerMoveListener
import me.fzzy.kits.listeners.PlayerRespawnListener
import ninja.leaping.configurate.ConfigurationNode
import ninja.leaping.configurate.commented.CommentedConfigurationNode
import ninja.leaping.configurate.hocon.HoconConfigurationLoader
import ninja.leaping.configurate.loader.ConfigurationLoader
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.generator.ChunkGenerator
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.io.File
import java.util.Random
import org.bukkit.World
import org.bukkit.generator.BlockPopulator
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.Arrays

class KitPvp : JavaPlugin() {

    private lateinit var playerFile: File
    lateinit var playerManager: ConfigurationLoader<CommentedConfigurationNode>
    lateinit var playerNode: ConfigurationNode

    companion object {
        lateinit var instance: KitPvp
        val random = Random()
    }

    override fun onEnable() {
        instance = this
        Bukkit.getServer().pluginManager.registerEvents(PlayerDeathListener, this)
        Bukkit.getServer().pluginManager.registerEvents(PlayerRespawnListener, this)
        Bukkit.getServer().pluginManager.registerEvents(PlayerMoveListener, this)
        Bukkit.getServer().pluginManager.registerEvents(PlayerDamageListener, this)
        Bukkit.getServer().pluginManager.registerEvents(KitMenu, this)

        this.getCommand("kit").executor = CommandKit()

        playerFile = File(this.dataFolder.path + File.separator + "players.conf")
        playerFile.parentFile.mkdirs()
        playerManager = HoconConfigurationLoader.builder().setPath(playerFile.toPath()).build()
        playerNode = playerManager.load()
        object: BukkitRunnable() {
            override fun run() {
                playerManager.save(playerNode)
            }
        }.runTaskTimer(this, 20L, 100L)
    }

    override fun getDefaultWorldGenerator(worldName: String?, id: String?): ChunkGenerator {
        return VoidWorldGenerator()
    }

    class VoidWorldGenerator : ChunkGenerator() {
        override fun getDefaultPopulators(world: World?): List<BlockPopulator> {
            return Arrays.asList(*arrayOfNulls(0))
        }

        override fun canSpawn(world: World, x: Int, z: Int): Boolean {
            return true
        }

        fun generate(world: World, rand: Random, chunkx: Int, chunkz: Int): ByteArray {
            return ByteArray(32768)
        }

        override fun getFixedSpawnLocation(world: World?, random: Random?): Location {
            return Location(world, 0.0, 128.0, 0.0)
        }

        override fun generateChunkData(world: World?, random: Random?, chunkX: Int, chunkZ: Int, biome: ChunkGenerator.BiomeGrid?): ChunkGenerator.ChunkData {
            return createChunkData(world)
        }
    }

}