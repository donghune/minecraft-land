package com.github.donghune.land.inventory

import com.github.donghune.land.model.land.entity.Land
import com.github.donghune.land.model.land.entity.LandType
import com.github.donghune.land.model.land.usecase.BuyLandUseCase
import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.extension.toMoneyFormat
import com.github.donghune.namulibrary.inventory.GUI
import com.github.shynixn.mccoroutine.launch
import kotlinx.coroutines.coroutineScope
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class LandBuyGUI(
    val plugin: JavaPlugin,
    val land: Land,
    private val landType: LandType,
) : GUI(plugin, 27, "토지를 구매하시겠습니까?") {

    companion object {
        private val ITEM_YES: (Int) -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.GREEN_WOOL)
                .setDisplay("&a확인")
                .setLore(listOf("&6${it.toMoneyFormat()} &f쉼을 지불하고 땅을 구매합니다."))
                .build()
        }

        private val ITEM_NO: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.RED_WOOL)
                .setDisplay("&a취소")
                .setLore(listOf("땅 구매를 취소합니다."))
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
        setItem(11, ITEM_YES(10000)) {
            BuyLandUseCase.buyLand(it.whoClicked as Player, land, landType)
        }
        setItem(15, ITEM_NO()) {
            plugin.launch { LandTypeSelectGUI(plugin, land).openLater(it.whoClicked as Player) }
        }
    }
}