package com.github.donghune.land.inventory

import com.github.donghune.land.plugin
import com.github.donghune.namulibrary.inventory.GUI
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class LandBuyInventory : GUI(plugin, 27, "토지 구매") {
    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
        LandMainInventory().open(event.player as Player)
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
    }
}