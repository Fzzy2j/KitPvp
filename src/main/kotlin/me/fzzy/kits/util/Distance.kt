package me.fzzy.kits.util

import java.util.ArrayList

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.util.Vector

object Distance {

    fun generateRose(loc: Location, size: Int, pattern: Double, density: Double): ArrayList<Location> {
        val list = ArrayList<Location>()

        // loop over 360 degrees
        var d = 0.0
        while (d < 360) {

            // covert degrees to radians
            val t = Math.PI / 180 * d

            // get out pattern
            val rm = size * Math.cos(pattern * t)

            // convert the polar ccordinates back to rectangular
            val x = Math.sin(d) * rm
            val z = Math.cos(d) * rm

            // get the location and add it to the list
            val rose = Location(loc.world, loc.x + x, loc.y, loc.z + z)
            list.add(rose)
            d += density
        }
        return list
    }

    fun getHighestPoint(loc: Location): Location? {
        var y = 256

        // couldn't just use the for loop value as y, not sure why, it just
        // didn't work
        for (i in 0..255) {
            val newLoc = Location(loc.world, loc.x, y.toDouble(), loc.z)
            if (newLoc.block.type.isSolid) {
                return newLoc
            }
            y--
        }
        return null
    }

    fun getTargetLocation(player: Player, distance: Int): Location? {
        val loc = player.eyeLocation

        var location: Location? = null

        // rectangular coordinates
        val px = loc.x
        val py = loc.y
        val pz = loc.z

        // yaw and pitch
        val yaw = Math.toRadians((loc.yaw + 90).toDouble())
        val pitch = Math.toRadians((loc.pitch + 90).toDouble())

        // polar coordinates
        val x = Math.sin(pitch) * Math.cos(yaw)
        val y = Math.sin(pitch) * Math.sin(yaw)
        val z = Math.cos(pitch)

        // loop for the distance
        for (i in 1..distance) {
            // add to polar coordinates then translate them to rectangular so
            // that bukkit understands what we're telling it
            val loc1 = Location(player.world, px + i * x, py + i * z, pz + i * y)
            // if not solid
            if (loc1.block.type.isSolid) {
                location = loc1
                break
            }
            if (i == distance) {
                if (location == null) {
                    location = loc1
                }
            }
        }
        return location
    }

    fun getEntitiesBetweenPlayerAndBlockLookingAt(player: Player, distance: Int): ArrayList<Entity> {
        val loc = player.eyeLocation

        val entitylist = ArrayList<Entity>()

        // rectangular coordinates
        val px = loc.x
        val py = loc.y
        val pz = loc.z

        // yaw and pitch
        val yaw = Math.toRadians((loc.yaw + 90).toDouble())
        val pitch = Math.toRadians((loc.pitch + 90).toDouble())

        // polar coordinates
        val x = Math.sin(pitch) * Math.cos(yaw)
        val y = Math.sin(pitch) * Math.sin(yaw)
        val z = Math.cos(pitch)

        for (i in 1..distance) {
            // increment polar coordinates then convert to rectangular
            val loc1 = Location(player.world, px + i * x, py + i * z, pz + i * y)
            // get entities in 3 block radius
            for (e in Distance.getEntitiesInRadius(loc1, 3.0)) {
                // add them to the list
                entitylist.add(e)
            }
        }
        return entitylist
    }

    fun getBlocksBetweenPlayerAndBlockLookingAt(player: Player, distance: Int): ArrayList<Block> {
        val loc = player.eyeLocation

        val blocklist = ArrayList<Block>()

        // rectangular coordinates
        val px = loc.x
        val py = loc.y
        val pz = loc.z

        // yaw and pitch
        val yaw = Math.toRadians((loc.yaw + 90).toDouble())
        val pitch = Math.toRadians((loc.pitch + 90).toDouble())

        // polar coordinates
        val x = Math.sin(pitch) * Math.cos(yaw)
        val y = Math.sin(pitch) * Math.sin(yaw)
        val z = Math.cos(pitch)

        for (i in 1..distance) {
            // increment polar coordinates then convert to rectangular
            val loc1 = Location(player.world, px + i * x, py + i * z, pz + i * y)
            // add the block at location to list
            blocklist.add(loc1.block)
        }
        return blocklist
    }

