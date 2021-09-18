package com.github.donghune.land.inventory

import com.github.donghune.land.extension.getBelongingNation
import com.github.donghune.land.extension.getBelongingVillage
import com.github.donghune.land.extension.getNation
import com.github.donghune.land.extension.getVillage
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
    private val landType: LandType,
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
        when (landType) {
            LandType.PERSONAL -> {
                LandRepository.getList()
                    .filter { it.type == LandType.PERSONAL }
                    .filter { it.owner == target.uniqueId.toString() }
            }
            LandType.VILLAGE -> {
                LandRepository.getList()
                    .filter { it.type == LandType.VILLAGE }
                    .filter { it.owner == target.getBelongingVillage()!!.uuid }
            }
            LandType.NATION -> {
                LandRepository.getList()
                    .filter { it.type == LandType.NATION }
                    .filter { it.owner == target.getBelongingNation()!!.uuid }
            }
            else -> return
        }.forEachIndexed { index, land ->
            setItem(index, land.toItemStack(index)) {
                when (landType) {
                    LandType.NONE -> return@setItem
                    LandType.PERSONAL -> LandPersonalSettingInventory(land).open(it.whoClicked as Player)
                    LandType.NATION, LandType.VILLAGE -> LandGroupSettingInventory(land.getVillage() ?: land.getNation()!!, land).open(it.whoClicked as Player)
                }
            }
        }
    }
}
