package com.github.donghune.land.inventory.group

import com.github.donghune.land.model.config.pref
import com.github.donghune.land.model.entity.LandType
import com.github.donghune.land.model.usecase.BuildVillageUseCase
import com.github.donghune.land.model.usecase.BuildVillageUseCaseParam
import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.extension.toMoneyFormat
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

class LandGroupBuildInventory(
    private val landType: LandType
) : GUI(plugin, 27, "") {

    companion object {
        private val ICON_VAULT: (Int) -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.GOLD_INGOT)
                .setDisplay("금고 건설 비용")
                .setLore(listOf("${it.toMoneyFormat()} 쉼"))
                .build()
        }
        private val ICON_LAND: (Int) -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.GRASS_BLOCK)
                .setDisplay("")
                .setLore(listOf("${it.toMoneyFormat()} 쉼"))
                .build()
        }
        private val ICON_BUILDING: (Int) -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.NETHERITE_PICKAXE)
                .setDisplay("")
                .setLore(listOf("${it.toMoneyFormat()} 쉼"))
                .build()
        }
        private val ICON_BUILD: (Int) -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.BEACON)
                .setDisplay("")
                .setLore(listOf("총 ${it.toMoneyFormat()} 쉼을 지불하고 구축합니다."))
                .build()
        }
    }

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        setItem(0, ICON_VAULT(0)) {
            it.isCancelled = true
        }
        setItem(0, ICON_LAND(0)) {
            it.isCancelled = true
        }
        setItem(0, ICON_BUILDING(0)) {
            it.isCancelled = true
        }
        setItem(0, ICON_BUILD(0)) {
            it.isCancelled = true
            BuildVillageUseCase.execute(BuildVillageUseCaseParam(it.whoClicked as Player, landType))
            (it.whoClicked as Player).closeInventory()
        }
    }
}