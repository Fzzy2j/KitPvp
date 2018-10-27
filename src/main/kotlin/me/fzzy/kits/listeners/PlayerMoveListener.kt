package me.fzzy.kits.listeners

import me.fzzy.kits.KitPvp
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

object PlayerMoveListener : Listener {

    val launched = arrayListOf<UUID>()

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        event.player.foodLevel = 20
        event.player.saturation = 0F

        if (event.player.velocity.lengthSquared() > 0.9) {
            for (i in 0..10)
                event.player.world.spawnParticle(Particle.SMOKE_NORMAL, event.player.location.clone()
                        .add(KitPvp.random.nextInt(100) / 50.0 - 1, KitPvp.random.nextInt(100) / 50.0 - 1, KitPvp.random.nextInt(100) / 50.0 - 1)
                        , 0)
        }

        if (event.to.clone().subtract(0.0, 0.1, 0.0).block.type == Material.SPONGE) {

            if (launched.contains(event.player.uniqueId)) return
            launched.add(event.player.uniqueId)
            object : BukkitRunnable() {
                override fun run() {
                    launched.remove(event.player.uniqueId)
                }
            }.runTaskLater(KitPvp.instance, 10L)

            event.player.velocity = event.player.location.direction.multiply(3.6)
            event.player.world.playSound(event.player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1F, 1F)
            for (i in 0..100)
                event.player.world.spawnParticle(Particle.SMOKE_LARGE, event.player.location.clone()
                        .add(KitPvp.random.nextInt(200) / 50.0 - 2, KitPvp.random.nextInt(200) / 50.0 - 2, KitPvp.random.nextInt(200) / 50.0 - 2)
                        , 0)
        }
    }

}