    // same thing as above but with locations instead of blocks
    fun getLocationsBetweenPlayerAndBlockLookingAt(player: Player, distance: Int): ArrayList<Location> {
        val loc = player.eyeLocation

        val loclist = ArrayList<Location>()

        val px = loc.x
        val py = loc.y
        val pz = loc.z

        val yaw = Math.toRadians((loc.yaw + 90).toDouble())
        val pitch = Math.toRadians((loc.pitch + 90).toDouble())

        val x = Math.sin(pitch) * Math.cos(yaw)
        val y = Math.sin(pitch) * Math.sin(yaw)
        val z = Math.cos(pitch)

        for (i in 1..distance) {
            val loc1 = Location(player.world, px + i * x, py + i * z, pz + i * y)
            loclist.add(loc1)
        }
        return loclist
    }

    fun getLocationsBetweenLocations(loc: Location, loc2: Location): ArrayList<Location> {
        var loc = loc

        val loclist = ArrayList<Location>()

        val distance = Math.floor(loc.distance(loc2)).toInt()

        loc = Distance.lookAt(loc, loc2)

        val px = loc.x
        val py = loc.y
        val pz = loc.z

        val yaw = Math.toRadians((loc.yaw + 90).toDouble())
        val pitch = Math.toRadians((loc.pitch + 90).toDouble())

        val x = Math.sin(pitch) * Math.cos(yaw)
        val y = Math.sin(pitch) * Math.sin(yaw)
        val z = Math.cos(pitch)

        for (i in 1..distance) {
            val loc1 = Location(loc.world, px + i * x, py + i * z, pz + i * y)
            loclist.add(loc1)
        }
        return loclist
    }

    fun getLocationsBetweenLocations(loc: Location, loc2: Location, offset: Double, interval: Double): ArrayList<Location> {
        var loc = loc

        val loclist = ArrayList<Location>()

        val distance = Math.floor(loc.distance(loc2)).toInt()

        loc = Distance.lookAt(loc, loc2)

        val px = loc.x
        val py = loc.y
        val pz = loc.z

        val yaw = Math.toRadians((loc.yaw + 90).toDouble())
        val pitch = Math.toRadians((loc.pitch + 90).toDouble())

        val x = Math.sin(pitch) * Math.cos(yaw)
        val y = Math.sin(pitch) * Math.sin(yaw)
        val z = Math.cos(pitch)

        loclist.add(loc)
        var i = offset
        while (i <= distance) {
            val loc1 = Location(loc.world, px + i * x, py + i * z, pz + i * y)
            loclist.add(loc1)
            i += interval
        }
        return loclist
    }

    fun lookAt(loc: Location, lookat: Location): Location {
        var loc = loc

        loc = loc.clone()

        // distance between x, y, and z
        val dx = lookat.x - loc.x
        val dy = lookat.y - loc.y
        val dz = lookat.z - loc.z

        if (dx != 0.0) {
            if (dx < 0) {
                loc.yaw = (1.5 * Math.PI).toFloat()
            } else {
                loc.yaw = (0.5 * Math.PI).toFloat()
            }
            loc.yaw = loc.yaw - Math.atan(dz / dx).toFloat()
        } else if (dz < 0) {
            loc.yaw = Math.PI.toFloat()
        }

        val dxz = Math.sqrt(Math.pow(dx, 2.0) + Math.pow(dz, 2.0))

        loc.pitch = (-Math.atan(dy / dxz)).toFloat()

        loc.yaw = -loc.yaw * 180f / Math.PI.toFloat()
        loc.pitch = loc.pitch * 180f / Math.PI.toFloat()

        return loc
    }

    fun move(loc: Location, offset: Vector): Location {

        val ryaw = -loc.yaw / 180f * Math.PI.toFloat()
        val rpitch = loc.pitch / 180f * Math.PI.toFloat()

        var x = loc.x
        var y = loc.y
        var z = loc.z
        z -= offset.x * Math.sin(ryaw.toDouble())
        z += offset.y * Math.cos(ryaw.toDouble()) * Math.sin(rpitch.toDouble())
        z += offset.z * Math.cos(ryaw.toDouble()) * Math.cos(rpitch.toDouble())
        x += offset.x * Math.cos(ryaw.toDouble())
        x += offset.y * Math.sin(rpitch.toDouble()) * Math.sin(ryaw.toDouble())
        x += offset.z * Math.sin(ryaw.toDouble()) * Math.cos(rpitch.toDouble())
        y += offset.y * Math.cos(rpitch.toDouble())
        y -= offset.z * Math.sin(rpitch.toDouble())
        return Location(loc.world, x, y, z, loc.yaw, loc.pitch)
    }

