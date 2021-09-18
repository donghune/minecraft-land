package com.github.donghune.warp.inventory

import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import com.github.donghune.warp.model.WarpConfigRepository
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class WarpListInventory(val player: Player) : GUI(plugin, 54, "Warp List"), Listener {

    override suspend fun setContent() {
        WarpConfigRepository.get().warpPointList
            .forEachIndexed { index, warp ->
                setItem(index, warp.toItemStack()) {
                    player.teleport(warp.location)
                }
            }
    }

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }
}
