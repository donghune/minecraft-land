package com.github.donghune.land.inventory

import com.github.donghune.land.plugin
import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.inventory.GUI
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

class LandSettingInventory : GUI(plugin, 27, "토지 설정") {

    companion object {
        private val ICON_PERSONAL_LAND: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.GRASS_BLOCK)
                .setDisplay("&f개인 토지 설정")
                .build()
        }
        private val ICON_VILLAGE_LAND: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.OAK_WOOD)
                .setDisplay("&f마을 토지 설정")
                .build()
        }
        private val ICON_NATION_LAND: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.IRON_BLOCK)
                .setDisplay("&f국가 토지 설정")
                .build()
        }
    }

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
        LandMainInventory().openLater(event.player as Player)
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        setItem(11, ICON_PERSONAL_LAND()) {
            it.isCancelled = true
            PersonalLandSelectInventory().open(it.whoClicked as Player)
        }
        setItem(13, ICON_VILLAGE_LAND()) {

        }
        setItem(15, ICON_NATION_LAND()) {

        }
    }
}