package com.github.donghune.land.inventory.group

import com.github.donghune.land.extension.toUUID
import com.github.donghune.land.inventory.InvIcon
import com.github.donghune.land.model.entity.Group
import com.github.donghune.land.model.entity.Village
import com.github.donghune.land.model.repository.RegisterRequestRepository
import com.github.donghune.land.model.repository.VillageRepository
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

class LandGroupChildRequestListInventory(
    val group: Group,
) : GUI(plugin, 27, "가입 요청 목록") {

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        val groupUUID = group.uuid
        RegisterRequestRepository.getList()
            .filter { it.groupUUID == groupUUID }
            .map {
                it.requesterUUID to if (group is Village) {
                    InvIcon.ICON_OWNER(it.requesterUUID.toUUID())
                } else {
                    VillageRepository.get(it.requesterUUID)!!.toItemStack()
                }
            }.forEachIndexed { index, (uuid, itemStack) ->
                setItem(index, itemStack) {
                    LandGroupChildJoinConfirmInventory(group, uuid.toUUID()).open(it.whoClicked as Player)
                }
            }
    }
}