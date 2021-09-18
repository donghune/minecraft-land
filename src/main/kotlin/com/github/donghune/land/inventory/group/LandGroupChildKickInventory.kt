package com.github.donghune.land.inventory.group

import com.github.donghune.land.extension.toUUID
import com.github.donghune.land.inventory.InvIcon
import com.github.donghune.land.model.entity.Group
import com.github.donghune.land.model.repository.VillageRepository
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class LandGroupChildKickInventory(
    private val group: Group,
) : GUI(plugin, 9, "추방 할 구성원을 선택해주세요") {

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        group.child.forEachIndexed { index, uuid ->
            val centerIcon = VillageRepository.get(uuid)?.toItemStack() ?: InvIcon.ICON_OWNER(uuid.toUUID())
            setItem(index, centerIcon) {
                it.isCancelled = true
                LandGroupChildKickConfirmInventory(group, uuid.toUUID()).open(it.whoClicked as Player)
            }
        }
    }
}
