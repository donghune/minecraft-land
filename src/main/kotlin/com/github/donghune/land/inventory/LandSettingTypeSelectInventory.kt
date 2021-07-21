package com.github.donghune.land.inventory

import com.github.donghune.land.extension.getBelongingNation
import com.github.donghune.land.extension.getBelongingVillage
import com.github.donghune.land.inventory.group.LandGroupBuildJoinInventory
import com.github.donghune.land.model.entity.LandType
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class LandSettingTypeSelectInventory : GUI(plugin, 27, "설정할 토지의 종류를 선택해주세요") {

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        setItem(11, InvIcon.ICON_LAND_PERSONAL()) {
            it.isCancelled = true
            LandSelectInventory(LandType.PERSONAL).open(it.whoClicked as Player)
        }
        setItem(13, InvIcon.ICON_LAND_VILLAGE()) {
            it.isCancelled = true
            val player = it.whoClicked as Player
            if (player.getBelongingVillage() == null) {
                LandGroupBuildJoinInventory(LandType.VILLAGE).open(it.whoClicked as Player)
                return@setItem
            }
            LandSelectInventory(LandType.VILLAGE).open(it.whoClicked as Player)
        }
        setItem(15, InvIcon.ICON_LAND_NATION()) {
            it.isCancelled = true
            val player = it.whoClicked as Player
            if (player.getBelongingNation() == null) {
                LandGroupBuildJoinInventory(LandType.NATION).open(it.whoClicked as Player)
                return@setItem
            }
            LandSelectInventory(LandType.NATION).open(it.whoClicked as Player)
        }
    }
}