    fun getPlayersInRadius(loc: Location, radius: Double): ArrayList<Player> {

        val players = ArrayList<Player>()

        val i1 = loc.x
        val j1 = loc.y
        val k1 = loc.z

        for (player in Bukkit.getOnlinePlayers()) {
            if (player.world == loc.world) {
                val i2 = player.location.x + 1
                val j2 = player.location.y + 1
                val k2 = player.location.z + 1

                val ad = (i2 - i1) * (i2 - i1) + (j2 - j1) * (j2 - j1) + (k2 - k1) * (k2 - k1)

                if (ad < radius * radius) {
                    players.add(player)
                }
            }
        }
        return players
    }

    fun getPlayersInRadius(p: Player, radius: Double): ArrayList<Player> {

        val loc = p.location

        val players = ArrayList<Player>()

        val i1 = loc.x
        val j1 = loc.y
        val k1 = loc.z

        for (player in Bukkit.getOnlinePlayers()) {
            if (player.uniqueId !== p.uniqueId) {
                if (player.world == loc.world) {
                    val i2 = player.location.x + 1
                    val j2 = player.location.y + 1
                    val k2 = player.location.z + 1

                    val ad = (i2 - i1) * (i2 - i1) + (j2 - j1) * (j2 - j1) + (k2 - k1) * (k2 - k1)

                    if (ad < radius * radius) {
                        players.add(player)
                    }
                }
            }
        }
        return players
    }

    fun getEntitiesInRadius(loc: Location, radius: Double): ArrayList<Entity> {

        val entities = ArrayList<Entity>()

        val i1 = loc.x
        val j1 = loc.y
        val k1 = loc.z

        for (e in loc.world.entities) {
            if (e.world == loc.world) {
                val i2 = e.location.x
                val j2 = e.location.y
                val k2 = e.location.z

                val ad = (i2 - i1) * (i2 - i1) + (j2 - j1) * (j2 - j1) + (k2 - k1) * (k2 - k1)

                if (ad < radius * radius) {
                    entities.add(e)
                }
            }
        }
        return entities
    }

    fun getEntitiesInRadius(entity: Entity, radius: Double): ArrayList<Entity> {

        val loc = entity.location

        val entities = ArrayList<Entity>()

        val i1 = loc.x
        val j1 = loc.y
        val k1 = loc.z

        for (e in loc.world.entities) {
            if (e.uniqueId !== entity.uniqueId) {
                if (e.world == loc.world) {
                    val i2 = e.location.x
                    val j2 = e.location.y
                    val k2 = e.location.z

                    val ad = (i2 - i1) * (i2 - i1) + (j2 - j1) * (j2 - j1) + (k2 - k1) * (k2 - k1)

                    if (ad < radius * radius) {
                        entities.add(e)
                    }
                }
            }
        }
        return entities
    }

    fun getClosestEntity(e: Entity, radius: Double): Entity? {

        var minimalDistance = Math.pow(radius, 2.0)
        var curDist: Double
        var closest: Entity? = null
        for (p in e.world.entities) {
            if (p != e) {
                curDist = e.location.distanceSquared(p.location)
                if (curDist < minimalDistance) {
                    minimalDistance = curDist
                    closest = p
                }
            }
        }
        return closest
    }

    fun getClosestEntity(loc: Location, radius: Double): Entity? {

        var minimalDistance = Math.pow(radius, 2.0)
        var curDist: Double
        var closest: Entity? = null
        for (p in loc.world.entities) {
            curDist = loc.distanceSquared(p.location)
            if (curDist < minimalDistance) {
                minimalDistance = curDist
                closest = p
            }
        }
        return closest
    }

    fun getClosestPlayer(e: Entity, radius: Double): Player? {

        var minimalDistance = Math.pow(radius, 2.0)
        var curDist: Double
        var closest: Player? = null
        for (p in Bukkit.getOnlinePlayers()) {
            curDist = e.location.distanceSquared(p.location)
            if (curDist < minimalDistance) {
                minimalDistance = curDist
                closest = p
            }
        }
        return closest
    }

    fun getClosestPlayer(loc: Location, radius: Double): Player? {

        var minimalDistance = Math.pow(radius, 2.0)
        var curDist: Double
        var closest: Player? = null
        for (p in Bukkit.getOnlinePlayers()) {
            curDist = loc.distanceSquared(p.location)
            if (curDist < minimalDistance) {
                minimalDistance = curDist
                closest = p
            }
        }
        return closest
    }
}