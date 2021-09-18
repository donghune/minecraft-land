package com.github.donghune.warp.listener

import com.github.donghune.warp.inventory.WarpListInventory
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class WarpListener : Listener {
    @EventHandler
    fun onPlayerSwapHandItemsEvent(event: PlayerSwapHandItemsEvent) {
        if (!event.player.isSneaking) {
            return
        }
        event.isCancelled = true
        WarpListInventory(event.player).open(event.player)
    }
}
