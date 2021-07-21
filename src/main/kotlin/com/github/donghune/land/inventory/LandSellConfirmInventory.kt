package com.github.donghune.land.inventory

import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class LandSellConfirmInventory : GUI(plugin, 27, "토지를 판매하시겠습니까?") {

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        setItem(11, InvIcon.ICON_OK()) {
            it.isCancelled = true
        }
        setItem(15, InvIcon.ICON_CANCEL()) {
            it.isCancelled = true
        }
    }
}