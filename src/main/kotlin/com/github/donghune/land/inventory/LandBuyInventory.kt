package com.github.donghune.land.inventory

import com.github.donghune.land.extension.getBelongingNation
import com.github.donghune.land.extension.getBelongingVillage
import com.github.donghune.land.inventory.nation.NationJoinBuildInventory
import com.github.donghune.land.inventory.nation.NationLandBuyInventory
import com.github.donghune.land.inventory.personal.PersonalLandBuyInventory
import com.github.donghune.land.inventory.personal.PersonalLandSelectInventory
import com.github.donghune.land.inventory.village.VillageJoinBuildInventory
import com.github.donghune.land.inventory.village.VillageLandBuyInventory
import com.github.donghune.land.model.entity.Nation
import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.plugin
import com.github.donghune.namulibrary.inventory.GUI
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

class LandBuyInventory : GUI(plugin, 27, "토지 구매") {

    companion object {
        private val ICON_PERSONAL_LAND: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.GRASS_BLOCK)
                .setDisplay("&f개인 토지")
                .build()
        }
        private val ICON_VILLAGE_LAND: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.OAK_WOOD)
                .setDisplay("&f마을 토지")
                .build()
        }
        private val ICON_NATION_LAND: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.IRON_BLOCK)
                .setDisplay("&f국가 토지")
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
        setItem(11, ICON_PERSONAL_LAND()) {
            it.isCancelled = true
            PersonalLandBuyInventory().open(it.whoClicked as Player)
        }
        setItem(13, ICON_VILLAGE_LAND()) {
            it.isCancelled = true
            val player = it.whoClicked as Player

            if (player.getBelongingVillage() == null) {
                VillageJoinBuildInventory().open(player)
            } else {
                VillageLandBuyInventory().open(player)
            }
        }
        setItem(15, ICON_NATION_LAND()) {
            it.isCancelled = true
            val player = it.whoClicked as Player

            if (player.getBelongingNation() == null) {
                NationJoinBuildInventory().open(player)
            } else {
                NationLandBuyInventory().open(player)
            }
        }
    }
}