package com.github.donghune.land.inventory.personal

import com.github.donghune.land.extension.getLandList
import com.github.donghune.land.inventory.LandSettingInventory
import com.github.donghune.plugin
import com.github.donghune.namulibrary.inventory.GUI
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

class PersonalLandSelectInventory : GUI(plugin, 27, "메인 메뉴") {
    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
        LandSettingInventory().openLater(event.player as Player)
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        val player = inventory.viewers[0] as Player

        (9..17).forEach { setItem(it, ItemStack(Material.BARRIER)) { event -> event.isCancelled = true } }
        player.getLandList().forEachIndexed { index, land ->
            setItem(index + 9, land.toItemStack()) {
                it.isCancelled = true
                PersonalLandSettingInventory(land).open(it.whoClicked as Player)
            }
        }
    }
}