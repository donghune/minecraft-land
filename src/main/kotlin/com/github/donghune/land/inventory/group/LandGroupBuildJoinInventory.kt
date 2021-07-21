package com.github.donghune.land.inventory.group

import com.github.donghune.land.model.entity.LandType
import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

class LandGroupBuildJoinInventory(
    private val landType: LandType
) : GUI(plugin, 27, "${landType.korName} 가입 또는 구축") {

    companion object {
        private val ICON_JOIN: (LandType) -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.OAK_SIGN)
                .setDisplay("${it.korName} 가입")
                .build()
        }
        private val ICON_BUILD: (LandType) -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.WRITABLE_BOOK)
                .setDisplay("${it.korName} 구축")
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
        setItem(11, ICON_JOIN(landType)) {
            it.isCancelled = true
            LandGroupJoinInventory().open(it.whoClicked as Player)
        }
        setItem(15, ICON_BUILD(landType)) {
            it.isCancelled = true
            LandGroupBuildInventory().open(it.whoClicked as Player)
        }
    }
}