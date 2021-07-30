package com.github.donghune.land.inventory

import com.github.donghune.land.model.entity.Land
import com.github.donghune.namulibrary.extension.minecraft.ItemStackFactory
import com.github.donghune.namulibrary.extension.replaceChatColorCode
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

class LandPersonalSettingInventory(
    private val land: Land,
) : GUI(plugin, 27, "개인 토지 설정") {

    companion object {
        private val ICON_GOLD: () -> ItemStack = {
            ItemStackFactory()
                .setType(Material.GOLD_INGOT)
                .setDisplayName("&f토지 시세".replaceChatColorCode())
                .build()
        }
        private val ICON_SELL: () -> ItemStack = {
            ItemStackFactory()
                .setType(Material.TOTEM_OF_UNDYING)
                .setDisplayName("&f토지 판매하기".replaceChatColorCode())
                .build()
        }
        private val ICON_TRANSFER: () -> ItemStack = {
            ItemStackFactory()
                .setType(Material.BOOK)
                .setDisplayName("&f토지 양도".replaceChatColorCode())
                .build()
        }
        private val ICON_PERMISSION: () -> ItemStack = {
            ItemStackFactory()
                .setType(Material.EMERALD)
                .setDisplayName("&f토지 권한".replaceChatColorCode())
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
            LandSellConfirmInventory(land).open(it.whoClicked as Player)
        }
        setItem(14, ICON_TRANSFER()) {
            it.isCancelled = true
            LandTransformPlayerSelectInventory(land).open(it.whoClicked as Player)
        }
        setItem(16, ICON_PERMISSION()) {
            it.isCancelled = true
            LandPermissionInventory(land).open(it.whoClicked as Player)
        }
    }
}