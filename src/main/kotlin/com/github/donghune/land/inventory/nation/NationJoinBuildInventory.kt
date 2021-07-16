package com.github.donghune.land.inventory.nation

import com.github.donghune.land.inventory.village.VillageJoinInventory
import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

class NationJoinBuildInventory : GUI(plugin, 27, "국가 가입 또는 구축") {

    companion object {
        private val ICON_JOIN: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.BOOK)
                .setDisplay("&f국가 가입")
                .build()
        }
        private val ICON_BUILD: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.WRITABLE_BOOK)
                .setDisplay("&f국가 구축")
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
            it.isCancelled = true
            NationJoinInventory().open(it.whoClicked as Player)
        }
        setItem(15, ICON_BUILD()) {
            it.isCancelled = true
            NationBuildInventory().open(it.whoClicked as Player)
        }
    }
}