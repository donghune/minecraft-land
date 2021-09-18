package com.github.donghune.land.inventory.group

import com.github.donghune.land.extension.getVillage
import com.github.donghune.land.inventory.InvIcon
import com.github.donghune.land.model.entity.Group
import com.github.donghune.land.model.usecase.GroupChildKickUseCase
import com.github.donghune.land.model.usecase.GroupChildKickUseCaseParam
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import java.util.*

class LandGroupChildKickConfirmInventory(
    private val group: Group,
    private val childUUID: UUID,
) : GUI(plugin, 27, "추방하시겠습니까?") {

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {

        val centerIcon = childUUID.getVillage()?.toItemStack() ?: InvIcon.ICON_OWNER(childUUID)

        setItem(11, InvIcon.ICON_OK()) {
            it.isCancelled = true
            GroupChildKickUseCase(GroupChildKickUseCaseParam(group, childUUID))
            (it.whoClicked as Player).closeInventory()
        }

        setItem(13, centerIcon) {
            it.isCancelled = true
        }

        setItem(15, InvIcon.ICON_CANCEL()) {
            it.isCancelled = true
            (it.whoClicked as Player).closeInventory()
        }
    }
}
