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

class VillageJoinBuildInventory : GUI(plugin, 27, "마을 가입 또는 구축") {

    companion object {
        private val ICON_JOIN: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.BOOK)
                .setDisplay("&f마을 가입")
                .build()
        }
        private val ICON_BUILD: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.WRITABLE_BOOK)
                .setDisplay("&f마을 구축")
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
        setItem(11, ICON_JOIN()) {
        }
        setItem(15, ICON_BUILD()) {
        }
    }
}