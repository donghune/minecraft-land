package com.github.donghune.land.inventory

import com.github.donghune.land.model.entity.Land
import com.github.donghune.land.model.usecase.SellLandUseCase
import com.github.donghune.land.model.usecase.SellLandUseCaseParam
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class LandSellConfirmInventory(
    private val land : Land
) : GUI(plugin, 27, "토지를 판매하시겠습니까?") {

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        setItem(11, InvIcon.ICON_OK()) {
            it.isCancelled = true
            SellLandUseCase(SellLandUseCaseParam(it.whoClicked as Player))
            (it.whoClicked as Player).closeInventory()
        }
        setItem(15, InvIcon.ICON_CANCEL()) {
            it.isCancelled = true
            (it.whoClicked as Player).closeInventory()
        }
    }
}