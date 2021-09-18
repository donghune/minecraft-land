package com.github.donghune.land.schduler

import com.github.donghune.land.extension.getLand
import com.github.donghune.land.model.entity.LandOption
import com.github.donghune.land.model.entity.hasPermission
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent

object PlayerLocationScheduler : Runnable {
    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {

            val previousLocation = player.previousLocation
            val currentLocation = player.location

            if (!player.isOp && previousLocation != null) {
                val previousArea = previousLocation.chunk.getLand()
                val currentArea = currentLocation.chunk.getLand()

                if (previousArea !== currentArea) {
                    if (!previousArea.hasPermission(player, LandOption.EXIT) ||
                        !currentArea.hasPermission(player, LandOption.ENTRANCE)
                    ) {
                        previousLocation.apply { yaw = currentLocation.yaw; pitch = currentLocation.pitch }
                        player.teleport(previousLocation, PlayerTeleportEvent.TeleportCause.PLUGIN)
                        continue
                    }
                }
            }
            player.previousLocation = currentLocation
        }
    }
}

private val playerPreviousLocation = mutableMapOf<Player, Location?>()

var Player.previousLocation: Location?
    get() = playerPreviousLocation[this@previousLocation]
    set(value) {
        playerPreviousLocation[this@previousLocation] = value
    }
