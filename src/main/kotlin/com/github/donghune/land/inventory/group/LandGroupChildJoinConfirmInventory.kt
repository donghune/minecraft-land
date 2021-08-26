package com.github.donghune.land.inventory.group

import com.github.donghune.land.extension.getVillage
import com.github.donghune.land.extension.toOfflinePlayer
import com.github.donghune.land.extension.toUUID
import com.github.donghune.land.inventory.InvIcon
import com.github.donghune.land.model.entity.Group
import com.github.donghune.land.model.entity.Land
import com.github.donghune.land.model.entity.LandType
import com.github.donghune.land.model.repository.RegisterRequestRepository
import com.github.donghune.land.model.repository.VillageRepository
import com.github.donghune.land.model.usecase.GroupChildJoinAcceptUseCase
import com.github.donghune.land.model.usecase.GroupChildJoinAcceptUseCaseParam
import com.github.donghune.land.model.usecase.GroupChildJoinDismissUseCase
import com.github.donghune.land.model.usecase.GroupChildJoinDismissUseCaseParam
import com.github.donghune.namulibrary.extension.minecraft.ItemStackFactory
import com.github.donghune.namulibrary.extension.replaceChatColorCode
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack
import java.util.*

class LandGroupChildJoinConfirmInventory(
    private val group: Group,
    private val childUUID: UUID,
) : GUI(plugin, 27, "해당 유저의 신청을 수락하시겠습니까?") {

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
            GroupChildJoinAcceptUseCase.execute(GroupChildJoinAcceptUseCaseParam(group, childUUID))
            (it.whoClicked as Player).closeInventory()
        }

        setItem(13, centerIcon) {
            it.isCancelled = true
        }

        setItem(15, InvIcon.ICON_CANCEL()) {
            it.isCancelled = true
            GroupChildJoinDismissUseCase.execute(GroupChildJoinDismissUseCaseParam(group, childUUID))
            (it.whoClicked as Player).closeInventory()
        }
    }
}