package com.github.donghune

import com.github.donghune.money.model.PlayerMoneyRepository
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerGroundListener : Listener {
    @EventHandler
    fun onPlayerJoinEvent(event: PlayerJoinEvent) {
        PlayerMoneyRepository.getSafety(event.player.uniqueId.toString())
    }
}
