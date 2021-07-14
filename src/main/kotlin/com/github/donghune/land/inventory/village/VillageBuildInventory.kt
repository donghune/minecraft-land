package com.github.donghune.land.inventory.village

import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

class VillageBuildInventory : GUI(plugin, 27, "마을 구축") {

    companion object {
        private val ICON_GOLD: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.GOLD_INGOT)
                .setDisplay("&f금고 건설 비용")
                .setLore(
                    listOf(
                        "3,585,600 쉼"
                    )
                )
                .build()
        }
        private val ICON_LAND: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.GRASS_BLOCK)
                .setDisplay("&f중앙 토지 비용")
                .setLore(
                    listOf(
                        "1,792,800 쉼"
                    )
                )
                .build()
        }
        private val ICON_PICK_AXE: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.NETHERITE_PICKAXE)
                .setDisplay("&f중앙 건물 비용")
                .setLore(
                    listOf(
                        "16,135,200 쉼",
                        "실제 건물이 생성되지 않습니다."
                    )
                )
                .build()
        }
        private val ICON_BUILD: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.BEACON)
                .setDisplay("&f구축!")
                .setLore(
                    listOf(
                        "총 21,513,600 쉼을 지불하고 마을을 구축합니다."
                    )
                )
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
        setItem(11, ICON_LAND()) {
            it.isCancelled = true
        }
        setItem(12, ICON_PICK_AXE()) {
            it.isCancelled = true
        }
        setItem(15, ICON_BUILD()) {
            it.isCancelled = true
        }
    }
}