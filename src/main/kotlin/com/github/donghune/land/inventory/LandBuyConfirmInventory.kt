package com.github.donghune.land.inventory

import com.github.donghune.land.model.entity.LandType
import com.github.donghune.land.model.usecase.BuyLandUseCase
import com.github.donghune.land.model.usecase.BuyLandUseCaseParam
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class LandBuyConfirmInventory(
    private val landType: LandType
) : GUI(plugin, 27, "토지 구매") {

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        setItem(11, InvIcon.ICON_OK()) {
            it.isCancelled = true
            BuyLandUseCase(BuyLandUseCaseParam(it.whoClicked as Player, landType))
            (it.whoClicked as Player).closeInventory()
        }
        setItem(15, InvIcon.ICON_CANCEL()) {
            it.isCancelled = true
            (it.whoClicked as Player).closeInventory()
        }
    }
}
