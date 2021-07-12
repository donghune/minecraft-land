package com.github.donghune.land.inventory

import com.github.donghune.plugin
import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.inventory.GUI
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

class LandMainInventory : GUI(plugin, 27, "메인 메뉴") {

    companion object {
        private val ICON_LAND_SETTING: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.WRITABLE_BOOK)
                .setDisplay("&f토지 설정")
                .build()
        }

        private val ICON_LAND_BUY: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.GOLD_INGOT)
                .setDisplay("&f토지 구매")
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
        setItem(11, ICON_LAND_SETTING()) {
            it.isCancelled = true
            LandSettingInventory().open(it.whoClicked as Player)
        }
        setItem(15, ICON_LAND_BUY()) {
            it.isCancelled = true
            LandBuyInventory().open(it.whoClicked as Player)
        }
    }
}