package com.github.donghune.land.inventory

import com.github.donghune.land.model.land.entity.Land
import com.github.donghune.land.model.land.entity.LandType
import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.inventory.GUI
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.plugin.java.JavaPlugin

class LandTypeSelectGUI(
    val plugin: JavaPlugin,
    val land: Land,
) : GUI(plugin, 27, "토지를 구매하시겠습니까?") {

    companion object {
        private val ITEM_PRIVATE = ItemBuilder()
            .setMaterial(Material.GRASS)
            .setDisplay("&a개인 토지 구매하기")
            .build()
        private val ITEM_VILLAGE = ItemBuilder()
            .setMaterial(Material.OAK_WOOD)
            .setDisplay("&a마을 토지 구매하기")
            .build()
        private val ITEM_NATION = ItemBuilder()
            .setMaterial(Material.IRON_BLOCK)
            .setDisplay("&a국가 토지 구매하기")
            .build()
    }

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {

    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {

    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {

    }

    override suspend fun setContent() {
        setItem(11, ITEM_PRIVATE) {
            it.whoClicked.closeInventory()
            LandBuyGUI(plugin, land, LandType.PRIVATE)
        }
        setItem(13, ITEM_VILLAGE) {
            it.whoClicked.closeInventory()
            LandBuyGUI(plugin, land, LandType.VILLAGE)
        }
        setItem(15, ITEM_NATION) {
            it.whoClicked.closeInventory()
            LandBuyGUI(plugin, land, LandType.NATION)
        }
    }

}