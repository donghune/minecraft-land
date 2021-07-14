package com.github.donghune.land.inventory.village

import com.github.donghune.land.inventory.LandBuyInventory
import com.github.donghune.land.model.entity.LandType
import com.github.donghune.land.model.usecase.BuyLandUseCase
import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

class VillageLandBuyInventory : GUI(plugin, 27, "마을 토지 구매") {

    companion object {
        private val ICON_OK: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.GREEN_WOOL)
                .setDisplay("&f구매")
                .build()
        }
        private val ICON_CANCEL: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.RED_WOOL)
                .setDisplay("&f취소")
                .build()
        }
    }

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        setItem(11, ICON_OK()) {
            BuyLandUseCase.buyChunk(it.whoClicked as Player, LandType.VILLAGE)
        }
        setItem(15, ICON_CANCEL()) {
            LandBuyInventory().open(it.whoClicked as Player)
        }
    }
}