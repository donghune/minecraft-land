package com.github.donghune.land.inventory

import com.github.donghune.land.model.entity.Land
import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

class LandPersonalSettingInventory(land: Land) : GUI(plugin, 27, "개인 토지 설정") {

    companion object {
        private val ICON_GOLD: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.GOLD_INGOT)
                .setDisplay("")
                .build()
        }
        private val ICON_SELL: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.GOLD_INGOT)
                .setDisplay("")
                .build()
        }
        private val ICON_TRANSFER: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.GOLD_INGOT)
                .setDisplay("")
                .build()
        }
        private val ICON_PERMISSION: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.GOLD_INGOT)
                .setDisplay("")
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
        setItem(10, ICON_GOLD()) {
            it.isCancelled = true
        }
        setItem(12, ICON_SELL()) {
            it.isCancelled = true
            LandSellConfirmInventory().open(it.whoClicked as Player)
        }
        setItem(14, ICON_TRANSFER()) {
            it.isCancelled = true
            LandTransformConfirmInventory().open(it.whoClicked as Player)
        }
        setItem(16, ICON_PERMISSION()) {
            it.isCancelled = true
            LandPermissionInventory().open(it.whoClicked as Player)
        }
    }
}