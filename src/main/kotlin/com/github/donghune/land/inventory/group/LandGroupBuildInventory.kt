package com.github.donghune.land.inventory.group

import com.github.donghune.land.model.entity.LandType
import com.github.donghune.land.model.usecase.GroupBuildUseCase
import com.github.donghune.land.model.usecase.GroupBuildUseCaseParam
import com.github.donghune.namulibrary.extension.replaceChatColorCode
import com.github.donghune.namulibrary.extension.toMoneyFormat
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.plugin
import com.github.donghune.util.ItemStackFactory
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

class LandGroupBuildInventory(
    private val landType: LandType,
) : GUI(plugin, 27, "") {

    companion object {
        private val ICON_VAULT: (Int) -> ItemStack = {
            ItemStackFactory()
                .setType(Material.GOLD_INGOT)
                .setDisplayName("&f금고 건설 비용".replaceChatColorCode())
                .addLore("&f${it.toMoneyFormat()} 쉼".replaceChatColorCode())
                .build()
        }
        private val ICON_LAND: (Int) -> ItemStack = {
            ItemStackFactory()
                .setType(Material.GRASS_BLOCK)
                .setDisplayName("&f중앙 토지 비용".replaceChatColorCode())
                .addLore("&f${it.toMoneyFormat()} 쉼".replaceChatColorCode())
                .build()
        }
        private val ICON_BUILDING: (Int) -> ItemStack = {
            ItemStackFactory()
                .setType(Material.NETHERITE_PICKAXE)
                .setDisplayName("&f중앙 건물 비용".replaceChatColorCode())
                .addLore("&f${it.toMoneyFormat()} 쉼".replaceChatColorCode())
                .build()
        }
        private val ICON_BUILD: (Int) -> ItemStack = {
            ItemStackFactory()
                .setType(Material.BEACON)
                .setDisplayName("&f구축하기".replaceChatColorCode())
                .addLore("&f총 ${it.toMoneyFormat()} 쉼을 지불하고 구축합니다.".replaceChatColorCode())
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
        setItem(10, ICON_VAULT(landType.getBuildVaultPrice())) {
            it.isCancelled = true
        }
        setItem(11, ICON_LAND(landType.getBuildLandPrice())) {
            it.isCancelled = true
        }
        setItem(12, ICON_BUILDING(landType.getBuildBuildingPrice())) {
            it.isCancelled = true
        }
        setItem(15, ICON_BUILD(landType.getBuildPrice())) {
            it.isCancelled = true
            GroupBuildUseCase(GroupBuildUseCaseParam(it.whoClicked as Player, landType))
            (it.whoClicked as Player).closeInventory()
        }
    }
}
