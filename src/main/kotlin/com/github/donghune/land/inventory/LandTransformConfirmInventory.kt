package com.github.donghune.land.inventory

import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class LandTransformConfirmInventory : GUI(plugin, 27, "소유권을 양도할 플레이어를 선택해주세요") {

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