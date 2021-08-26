package com.github.donghune.land.inventory.group

import com.github.donghune.land.model.entity.Group
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

class LandGroupChildInventory(
    val group : Group
) : GUI(plugin, 27, "구성원 관리") {

    companion object {
        private val ICON_REQUEST_LIST: () -> ItemStack = {
            ItemStackFactory()
                .setType(Material.BOOK)
                .setDisplayName("&f신청 내역".replaceChatColorCode())
                .build()
        }
        private val ICON_KICK: () -> ItemStack = {
            ItemStackFactory()
                .setType(Material.BARRIER)
                .setDisplayName("&f추방".replaceChatColorCode())
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
        setItem(11, ICON_REQUEST_LIST()) {
            it.isCancelled = true
            LandGroupChildRequestListInventory(group).open(it.whoClicked as Player)
        }
        setItem(15, ICON_KICK()) {
            it.isCancelled = true
            LandGroupChildKickInventory(group).open(it.whoClicked as Player)
        }
    }
}