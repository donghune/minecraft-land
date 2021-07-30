package com.github.donghune.land.inventory

import com.github.donghune.namulibrary.extension.minecraft.ItemStackFactory
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

class LandInventory : GUI(plugin, 27, "토지") {

    companion object {
        private val ICON_SETTING: () -> ItemStack = {
            ItemStackFactory()
                .setType(Material.WRITABLE_BOOK)
                .setDisplayName("토지 설정")
                .build()
        }

        private val ICON_BUY: () -> ItemStack = {
            ItemStackFactory()
                .setType(Material.GOLD_INGOT)
                .setDisplayName("토지 구매")
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
        setItem(11, ICON_SETTING()) {
            it.isCancelled = true
            LandSettingTypeSelectInventory().open(it.whoClicked as Player)
        }
        setItem(15, ICON_BUY()) {
            it.isCancelled = true
            LandBuyTypeSelectInventory().open(it.whoClicked as Player)
        }
    }
}