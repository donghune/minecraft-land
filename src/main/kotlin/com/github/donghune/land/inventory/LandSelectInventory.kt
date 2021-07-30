package com.github.donghune.land.inventory

import com.github.donghune.land.inventory.group.LandGroupSettingInventory
import com.github.donghune.land.model.entity.LandType
import com.github.donghune.land.model.repository.LandRepository
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class LandSelectInventory(
    private val landType: LandType
) : GUI(plugin, 27, "토지를 선택해주세요") {

    lateinit var target: Player

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
        target = event.player as Player
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        LandRepository.getList()
            .filter { it.type == landType }
            .filter { it.owner == target.uniqueId.toString() }
            .forEachIndexed { index, land ->
                setItem(index, land.toItemStack(index)) {
                    when (landType) {
                        LandType.NONE -> return@setItem
                        LandType.PERSONAL -> LandPersonalSettingInventory(land).open(it.whoClicked as Player)
                        LandType.NATION, LandType.VILLAGE -> LandGroupSettingInventory(land).open(it.whoClicked as Player)
                    }
                }
            }
    }
}