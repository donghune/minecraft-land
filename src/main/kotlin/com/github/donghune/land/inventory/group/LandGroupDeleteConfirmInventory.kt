package com.github.donghune.land.inventory.group

import com.github.donghune.land.inventory.InvIcon
import com.github.donghune.land.model.entity.Group
import com.github.donghune.land.model.usecase.GroupDeleteUseCase
import com.github.donghune.land.model.usecase.GroupDeleteUseCaseParam
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class LandGroupDeleteConfirmInventory(
    val group: Group,
) : GUI(plugin, 27, "") {

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        setItem(11, InvIcon.ICON_OK()) {
            it.isCancelled = true
            GroupDeleteUseCase(GroupDeleteUseCaseParam(group))
            (it.whoClicked as Player).closeInventory()
        }
        setItem(15, InvIcon.ICON_CANCEL()) {
            it.isCancelled = true
            (it.whoClicked as Player).closeInventory()
        }
    }
}
