package com.github.donghune.land.inventory

import com.github.donghune.land.model.entity.Land
import com.github.donghune.land.model.entity.LandOption
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class LandPermissionInventory(
    private val land: Land,
) : GUI(plugin, 27, "토지 권한 설정") {

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        LandOption.values().forEachIndexed { index, landOption ->
            setItem(index, landOption.toItemStack(land.landOption[landOption] ?: false)) {
                it.isCancelled = true
                land.landOption[landOption] = land.landOption[landOption]?.not() ?: false
                refreshContent()
            }
        }
    }
}