package com.github.donghune.land.inventory

import com.github.donghune.land.model.land.entity.Land
import com.github.donghune.land.plugin
import com.github.donghune.namulibrary.inventory.GUI
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class PersonalTransferOfOwnershipInventory(
    val land: Land,
) : GUI(plugin, 27, "개인 토지 소유권 이전") {

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
        PersonalLandSettingInventory(land).openLater(event.player as Player)
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
    }
}