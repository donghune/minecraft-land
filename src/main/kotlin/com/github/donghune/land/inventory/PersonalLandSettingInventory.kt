package com.github.donghune.land.inventory

import com.github.donghune.land.extension.giveMoney
import com.github.donghune.land.model.land.entity.Land
import com.github.donghune.land.plugin
import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.donghune.namulibrary.inventory.GUI
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

class PersonalLandSettingInventory(
    val land: Land,
) : GUI(plugin, 27, "개인 토지 설정") {

    companion object {
        private val ICON_BUY_PRICE: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.GOLD_INGOT)
                .setDisplay("&f토지 가격")
                .setLore(listOf(
                    "&f- 구매가 : 1,792,800쉼",
                    "&f- 판매가 : 1,613,520쉼"
                ))
                .build()
        }
        private val ICON_LAND_SELL: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.GOLD_INGOT)
                .setDisplay("&f토지 가격")
                .setLore(listOf(
                    "&f- 1,613,520쉼을 받고 토지를 판매합니다."
                ))
                .build()
        }
        private val ICON_ENVIRONMENT_PERMISSION: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.WRITABLE_BOOK)
                .setDisplay("&f토지 환경 설정")
                .setLore(listOf(
                    "&f- 토지 환경을 설정합니다."
                ))
                .build()
        }
        private val ICON_AUTHORITY_PERMISSION: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.WRITABLE_BOOK)
                .setDisplay("&f토지 권한 설정")
                .setLore(listOf(
                    "&f- 토지 권한을 설정합니다."
                ))
                .build()
        }
        private val ICON_TRANSFER_OF_OWNERSHIP: () -> ItemStack = {
            ItemBuilder()
                .setMaterial(Material.WRITABLE_BOOK)
                .setDisplay("&f토지 소유권 이전")
                .setLore(listOf(
                    "&f- 토지의 소유권을 이전합니다."
                ))
                .build()
        }
    }

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
        PersonalLandSelectInventory().openLater(event.player as Player)
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        setItem(9, ICON_BUY_PRICE()) {
            it.isCancelled = true
        }
        setItem(11, ICON_LAND_SELL()) {
            val player = it.whoClicked as Player
            it.isCancelled = true
            land.member.clear()
            land.owner = ""
            player.giveMoney(1613520)
            player.closeInventory()
            player.sendInfoMessage("토지를 판매하고 1,613,520원을 지급 받았습니다.")
        }
        setItem(13, ICON_ENVIRONMENT_PERMISSION()) {
            val player = it.whoClicked as Player
            it.isCancelled = true
            PersonalLandEnvironmentPermissionInventory(land).open(player)
        }
        setItem(15, ICON_AUTHORITY_PERMISSION()) {
            val player = it.whoClicked as Player
            it.isCancelled = true
            PersonalLandAuthorityPermissionInventory(land).open(player)
        }
        setItem(17, ICON_TRANSFER_OF_OWNERSHIP()) {
            val player = it.whoClicked as Player
            it.isCancelled = true
            PersonalTransferOfOwnershipInventory(land).open(player)
        }
    }
}