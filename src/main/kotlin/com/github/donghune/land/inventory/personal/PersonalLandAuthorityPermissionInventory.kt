package com.github.donghune.land.inventory.personal

import com.github.donghune.land.model.entity.Land
import com.github.donghune.land.model.repository.LandRepository
import com.github.donghune.plugin
import com.github.donghune.namulibrary.inventory.GUI
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class PersonalLandAuthorityPermissionInventory(
    val land: Land,
) : GUI(plugin, 27, "개인 토지 권한 설정") {

    companion object;

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        land.authorityPermission.onEachIndexed { index, entry ->
            val permission = entry.key
            val value = entry.value
            setItem(index, permission.toItemStack(value)) {
                it.isCancelled = true
                land.authorityPermission[permission] = !value
                LandRepository.update(land.chunkKey.toString(), land)
                refreshContent()
            }
        }
    }
}